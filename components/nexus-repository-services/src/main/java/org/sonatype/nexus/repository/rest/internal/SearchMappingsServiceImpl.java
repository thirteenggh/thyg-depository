package org.sonatype.nexus.repository.rest.internal;

import java.util.Collection;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.repository.rest.SearchMapping;
import org.sonatype.nexus.repository.rest.SearchMappings;
import org.sonatype.nexus.repository.rest.SearchMappingsService;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

import static com.google.common.base.Preconditions.checkNotNull;

@Named
@Singleton
public class SearchMappingsServiceImpl
    extends ComponentSupport
    implements SearchMappingsService
{
  private static final String DEFAULT = "default";

  private final Collection<SearchMapping> searchMappings;

  @Inject
  public SearchMappingsServiceImpl(final Map<String, SearchMappings> searchMappings) {
    this.searchMappings = collectMappings(checkNotNull(searchMappings));
  }

  private static Collection<SearchMapping> collectMappings(final Map<String, SearchMappings> searchMappings) {
    final Builder<SearchMapping> builder = ImmutableList.builder();

    // put the default mappings in first
    final SearchMappings defaultMappings = searchMappings.get(DEFAULT);
    if (defaultMappings != null) {
      builder.addAll(defaultMappings.get());
    }

    // add the rest of the mappings
    searchMappings.keySet().stream()
        .filter(key -> !DEFAULT.equals(key))
        .sorted()
        .forEach(key -> builder.addAll(searchMappings.get(key).get()));

    return builder.build();
  }

  @Override
  public Iterable<SearchMapping> getAllMappings() {
    return searchMappings;
  }
}
