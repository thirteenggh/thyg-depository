package org.sonatype.nexus.repository.rest.api;

import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.goodies.common.Time;
import org.sonatype.nexus.common.collect.NestedAttributesMap;
import org.sonatype.nexus.common.entity.EntityId;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.config.Configuration;
import org.sonatype.nexus.repository.config.ConfigurationConstants;
import org.sonatype.nexus.repository.rest.api.model.AbstractApiRepository;
import org.sonatype.nexus.repository.rest.api.model.CleanupPolicyAttributes;
import org.sonatype.nexus.repository.rest.api.model.GroupAttributes;
import org.sonatype.nexus.repository.rest.api.model.GroupDeployAttributes;
import org.sonatype.nexus.repository.rest.api.model.HostedStorageAttributes;
import org.sonatype.nexus.repository.rest.api.model.HttpClientAttributes;
import org.sonatype.nexus.repository.rest.api.model.HttpClientConnectionAttributes;
import org.sonatype.nexus.repository.rest.api.model.HttpClientConnectionAuthenticationAttributes;
import org.sonatype.nexus.repository.rest.api.model.NegativeCacheAttributes;
import org.sonatype.nexus.repository.rest.api.model.ProxyAttributes;
import org.sonatype.nexus.repository.rest.api.model.SimpleApiGroupRepository;
import org.sonatype.nexus.repository.rest.api.model.SimpleApiHostedRepository;
import org.sonatype.nexus.repository.rest.api.model.SimpleApiProxyRepository;
import org.sonatype.nexus.repository.rest.api.model.StorageAttributes;
import org.sonatype.nexus.repository.routing.RoutingRule;
import org.sonatype.nexus.repository.routing.RoutingRuleStore;
import org.sonatype.nexus.repository.types.GroupType;
import org.sonatype.nexus.repository.types.HostedType;
import org.sonatype.nexus.repository.types.ProxyType;

import com.google.common.reflect.TypeToken;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.repository.config.ConfigurationConstants.GROUP_WRITE_MEMBER;

/**
 * @since 3.20
 */
@Named("default")
public class SimpleApiRepositoryAdapter
    implements ApiRepositoryAdapter
{
  private final RoutingRuleStore routingRuleStore;

  @Inject
  public SimpleApiRepositoryAdapter(final RoutingRuleStore routingRuleStore) {
    this.routingRuleStore = checkNotNull(routingRuleStore);
  }

  @Override
  public AbstractApiRepository adapt(final Repository repository) {
    boolean online = repository.getConfiguration().isOnline();
    String name = repository.getName();
    String format = repository.getFormat().toString();
    String url = repository.getUrl();

    switch (repository.getType().toString()) {
      case GroupType.NAME:
        return new SimpleApiGroupRepository(name, format, url, online, getStorageAttributes(repository),
            getGroupAttributes(repository));
      case HostedType.NAME:
        return new SimpleApiHostedRepository(name, format, url, online, getHostedStorageAttributes(repository),
            getCleanupPolicyAttributes(repository));
      case ProxyType.NAME:
        return new SimpleApiProxyRepository(name, format, url, online, getStorageAttributes(repository),
            getCleanupPolicyAttributes(repository), getProxyAttributes(repository),
            getNegativeCacheAttributes(repository), getHttpClientAttributes(repository),
            getRoutingRuleName(repository));
      default:
        return null;
    }
  }

  protected String getRoutingRuleName(final Repository repository) {
    EntityId routingRuleId = repository.getConfiguration().getRoutingRuleId();
    if (routingRuleId == null) {
      return null;
    }
    RoutingRule routingRule = routingRuleStore.getById(routingRuleId.getValue());
    return routingRule != null ? routingRule.name() : null;
  }

  protected StorageAttributes getStorageAttributes(final Repository repository) {
    NestedAttributesMap configuration = repository.getConfiguration().attributes(ConfigurationConstants.STORAGE);

    String blobStoreName = configuration.get(ConfigurationConstants.BLOB_STORE_NAME, String.class);
    Boolean strictContentTypeValidation =
        configuration.get(ConfigurationConstants.STRICT_CONTENT_TYPE_VALIDATION, Boolean.class, Boolean.TRUE);

    return new StorageAttributes(blobStoreName, strictContentTypeValidation);
  }

  protected HostedStorageAttributes getHostedStorageAttributes(final Repository repository) {
    NestedAttributesMap configuration = repository.getConfiguration().attributes(ConfigurationConstants.STORAGE);

    String blobStoreName = configuration.get(ConfigurationConstants.BLOB_STORE_NAME, String.class);
    Boolean strictContentTypeValidation =
        configuration.get(ConfigurationConstants.STRICT_CONTENT_TYPE_VALIDATION, Boolean.class, Boolean.TRUE);
    String writePolicy =
        toString(configuration.get(ConfigurationConstants.WRITE_POLICY), ConfigurationConstants.WRITE_POLICY_DEFAULT);

    return new HostedStorageAttributes(blobStoreName, strictContentTypeValidation, writePolicy);
  }

  protected GroupDeployAttributes getGroupAttributes(final Repository repository) {
    NestedAttributesMap groupAttributes = repository.getConfiguration().attributes("group");
    Collection<String> memberNames = groupAttributes.get("memberNames", new TypeToken<Collection<String>>() { });
    String groupWriteMember = groupAttributes.get(GROUP_WRITE_MEMBER, String.class);
    return new GroupDeployAttributes(memberNames, groupWriteMember);
  }

  protected CleanupPolicyAttributes getCleanupPolicyAttributes(final Repository repository) {
    Configuration configuration = repository.getConfiguration();
    if (!configuration.getAttributes().containsKey("cleanup")) {
      return null;
    }

    Collection<String> policyNames =
        configuration.attributes("cleanup").get("policyName", new TypeToken<Collection<String>>()
        {
        }, Collections.emptyList());

    return new CleanupPolicyAttributes(policyNames);
  }

  protected ProxyAttributes getProxyAttributes(final Repository repository) {
    NestedAttributesMap configuration = repository.getConfiguration().attributes("proxy");
    String remoteUrl = configuration.get("remoteUrl", String.class);
    Integer contentMaxAge = toInt(configuration.get("contentMaxAge", Number.class), 1440);
    Integer metadataMaxAge = toInt(configuration.get("metadataMaxAge", Number.class), 1440);
    return new ProxyAttributes(remoteUrl, contentMaxAge, metadataMaxAge);
  }

  protected NegativeCacheAttributes getNegativeCacheAttributes(final Repository repository) {
    NestedAttributesMap configuration = repository.getConfiguration().attributes("negativeCache");

    Boolean enabled = configuration.get("enabled", Boolean.class, true);
    Integer timeToLive = toInt(configuration.get("timeToLive", Number.class, Time.hours(24).toMinutesI()));

    return new NegativeCacheAttributes(enabled, timeToLive);
  }

  protected HttpClientAttributes getHttpClientAttributes(final Repository repository) {
    Configuration configuration = repository.getConfiguration();

    NestedAttributesMap httpclient = configuration.attributes("httpclient");

    Boolean blocked = httpclient.get("blocked", Boolean.class, false);
    Boolean autoBlock = httpclient.get("autoBlock", Boolean.class, false);

    HttpClientConnectionAuthenticationAttributes authentication = null;
    if (httpclient.contains("authentication")) {
      NestedAttributesMap authenticationMap = httpclient.child("authentication");

      String type = authenticationMap.get("type", String.class);
      String username = authenticationMap.get("username", String.class);
      String ntlmHost = authenticationMap.get("ntlmHost", String.class);
      String ntlmDomain = authenticationMap.get("ntlmDomain", String.class);

      authentication = new HttpClientConnectionAuthenticationAttributes(type, username, null, ntlmHost, ntlmDomain);
    }

    HttpClientConnectionAttributes connection = null;
    if (httpclient.contains("connection")) {
      NestedAttributesMap connectionMap = httpclient.child("connection");
      Integer retries = toInt(connectionMap.get("retries", Number.class));
      String userAgentSuffix = connectionMap.get("userAgentSuffix", String.class);
      Integer timeout = toInt(connectionMap.get("timeout", Number.class));
      Boolean enableCircularRedirects = connectionMap.get("enableCircularRedirects", Boolean.class, Boolean.FALSE);
      Boolean enableCookies = connectionMap.get("enableCookies", Boolean.class, Boolean.FALSE);

      connection =
          new HttpClientConnectionAttributes(retries, userAgentSuffix, timeout, enableCircularRedirects, enableCookies);
    }

    return new HttpClientAttributes(blocked, autoBlock, connection, authentication);
  }

  protected static String toString(final Object o, final Object defaultValue) {
    return (o == null ? defaultValue : o).toString();
  }

  protected static Integer toInt(final Number num) {
    return num == null ? null : num.intValue();
  }

  protected static Integer toInt(final Number num, final Integer defaultValue) {
    return num == null ? defaultValue : num.intValue();
  }
}
