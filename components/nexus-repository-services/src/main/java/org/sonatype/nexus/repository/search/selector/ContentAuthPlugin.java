package org.sonatype.nexus.repository.search.selector;

import org.sonatype.nexus.repository.manager.RepositoryManager;
import org.sonatype.nexus.repository.search.query.SearchSubjectHelper;
import org.sonatype.nexus.repository.security.ContentPermissionChecker;
import org.sonatype.nexus.repository.security.VariableResolverAdapterManager;

import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.script.ScriptModule;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Elasticsearch plugin that exposes a content auth function for working with content selectors in search. Also holds
 * on to necessary instances as static variables that should be set by the matching {@link ContentAuthPluginLocator},
 * as this class is instantiated by ES, not by us.
 *
 * @since 3.1
 */
public class ContentAuthPlugin
    extends Plugin
{
  private static ContentPermissionChecker contentPermissionChecker;
  private static VariableResolverAdapterManager variableResolverAdapterManager;
  private static SearchSubjectHelper searchSubjectHelper;
  private static RepositoryManager repositoryManager;
  private static boolean contentAuthSleep;

  public ContentAuthPlugin() {
    checkNotNull(contentPermissionChecker);
    checkNotNull(variableResolverAdapterManager);
    checkNotNull(searchSubjectHelper);
    checkNotNull(repositoryManager);
  }

  @Override
  public String name() {
    return "content-auth-plugin";
  }

  @Override
  public String description() {
    return "ES plugin for working with content selectors";
  }

  public void onModule(final ScriptModule module) {
    module.registerScript(ContentAuthPluginScript.NAME, ContentAuthPluginScriptFactory.class);
  }

  public static void setDependencies(final ContentPermissionChecker contentPermissionChecker,
                                     final VariableResolverAdapterManager variableResolverAdapterManager,
                                     final SearchSubjectHelper searchSubjectHelper,
                                     final RepositoryManager repositoryManager,
                                     final boolean contentAuthSleep)
  {
    ContentAuthPlugin.contentPermissionChecker = checkNotNull(contentPermissionChecker);
    ContentAuthPlugin.variableResolverAdapterManager = checkNotNull(variableResolverAdapterManager);
    ContentAuthPlugin.searchSubjectHelper = checkNotNull(searchSubjectHelper);
    ContentAuthPlugin.repositoryManager = checkNotNull(repositoryManager);
    ContentAuthPlugin.contentAuthSleep = contentAuthSleep;
  }

  public static ContentPermissionChecker getContentPermissionChecker() {
    return contentPermissionChecker;
  }

  public static VariableResolverAdapterManager getVariableResolverAdapterManager() {
    return variableResolverAdapterManager;
  }

  public static SearchSubjectHelper getSearchSubjectHelper() {
    return searchSubjectHelper;
  }

  public static RepositoryManager getRepositoryManager() {
    return repositoryManager;
  }

  public static boolean getContentAuthSleep() {
    return contentAuthSleep;
  }
}
