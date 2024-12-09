package org.sonatype.nexus.internal.security.model;

import org.sonatype.nexus.datastore.api.IdentifiedDataAccess;

/**
 * {@link CUserData} access.
 *
 * @since 3.21
 */
public interface CUserDAO
    extends IdentifiedDataAccess<CUserData>
{
  // no additional behaviour...
}
