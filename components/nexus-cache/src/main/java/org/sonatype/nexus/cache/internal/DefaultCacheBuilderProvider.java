package org.sonatype.nexus.cache.internal;

import java.util.Map;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.cache.CacheBuilder;
import org.sonatype.nexus.common.node.NodeAccess;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Runtime {@link CacheBuilder} provider using {@code nexus.cache.provider}
 * configuration to select the named cache builder to use.
 *
 * Defaults to {@code ehcache}.
 *
 * @since 3.14
 */
@Named("default")
@Singleton
public class DefaultCacheBuilderProvider
    extends ComponentSupport
    implements Provider<CacheBuilder>
{
  private final Map<String, Provider<CacheBuilder>> providers;

  private final String name;

  @Inject
  public DefaultCacheBuilderProvider(final Map<String, Provider<CacheBuilder>> providers,
                                     @Nullable @Named("${nexus.cache.provider}") final String customName,
                                     final NodeAccess nodeAccess)
  {
    this.providers = checkNotNull(providers);
    this.name = customName != null ? customName : (nodeAccess.isClustered() ? "hazelcast" : "ehcache");
    checkArgument(!"default".equals(name));
    checkState(providers.containsKey(name), "Missing cache-builder: %s", name);
  }

  @Override
  public CacheBuilder get() {
    Provider<CacheBuilder> provider = providers.get(name);
    checkState(provider != null, "Cache-builder vanished: %s", name);
    CacheBuilder builder = provider.get();
    log.debug("Constructed cache-builder: {} -> {}", name, builder);
    return builder;
  }
}
