package org.sonatype.nexus.repository.rest.internal;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.repository.rest.SearchMapping;
import org.sonatype.nexus.repository.rest.SearchMappings;

import com.google.common.collect.ImmutableList;

import static org.sonatype.nexus.repository.search.index.SearchConstants.IS_PRERELEASE_KEY;
import static org.sonatype.nexus.repository.search.index.SearchConstants.REPOSITORY_NAME;

/**
 * @since 3.7
 */
@Named("default")
@Singleton
public class DefaultSearchMappings
    extends ComponentSupport
    implements SearchMappings
{
  private static final List<SearchMapping> MAPPINGS = ImmutableList.of(
      new SearchMapping("q", "keyword", "Query by keyword"),
      new SearchMapping("repository", REPOSITORY_NAME, "Repository name"),
      new SearchMapping("format", "format", "Query by format"),
      new SearchMapping("group", "group.raw", "Component group"),
      new SearchMapping("name", "name.raw", "Component name"),
      new SearchMapping("version", "version", "Component version"),
      new SearchMapping("md5", "assets.attributes.checksum.md5", "Specific MD5 hash of component's asset"),
      new SearchMapping("sha1", "assets.attributes.checksum.sha1", "Specific SHA-1 hash of component's asset"),
      new SearchMapping("sha256", "assets.attributes.checksum.sha256", "Specific SHA-256 hash of component's asset"),
      new SearchMapping("sha512", "assets.attributes.checksum.sha512", "Specific SHA-512 hash of component's asset"),
      new SearchMapping("prerelease", IS_PRERELEASE_KEY, "Prerelease version flag")
  );

  @Override
  public Iterable<SearchMapping> get() {
    return MAPPINGS;
  }
}
