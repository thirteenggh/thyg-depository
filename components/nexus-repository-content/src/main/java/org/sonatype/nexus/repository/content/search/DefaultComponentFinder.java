package org.sonatype.nexus.repository.content.search;

import java.util.Optional;
import java.util.stream.Stream;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.common.entity.EntityHelper;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.content.facet.ContentFacet;
import org.sonatype.nexus.repository.content.fluent.FluentComponent;
import org.sonatype.nexus.repository.content.fluent.FluentComponents;

import static com.google.common.base.Strings.nullToEmpty;
import static java.util.Optional.empty;

/**
 * @since 3.26
 */
@Named
@Singleton
public class DefaultComponentFinder
    extends ComponentSupport
    implements ComponentFinder
{
  @Override
  public Stream<FluentComponent> findComponentsByModel(
      final Repository repository,
      final String searchComponentId,
      final String namespace,
      final String name,
      final String version)
  {
    Optional<FluentComponent> component = empty();

    if (repository != null) {
      FluentComponents components = contentFacet(repository).components();

      if (searchComponentId != null) {
        component = components.find(EntityHelper.id(searchComponentId));
      }

      if (!component.isPresent()) {
        component = components
            .name(name)
            .namespace(nullToEmpty(namespace))
            .version(nullToEmpty(version))
            .find();
      }
    }

    return component.map(Stream::of).orElse(Stream.empty());
  }

  protected ContentFacet contentFacet(final Repository repository) {
    return repository.facet(ContentFacet.class);
  }
}
