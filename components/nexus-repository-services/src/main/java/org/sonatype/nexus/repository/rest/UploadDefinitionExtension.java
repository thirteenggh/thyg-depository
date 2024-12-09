package org.sonatype.nexus.repository.rest;

import org.sonatype.nexus.repository.upload.UploadFieldDefinition;

/**
 * Extension point interface which provides a mechanism for contributing an {@link UploadFieldDefinition}
 *
 * @since 3.10
 */
public interface UploadDefinitionExtension
{
  UploadFieldDefinition contribute();
}
