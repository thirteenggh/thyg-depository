package org.sonatype.nexus.rest;

/**
 * Constants relating to REST APIs.
 *
 * @since 3.4
 */
public class APIConstants
{
  private APIConstants() {
  }

  public static final String V1_API_PREFIX = "/v1";

  /**
   * @deprecated beta prefix is being phased out, prefer starting new APIs with {@link #V1_API_PREFIX} instead
   */
  @Deprecated
  public static final String BETA_API_PREFIX = "/beta";

  public static final String INTERNAL_API_PREFIX = "/internal";
}
