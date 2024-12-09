package org.sonatype.nexus.rest

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.builder.Builder

/**
 * An object to hold pages of XOs for use in the REST API.
 *
 * @since 3.3
 */
@CompileStatic
@Builder
@EqualsAndHashCode
class Page<T>
{
  List<T> items

  String continuationToken

  Page(@JsonProperty('items') final List<T> items,
       @JsonProperty('continuationToken') final String continuationToken) {
    this.items = items
    this.continuationToken = continuationToken
  }
}
