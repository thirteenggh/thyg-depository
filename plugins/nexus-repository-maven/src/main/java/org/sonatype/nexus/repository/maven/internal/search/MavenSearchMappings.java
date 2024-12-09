package org.sonatype.nexus.repository.maven.internal.search;

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
@Named("maven2")
@Singleton
public class MavenSearchMappings
    extends ComponentSupport
    implements SearchMappings
{
  private static final List<SearchMapping> MAPPINGS = ImmutableList.of(
      new SearchMapping("maven.groupId", "attributes.maven2.groupId", "Maven groupId"),
      new SearchMapping("maven.artifactId", "attributes.maven2.artifactId", "Maven artifactId"),
      new SearchMapping("maven.baseVersion", "attributes.maven2.baseVersion", "Maven base version"),
      new SearchMapping("maven.extension", "assets.attributes.maven2.extension", "Maven extension of component's asset"),
      new SearchMapping("maven.classifier", "assets.attributes.maven2.classifier", "Maven classifier of component's asset")
  );

  @Override
  public Iterable<SearchMapping> get() {
    return MAPPINGS;
  }
}
