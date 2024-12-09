package org.sonatype.nexus.quartz.internal;

import org.sonatype.nexus.datastore.api.DataAccess;

import org.quartz.impl.jdbcjobstore.JobStoreTX;

/**
 * Quartz data access object used to create the schema that {@link JobStoreTX} expects.
 *
 * @since 3.19
 */
public interface QuartzDAO
    extends DataAccess
{
}
