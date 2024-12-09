package org.sonatype.nexus.repository.maintenance.internal;

import java.util.function.BooleanSupplier;

import org.sonatype.nexus.repository.Repository;

import org.joda.time.DateTime;

/**
 * Deletes all assets under a given path, that were created before a given timestamp, in a repository based on
 * a user's privileges.
 *
 * @since 3.15
 */
public interface DeleteFolderService
{
  void deleteFolder(Repository repository, String path, DateTime timestamp, BooleanSupplier cancelledCheck);
}
