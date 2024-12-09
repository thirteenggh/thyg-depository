package org.sonatype.nexus.common.entity;

/**
 * Mix-in for entities that are uniquely named.
 *
 * @since 3.19
 */
public interface HasName
{
  String getName();

  void setName(String name);
}
