package org.sonatype.nexus.repository.content;

/**
 * Each component represents a unique logical coordinate in a repository.
 *
 * @since 3.20
 * @see Asset
 */
public interface Component
    extends RepositoryContent
{
  /**
   * The component namespace; empty string if the component doesn't have a namespace.
   */
  String namespace();

  /**
   * The component name.
   */
  String name();

  /**
   * The component kind.
   *
   * @since 3.25
   */
  String kind();

  /**
   * The component version; empty string if the component doesn't have a version.
   */
  String version();
}
