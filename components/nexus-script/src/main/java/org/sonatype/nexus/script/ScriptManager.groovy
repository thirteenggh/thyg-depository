package org.sonatype.nexus.script

import javax.annotation.Nullable

import org.sonatype.goodies.lifecycle.Lifecycle

/**
 * Script manager.
 *
 * @since 3.0
 */
interface ScriptManager
    extends Lifecycle
{
  static final String DEFAULT_TYPE = 'groovy'
  
  Iterable<Script> browse()

  @Nullable
  Script get(String name)

  Script create(String name, String content, String type)

  Script update(String name, String content)

  void delete(String name)
  
}
