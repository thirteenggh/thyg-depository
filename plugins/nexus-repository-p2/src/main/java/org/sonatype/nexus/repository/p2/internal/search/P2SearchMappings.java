package org.sonatype.nexus.repository.p2.internal.search;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.repository.p2.internal.P2Format;
import org.sonatype.nexus.repository.rest.SearchMapping;
import org.sonatype.nexus.repository.rest.SearchMappings;

import com.google.common.collect.ImmutableList;
/**
 * @since 1.0.0
 *
 */
@Named(P2Format.NAME)
@Singleton
public class P2SearchMappings
    extends ComponentSupport
    implements SearchMappings
{
  private static final List<SearchMapping> MAPPINGS = ImmutableList.of(
      new SearchMapping("p2.pluginName", "attributes.p2.pluginName", "p2 plugin name")
  );

  @Override
  public Iterable<SearchMapping> get() {
    return MAPPINGS;
  }
}
