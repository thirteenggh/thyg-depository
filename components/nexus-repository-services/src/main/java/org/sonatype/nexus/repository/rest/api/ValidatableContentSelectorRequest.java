package org.sonatype.nexus.repository.rest.api;

/**
 * A content selector request that should be validated
 *
 * @since 3.19
 */
public interface ValidatableContentSelectorRequest
{
  String getExpression();
}
