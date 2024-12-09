package org.sonatype.nexus.script.plugin.internal

import org.sonatype.nexus.content.testsuite.groups.SQLTestGroup
import org.sonatype.nexus.datastore.api.DataSession
import org.sonatype.nexus.script.Script
import org.sonatype.nexus.testdb.DataSessionRule

import org.junit.Rule
import org.junit.experimental.categories.Category
import spock.lang.Specification

import static org.sonatype.nexus.datastore.api.DataStoreManager.CONFIG_DATASTORE_NAME

@Category(SQLTestGroup.class)
class ScriptDAOTest
    extends Specification
{
  @Rule
  DataSessionRule sessionRule = new DataSessionRule().access(ScriptDAO)

  DataSession session

  ScriptDAO dao

  void setup() {
    session = sessionRule.openSession(CONFIG_DATASTORE_NAME)
    dao = session.access(ScriptDAO)
  }

  void cleanup() {
    session.close()
  }

  def 'create read update delete'() {
    given: 'a Script'
      Script script = new ScriptData(name: 'foo', content: "log.info('hello')")
    when: 'the script is stored'
      dao.create(script)
    and: 'it is read back'
      Script read = dao.read(script.name).orElse(null)
    then: 'the read value matches the original'
      read.name == script.name
      read.type == script.type
      read.content == script.content
    when: 'it is updated'
      script.content = "log.info('world')"
      dao.update(script)
    and: 'it is read back'
      Script update = dao.read(script.name).orElse(null)
    then: 'the read value matches the update'
      update.name == script.name
      update.type == script.type
      update.content == script.content
    when: 'it is deleted'
      dao.delete(script.name)
    then: 'no script is found by that name'
      !dao.read(script.name).isPresent()
  }
}
