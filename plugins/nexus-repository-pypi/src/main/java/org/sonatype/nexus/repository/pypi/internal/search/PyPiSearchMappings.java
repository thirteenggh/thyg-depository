package org.sonatype.nexus.repository.pypi.internal.search;

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
@Named("pypi")
@Singleton
public class PyPiSearchMappings
    extends ComponentSupport
    implements SearchMappings
{
  private static final List<SearchMapping> MAPPINGS = ImmutableList.of(
      new SearchMapping("pypi.classifiers", "assets.attributes.pypi.classifiers", "PyPI classifiers"),
      new SearchMapping("pypi.description", "assets.attributes.pypi.description", "PyPI description"),
      new SearchMapping("pypi.keywords", "assets.attributes.pypi.keywords", "PyPI keywords"),
      new SearchMapping("pypi.summary", "assets.attributes.pypi.summary", "PyPI summary")
  );

  @Override
  public Iterable<SearchMapping> get() {
    return MAPPINGS;
  }
}
