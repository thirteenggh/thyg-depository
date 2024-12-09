package org.sonatype.nexus.repository.attributes;

import org.sonatype.nexus.common.collect.ImmutableNestedAttributesMap;
import org.sonatype.nexus.repository.AttributeChange;
import org.sonatype.nexus.repository.Facet;

/**
 * Services for accessing and manipulating repository-level metadata attributes.
 *
 * @since 3.0
 */
@Facet.Exposed
public interface AttributesFacet
    extends Facet
{
  /**
   * Returns an immutable view of the Repository's attributes.
   */
  ImmutableNestedAttributesMap getAttributes();

  /**
   * Modifies the Repository's attributes.
   */
  void modifyAttributes(AttributeChange change);
}
