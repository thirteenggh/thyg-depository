package org.sonatype.nexus.common.app;

/**
 * Provide information about the application version.
 *
 * Accessors will return {@link ApplicationVersionSupport#UNKNOWN} if unable to determine value.
 *
 * @since 3.0
 */
public interface ApplicationVersion
{
  /**
   * Returns the version.
   */
  String getVersion();

  /**
   * Returns the edition.
   */
  String getEdition();

  /**
   * Returns the edition and version suitable for branded display.
   */
  String getBrandedEditionAndVersion();

  /**
   * Returns the build revision.
   */
  String getBuildRevision();

  /**
   * Returns the build timestamp.
   */
  String getBuildTimestamp();

  /**
   * Returns the minimum Nexus 2 version allowed for migration compatibility with this build.
   *
   * @since 3.4
   */
  String getNexus2CompatibleVersion();
}
