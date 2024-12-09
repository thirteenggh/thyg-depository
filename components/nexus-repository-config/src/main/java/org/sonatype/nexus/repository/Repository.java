package org.sonatype.nexus.repository;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.sonatype.nexus.repository.config.Configuration;

/**
 * Repository.
 *
 * @since 3.0
 */
public interface Repository
{
  /**
   * Returns the type of the repository.
   */
  Type getType();

  /**
   * Returns the format of the repository.
   */
  Format getFormat();

  /**
   * Returns the unique name of the repository.
   */
  String getName();

  /**
   * Validate repository configuration.
   */
  void validate(Configuration configuration) throws Exception;

  /**
   * Initialize the repository.
   *
   * Called when a new repository is created or a repository is restored from persistent storage on startup.
   */
  void init(Configuration configuration) throws Exception;

  /**
   * Update the repository.
   *
   * Called when the repository configuration changes.  Repository has already been initialized.
   */
  void update(Configuration configuration) throws Exception;

  /**
   * Start the repository.
   *
   * Repository has already been initialized or updated.
   */
  void start() throws Exception;

  /**
   * Stop the repository.
   *
   * Repository must have been previously started.  Repository is stopped before applying {@link #update}.
   */
  void stop() throws Exception;

  /**
   * Delete the repository and remove all persistent knowledge about repository and its contents.
   *
   * Repository must have been previously stopped.
   */
  void delete() throws Exception;

  /**
   * Destroy the repository.
   *
   * Allows repository to clean up resources.  This is different than {@link #delete}.
   *
   * Repository is stopped before destruction.
   */
  void destroy() throws Exception;

  /**
   * Returns the configuration entity for the repository.
   *
   * Only available after {@link #init(Configuration)} lifecycle-operation.
   */
  Configuration getConfiguration();

  /**
   * Attach a facet to the repository.
   *
   * Only allowed before repository {@link #init(Configuration)} lifecycle-operation.
   */
  void attach(Facet facet) throws Exception;

  /**
   * Returns a facet instance for the given type.
   *
   * @throws MissingFacetException Request facet type was not previously attached.
   */
  @Nonnull
  <T extends Facet> T facet(Class<T> type) throws MissingFacetException;

  /**
   * Returns an {@link Optional} facet instance for the given type.
   */
  @Nonnull
  <T extends Facet> Optional<T> optionalFacet(Class<T> type);

  /**
   * Returns the URL for the root of the repository, without its trailing slash.
   */
  String getUrl();
}
