package org.sonatype.nexus.script.plugin.internal

import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

import org.sonatype.nexus.common.app.ManagedLifecycle
import org.sonatype.nexus.common.event.EventManager
import org.sonatype.nexus.common.stateguard.Guarded
import org.sonatype.nexus.common.stateguard.StateGuardLifecycleSupport
import org.sonatype.nexus.script.Script
import org.sonatype.nexus.script.ScriptCreatedEvent
import org.sonatype.nexus.script.ScriptDeletedEvent
import org.sonatype.nexus.script.ScriptManager
import org.sonatype.nexus.script.ScriptUpdatedEvent

import com.google.common.collect.ImmutableList
import groovy.transform.CompileStatic

import static org.sonatype.nexus.common.app.ManagedLifecycle.Phase.SERVICES
import static org.sonatype.nexus.common.stateguard.StateGuardLifecycleSupport.State.STARTED

/**
 * Default {@link ScriptManager}.
 *
 * @since 3.0
 */
@Named
@ManagedLifecycle(phase = SERVICES)
@Singleton
@CompileStatic
class ScriptManagerImpl
    extends StateGuardLifecycleSupport
    implements ScriptManager
{
  @Inject
  EventManager eventManager

  @Inject
  ScriptStore scriptStore

  @Inject
  @Named('${nexus.scripts.allowCreation:-false}')
  boolean allowCreation

  @Override
  @Guarded(by = STARTED)
  Iterable<Script> browse() {
    return ImmutableList.copyOf(scriptStore.list())
  }

  @Override
  @Guarded(by = STARTED)
  Script get(final String name) {
    return scriptStore.get(name)
  }

  @Override
  @Guarded(by = STARTED)
  Script create(final String name, final String content, final String type) {
    validateCreationIsAllowed()

    Script script = scriptStore.newScript()
    script.name = name
    script.content = content
    script.type = type
    scriptStore.create(script)
    eventManager.post(new ScriptCreatedEvent(script))
    return script
  }

  @Override
  @Guarded(by = STARTED)
  Script update(final String name, final String content) {
    validateCreationIsAllowed()

    Script script = scriptStore.get(name)
    if (script == null) {
      return null
    }
    script.content = content
    scriptStore.update(script)
    eventManager.post(new ScriptUpdatedEvent(script))
    return script
  }

  @Override
  @Guarded(by = STARTED)
  void delete(final String name) {
    Script script = scriptStore.get(name)
    if (script != null) {
      scriptStore.delete(script)
      eventManager.post(new ScriptDeletedEvent(script))
    }
  }

  void validateCreationIsAllowed() {
    if(!allowCreation){
      throw new ScriptingDisabledException('Creating and updating scripts is disabled')
    }
  }
}
