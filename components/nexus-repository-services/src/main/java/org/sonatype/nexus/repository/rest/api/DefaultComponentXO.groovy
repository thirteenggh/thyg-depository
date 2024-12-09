package org.sonatype.nexus.repository.rest.api

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.builder.Builder

import static java.util.Collections.emptyMap

/**
 * Component transfer object for REST APIs.
 *
 * @since 3.8
 */
@CompileStatic
@Builder
@ToString(includePackage = false, includeNames = true)
@EqualsAndHashCode(includes = ['id'])
class DefaultComponentXO
  implements ComponentXO
{
  String id

  String group

  String name

  String version

  String repository

  String format

  List<AssetXO> assets

  /**
   * Provides extra attributes for the JSON payload. Implementers must use @JsonAnyGetter.
   * @since 3.8
   */
  @Override
  Map<String, Object> getExtraJsonAttributes() {
    return emptyMap()
  }
}
