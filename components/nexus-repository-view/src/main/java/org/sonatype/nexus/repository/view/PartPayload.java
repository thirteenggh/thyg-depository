package org.sonatype.nexus.repository.view;

import javax.annotation.Nullable;

/**
 * Multipart payload.
 *
 * @since 3.1
 */
public interface PartPayload
    extends Payload
{
  /**
   * Returns the original name of this file as provided by the client.
   */
  @Nullable
  String getName();

  /**
   * Returns the name of the form field.
   */
  String getFieldName();

  /**
   * Returns true for form fields and false for files.
   */
  boolean isFormField();
}
