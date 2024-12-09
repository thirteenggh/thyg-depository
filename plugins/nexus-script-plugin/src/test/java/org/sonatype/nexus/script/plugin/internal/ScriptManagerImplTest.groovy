package org.sonatype.nexus.script.plugin.internal

import org.sonatype.nexus.common.event.EventManager
import org.sonatype.nexus.script.plugin.internal.orient.OrientScript

import spock.lang.Specification

class ScriptManagerImplTest extends Specification
{
  ScriptManagerImpl scriptManager
  EventManager eventManager
  ScriptStore scriptStore

  def setup(){
    eventManager = Mock(EventManager)
    scriptStore = Mock(ScriptStore)

    scriptManager = new ScriptManagerImpl()
    scriptManager.eventManager = eventManager
    scriptManager.scriptStore = scriptStore
  }

  def 'scripts can be created if allowCreation is true'(){
    given: 'allowCreation set to true'
      scriptManager.allowCreation = true
      scriptStore.newScript() >> new OrientScript()

    when: 'a script is attempted to be created'
      scriptManager.create("new script", "some content", "groovy")

    then:
      1 * scriptStore.create(_)
      1 * eventManager.post(_)
  }

  def 'scripts can be updated if allowCreation is true'(){
    given: 'allowCreation set to true'
      scriptManager.allowCreation = true
      scriptStore.get(_) >> new OrientScript()

    when: 'a script is attempted to be created'
      scriptManager.update("existing script", "some new content")

    then:
      1 * scriptStore.update(_)
      1 * eventManager.post(_)
  }

  def 'scripts cannot be created if allowCreation is false'(){
    given:
      scriptManager.allowCreation = false
    when:
      scriptManager.create("new script", "some content", "groovy")

    then:
      thrown(ScriptingDisabledException)
  }

  def 'scripts cannot be updated if allowCreation is false'() {
    given:
      scriptManager.allowCreation = false

    when:
      scriptManager.update("existing script", "some content")

    then:
      thrown(ScriptingDisabledException)
  }
}
