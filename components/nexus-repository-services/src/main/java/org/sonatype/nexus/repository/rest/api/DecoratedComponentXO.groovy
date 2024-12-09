package org.sonatype.nexus.repository.rest.api

import org.sonatype.nexus.common.decorator.DecoratedObject

import com.fasterxml.jackson.annotation.JsonIgnore
import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.builder.Builder

/**
 * Base abstract decorator for the {@link ComponentXO} class
 *
 * @since 3.8
 */
@CompileStatic
@Builder
@ToString(includePackage = false, includeNames = true)
@EqualsAndHashCode(includes = ['id'])
abstract class DecoratedComponentXO
    implements ComponentXO, DecoratedObject<ComponentXO>
{
  @Delegate
  protected final ComponentXO componentXO

  DecoratedComponentXO(ComponentXO componentXO) {
    this.componentXO = componentXO
  }

  @Override
  @JsonIgnore
  ComponentXO getWrappedObject() {
    return componentXO
  }

  /**
   * Get the additional attributes from this decorated object to add to the json payload. Variables in the extending
   * class should have the @JsonIgnore annotation and expose the data via this method.
   * @return
   */
  @JsonIgnore
  abstract Map<String, Object> getDecoratedExtraJsonAttributes()

  @Override
  final Map<String, Object> getExtraJsonAttributes() {
    return componentXO.getExtraJsonAttributes() + getDecoratedExtraJsonAttributes()
  }
}
