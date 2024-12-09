package org.sonatype.nexus.blobstore.s3.internal;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.blobstore.api.BlobStoreConfiguration;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.PredefinedClientConfigurations;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.auth.STSAssumeRoleSessionCredentialsProvider;
import com.amazonaws.metrics.AwsSdkMetrics;
import com.amazonaws.regions.DefaultAwsRegionProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.NexusS3ClientBuilder;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;

import static com.google.common.base.Strings.isNullOrEmpty;
import static org.sonatype.nexus.blobstore.s3.internal.S3BlobStore.ACCESS_KEY_ID_KEY;
import static org.sonatype.nexus.blobstore.s3.internal.S3BlobStore.ASSUME_ROLE_KEY;
import static org.sonatype.nexus.blobstore.s3.internal.S3BlobStore.CONFIG_KEY;
import static org.sonatype.nexus.blobstore.s3.internal.S3BlobStore.ENDPOINT_KEY;
import static org.sonatype.nexus.blobstore.s3.internal.S3BlobStore.FORCE_PATH_STYLE_KEY;
import static org.sonatype.nexus.blobstore.s3.internal.S3BlobStore.REGION_KEY;
import static org.sonatype.nexus.blobstore.s3.internal.S3BlobStore.SECRET_ACCESS_KEY_KEY;
import static org.sonatype.nexus.blobstore.s3.internal.S3BlobStore.SESSION_TOKEN_KEY;
import static org.sonatype.nexus.blobstore.s3.internal.S3BlobStore.SIGNERTYPE_KEY;

/**
 * Creates configured AmazonS3 clients.
 *
 * @since 3.6.1
 */
@Named
public class AmazonS3Factory
    extends ComponentSupport
{
  public static final String DEFAULT = "DEFAULT";

  private final int connectionPoolSize;

  private final boolean cloudWatchMetricsEnabled;

  private final String cloudWatchMetricsNamespace;

  @Inject
  public AmazonS3Factory(@Named("${nexus.s3.connection.pool:--1}") final int connectionPoolSize,
                         @Named("${nexus.s3.cloudwatchmetrics.enabled:-false}") final boolean cloudWatchMetricsEnabled,
                         @Named("${nexus.s3.cloudwatchmetrics.namespace:-nexus-blobstore-s3}") final String cloudWatchMetricsNamespace) {
    this.connectionPoolSize = connectionPoolSize;
    this.cloudWatchMetricsEnabled = cloudWatchMetricsEnabled;
    this.cloudWatchMetricsNamespace = cloudWatchMetricsNamespace;
  }

  public AmazonS3 create(final BlobStoreConfiguration blobStoreConfiguration) {
    NexusS3ClientBuilder builder = NexusS3ClientBuilder.standard();

    String accessKeyId = blobStoreConfiguration.attributes(CONFIG_KEY).get(ACCESS_KEY_ID_KEY, String.class);
    String secretAccessKey = blobStoreConfiguration.attributes(CONFIG_KEY).get(SECRET_ACCESS_KEY_KEY, String.class);
    String region = blobStoreConfiguration.attributes(CONFIG_KEY).get(REGION_KEY, String.class);
    String signerType = blobStoreConfiguration.attributes(CONFIG_KEY).get(SIGNERTYPE_KEY, String.class);
    String forcePathStyle = blobStoreConfiguration.attributes(CONFIG_KEY).get(FORCE_PATH_STYLE_KEY, String.class);

    AWSCredentialsProvider credentialsProvider = null;
    if (!isNullOrEmpty(accessKeyId) && !isNullOrEmpty(secretAccessKey)) {
      String sessionToken = blobStoreConfiguration.attributes(CONFIG_KEY).get(SESSION_TOKEN_KEY, String.class);
      AWSCredentials credentials = buildCredentials(accessKeyId, secretAccessKey, sessionToken);

      String assumeRole = blobStoreConfiguration.attributes(CONFIG_KEY).get(ASSUME_ROLE_KEY, String.class);
      credentialsProvider = buildCredentialsProvider(credentials, region, assumeRole);

      builder = builder.withCredentials(credentialsProvider);
    }

    if (!isNullOrEmptyOrDefault(region)) {
      String endpoint = blobStoreConfiguration.attributes(CONFIG_KEY).get(ENDPOINT_KEY, String.class);
      if (!isNullOrEmpty(endpoint)) {
        builder = builder.withEndpointConfiguration(new AmazonS3ClientBuilder.EndpointConfiguration(endpoint, region));
      } else {
        builder = builder.withRegion(region);
      }
    }

    ClientConfiguration clientConfiguration = PredefinedClientConfigurations.defaultConfig();
    if (connectionPoolSize > 0) {
      clientConfiguration.setMaxConnections(connectionPoolSize);
    }
    if (!isNullOrEmptyOrDefault(signerType)) {
      clientConfiguration.setSignerOverride(signerType);
    }
    builder = builder.withClientConfiguration(clientConfiguration);

    builder = builder.withPathStyleAccessEnabled(Boolean.parseBoolean(forcePathStyle));

    builder.withBlobStoreConfig(blobStoreConfiguration);

    if (cloudWatchMetricsEnabled) {
      if (credentialsProvider != null) {
          AwsSdkMetrics.setCredentialProvider(credentialsProvider);
      }
      AwsSdkMetrics.setMetricNameSpace(cloudWatchMetricsNamespace);
      if (!isNullOrEmptyOrDefault(region)) {
        AwsSdkMetrics.setRegion(region);
      }
      AwsSdkMetrics.enableDefaultMetrics();
      log.info("CloudWatch metrics enabled using namespace {}", cloudWatchMetricsNamespace);
    }

    return builder.build();
  }

  private AWSCredentials buildCredentials(final String accessKeyId,
                                          final String secretAccessKey,
                                          final String sessionToken) {
    if (isNullOrEmpty(sessionToken)) {
      return new BasicAWSCredentials(accessKeyId, secretAccessKey);
    }
    else {
      return new BasicSessionCredentials(accessKeyId, secretAccessKey, sessionToken);
    }
  }

  private AWSCredentialsProvider buildCredentialsProvider(final AWSCredentials credentials, final String region, final String assumeRole) {
    AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
    if (isNullOrEmpty(assumeRole)) {
      return credentialsProvider;
    }
    else {
      // STS requires a region; fall back on the SDK default if not set
      String stsRegion;
      if (isNullOrEmpty(region)) {
        stsRegion = defaultRegion();
      }
      else {
        stsRegion = region;
      }
      AWSSecurityTokenService securityTokenService = AWSSecurityTokenServiceClientBuilder.standard()
          .withRegion(stsRegion)
          .withCredentials(credentialsProvider).build();

      return new STSAssumeRoleSessionCredentialsProvider.Builder(assumeRole, "nexus-s3-session")
          .withStsClient(securityTokenService)
          .build();
    }
  }

  private String defaultRegion() {
    try {
      return new DefaultAwsRegionProviderChain().getRegion();
    }
    catch (SdkClientException e) {
      String region = Regions.DEFAULT_REGION.getName();
      log.warn("Default AWS region not configured, using {}", region, e);
      return region;
    }
  }

  private boolean isNullOrEmptyOrDefault(final String value) {
    return isNullOrEmpty(value) || DEFAULT.equals(value);
  }
}
