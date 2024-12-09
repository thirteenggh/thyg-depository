package org.sonatype.nexus.blobstore;

/**
 * Describes properties of blob store attribute files
 *
 * @since 3.15
 */
public interface AttributesLocation
{
  String getFileName();

  String getFullPath();
}
