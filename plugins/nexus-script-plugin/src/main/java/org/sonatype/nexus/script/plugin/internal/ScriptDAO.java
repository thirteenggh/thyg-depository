package org.sonatype.nexus.script.plugin.internal;

import org.sonatype.nexus.datastore.api.NamedDataAccess;

/**
 * {@link ScriptData} access.
 *
 * @since 3.21
 */
public interface ScriptDAO
    extends NamedDataAccess<ScriptData>
{
  // no additional behaviour
}
