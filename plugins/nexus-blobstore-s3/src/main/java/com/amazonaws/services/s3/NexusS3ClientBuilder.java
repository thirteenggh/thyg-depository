package com.amazonaws.services.s3;

import org.sonatype.nexus.blobstore.api.BlobStoreConfiguration;

import com.amazonaws.client.AwsSyncClientParams;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Wraps another AmazonS3 client builder to create a {@link EncryptingAmazonS3Client}.
 *
 * It is located in this package because of {@code S3CredentialsProviderChain} and {@code AmazonS3ClientParamsWrapper}
 *
 * @since 3.19
 */
public class NexusS3ClientBuilder
    extends AmazonS3Builder<NexusS3ClientBuilder, AmazonS3>
{

  private BlobStoreConfiguration blobStoreConfig;

  public static NexusS3ClientBuilder standard() {
    NexusS3ClientBuilder builder = new NexusS3ClientBuilder();
    builder.setCredentials(new S3CredentialsProviderChain());
    return builder;
  }

  public NexusS3ClientBuilder withBlobStoreConfig(final BlobStoreConfiguration blobStoreConfig) {
    this.blobStoreConfig = checkNotNull(blobStoreConfig);
    return this;
  }

  public BlobStoreConfiguration getBlobStoreConfig() {
    return blobStoreConfig;
  }

  @Override
  protected AmazonS3 build(final AwsSyncClientParams clientParams) {
    checkNotNull(getBlobStoreConfig());
    return new EncryptingAmazonS3Client(getBlobStoreConfig(), new AmazonS3ClientParamsWrapper(clientParams, resolveS3ClientOptions()));
  }
}
