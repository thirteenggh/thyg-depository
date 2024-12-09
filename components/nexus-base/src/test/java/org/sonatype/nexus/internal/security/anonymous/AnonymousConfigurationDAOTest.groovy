package org.sonatype.nexus.internal.security.anonymous

import org.sonatype.nexus.content.testsuite.groups.SQLTestGroup
import org.sonatype.nexus.datastore.api.DataSession
import org.sonatype.nexus.testdb.DataSessionRule

import org.junit.Rule
import org.junit.experimental.categories.Category
import spock.lang.Specification

import static org.sonatype.nexus.datastore.api.DataStoreManager.CONFIG_DATASTORE_NAME

@Category(SQLTestGroup.class)
class AnonymousConfigurationDAOTest extends Specification
{
  @Rule
  DataSessionRule sessionRule = new DataSessionRule().access(AnonymousConfigurationDAO)

  DataSession session

  AnonymousConfigurationDAO dao

  void setup() {
    session = sessionRule.openSession(CONFIG_DATASTORE_NAME)
    dao = session.access(AnonymousConfigurationDAO)
  }

  void cleanup() {
    session.close()
  }

  def 'create read update delete'() {
    given: 'an anonymous configuration'
      def config = new AnonymousConfigurationData(enabled: true, userId: 'anon', realmName: 'local')
    when: 'it is created'
      dao.set(config)
    and: 'it is read back'
      def read = dao.get().orElse(null)
    then:
      read != null
      read.enabled
      read.userId == 'anon'
      read.realmName == 'local'
    when: 'it is updated'
      config.enabled = false
      config.userId = 'anonymous'
      config.realmName = 'remote'
      dao.set(config)
    and: 'it is read back'
      read = dao.get().orElse(null)
    then:
      read != null
      !read.enabled
      read.userId == 'anonymous'
      read.realmName == 'remote'
    when: 'it is deleted'
      dao.clear()
    and: 'it is read back'
      read = dao.get().orElse(null)
    then: 'it will be null'
      read == null
  }
}
