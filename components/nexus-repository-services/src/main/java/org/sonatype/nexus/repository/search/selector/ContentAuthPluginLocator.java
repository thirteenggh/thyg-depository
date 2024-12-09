package org.sonatype.nexus.repository.search.selector;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.elasticsearch.PluginLocator;
import org.sonatype.nexus.repository.manager.RepositoryManager;
import org.sonatype.nexus.repository.search.query.SearchSubjectHelper;
import org.sonatype.nexus.repository.security.ContentPermissionChecker;
import org.sonatype.nexus.repository.security.VariableResolverAdapterManager;

import org.elasticsearch.plugins.Plugin;

/**
 * {@link PluginLocator} for {@link ContentAuthPlugin}. Also responsible for setting some required objects into static
 * fields on {@link ContentAuthPlugin} as the instantiation of the latter occurs outside of our purview within ES.
 *
 * @since 3.1
 */
@Named
@Singleton
public class ContentAuthPluginLocator
    implements PluginLocator
{
  @Inject
  public ContentAuthPluginLocator(final ContentPermissionChecker contentPermissionChecker,
                                  final VariableResolverAdapterManager variableResolverAdapterManager,
                                  final SearchSubjectHelper searchSubjectHelper,
                                  final RepositoryManager repositoryManager,
                                  @Named("${nexus.elasticsearch.contentAuthSleep:-false}") final boolean contentAuthSleep)
  {
    ContentAuthPlugin.setDependencies(contentPermissionChecker, variableResolverAdapterManager,
        searchSubjectHelper, repositoryManager, contentAuthSleep);
  }

  @Override
  public Class<? extends Plugin> pluginClass() {
    return ContentAuthPlugin.class;
  }
}
