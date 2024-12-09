package org.sonatype.nexus.repository.search.query;

import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.manager.RepositoryManager;
import org.sonatype.nexus.repository.security.ContentPermissionChecker;
import org.sonatype.nexus.repository.security.VariableResolverAdapterManager;

import org.elasticsearch.search.SearchHit;

import static org.sonatype.nexus.repository.search.index.SearchConstants.FORMAT;
import static org.sonatype.nexus.repository.search.index.SearchConstants.GROUP;
import static org.sonatype.nexus.repository.search.index.SearchConstants.NAME;
import static org.sonatype.nexus.repository.search.index.SearchConstants.VERSION;

/**
 * @since 3.14
 */
@Singleton
@Named
public class DefaultSearchResultComponentGenerator
  extends SearchResultComponentGeneratorSupport
{
  public static final String DEFAULT_SEARCH_RESULT_COMPONENT_GENERATOR_KEY = "default";

  @Inject
  public DefaultSearchResultComponentGenerator(final VariableResolverAdapterManager variableResolverAdapterManager,
                                               final RepositoryManager repositoryManager,
                                               final ContentPermissionChecker contentPermissionChecker)
  {
    super(variableResolverAdapterManager, repositoryManager, contentPermissionChecker);
  }

  @Override
  public SearchResultComponent from(final SearchHit hit, final Set<String> componentIdSet) {
    SearchResultComponent component = new SearchResultComponent();
    final Map<String, Object> source = hit.getSource();

    component.setId(hit.getId());
    component.setRepositoryName(getPrivilegedRepositoryName(source));
    component.setGroup((String) source.get(GROUP));
    component.setName((String) source.get(NAME));
    component.setVersion((String) source.get(VERSION));
    component.setFormat((String) source.get(FORMAT));

    return component;
  }
}
