package org.sonatype.nexus.script.plugin.internal;

import java.util.List;

import javax.annotation.Nullable;

import org.sonatype.goodies.lifecycle.Lifecycle;
import org.sonatype.nexus.script.Script;


/**
 * Store for managing {@link Script} entities.
 * 
 * @since 3.0
 */
public interface ScriptStore
    extends Lifecycle
{

  /**
   * Create a new, unpopulated Script
   * @since 3.20
   */
  Script newScript();

  /**
   * @return all stored {@link Script}
   */
  List<Script> list();

  /**
   * @return {@link Script} with matching name
   */
  @Nullable
  Script get(String name);

  /**
   * Persist a new {@link Script}.
   */
  void create(Script script);

  /**
   * Update an existing {@link Script}.
   */
  void update(Script script);

  /**
   * Delete an existing {@link Script}.
   */
  void delete(Script script);
}
