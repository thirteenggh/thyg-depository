package org.sonatype.nexus.repository.rest.internal;

import java.util.Collection;
import java.util.List;
import java.util.stream.StreamSupport;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.rest.SearchMapping;
import org.sonatype.nexus.repository.rest.SearchMappingsService;
import org.sonatype.nexus.repository.rest.internal.resources.SearchResource;
import org.sonatype.nexus.swagger.ParameterContributor;

import com.google.common.collect.ImmutableList;
import io.swagger.models.HttpMethod;
import io.swagger.models.parameters.QueryParameter;

import static io.swagger.models.HttpMethod.GET;
import static java.util.stream.Collectors.toList;

/**
 * @since 3.7
 */
@Named
@Singleton
public class SearchParameterContributor
    extends ParameterContributor<QueryParameter>
{
  private static final List<HttpMethod> HTTP_METHODS = ImmutableList.of(GET);

  private static final List<String> PATHS = ImmutableList.of(
      SearchResource.RESOURCE_URI,
      SearchResource.RESOURCE_URI + SearchResource.SEARCH_ASSET_URI,
      SearchResource.RESOURCE_URI + SearchResource.SEARCH_AND_DOWNLOAD_URI
  );

  @Inject
  public SearchParameterContributor(final SearchMappingsService searchMappings) {
    super(HTTP_METHODS, PATHS, transformMappings(searchMappings.getAllMappings()));
  }

  private static Collection<QueryParameter> transformMappings(final Iterable<SearchMapping> searchMappings) { // NOSONAR
    return StreamSupport.stream(searchMappings.spliterator(), false)
        .map(m -> new QueryParameter().name(m.getAlias()).type("string").description(m.getDescription()))
        .collect(toList());
  }
}
