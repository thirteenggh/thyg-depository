package org.sonatype.nexus.repository.npm.internal.search;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.repository.rest.SearchMapping;
import org.sonatype.nexus.repository.rest.SearchMappings;

import com.google.common.collect.ImmutableList;

/**
 * @since 3.7
 */
@Named("npm")
@Singleton
public class NpmSearchMappings
    extends ComponentSupport
    implements SearchMappings
{
  private static final List<SearchMapping> MAPPINGS = ImmutableList.of(
      new SearchMapping("npm.scope", "group", "npm scope")
  );

  @Override
  public Iterable<SearchMapping> get() {
    return MAPPINGS;
  }
}
