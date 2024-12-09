package org.sonatype.nexus.internal.security.realm

import org.sonatype.nexus.content.testsuite.groups.SQLTestGroup
import org.sonatype.nexus.datastore.api.DataSession
import org.sonatype.nexus.testdb.DataSessionRule

import org.junit.Rule
import org.junit.experimental.categories.Category
import spock.lang.Specification

import static org.sonatype.nexus.datastore.api.DataStoreManager.CONFIG_DATASTORE_NAME

@Category(SQLTestGroup.class)
class RealmConfigurationDAOTest
    extends Specification
{
  @Rule
  DataSessionRule sessionRule = new DataSessionRule().access(RealmConfigurationDAO)

  DataSession session

  RealmConfigurationDAO dao

  void setup() {
    session = sessionRule.openSession(CONFIG_DATASTORE_NAME)
    dao = session.access(RealmConfigurationDAO)
  }

  void cleanup() {
    session.close()
  }

  def 'It will read and write a single realm configuration'() {
    given: 'an item'
      def config = new RealmConfigurationData()
      config.setRealmNames(["hello", "world"])

    when: 'inserted'
      dao.set(config)

    and: 'it is read'
      def readEntity = dao.get().orElse(null)

    then: 'it is persisted'
      assert readEntity != null
      assert readEntity.realmNames.size() == 2
      assert readEntity.realmNames.contains('hello')
      assert readEntity.realmNames.contains('world')

    when: 'it is updated'
      config.setRealmNames(['foo', 'bar'])
      dao.set(config)

    and: 'it is read back'
      readEntity = dao.get().orElse(null)

    then: 'it was updated'
      assert readEntity != null
      assert readEntity.realmNames.size() == 2
      assert readEntity.realmNames.contains('foo')
      assert readEntity.realmNames.contains('bar')
  }
}
