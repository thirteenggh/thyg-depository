package org.sonatype.nexus.internal.backup.orient

import java.time.LocalDateTime

import org.sonatype.nexus.common.app.ApplicationDirectories
import org.sonatype.nexus.common.app.ApplicationVersion
import org.sonatype.nexus.orient.DatabaseManager
import org.sonatype.nexus.orient.DatabaseRestorer
import org.sonatype.nexus.orient.DatabaseServer

import spock.lang.Specification

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.notNullValue
import static org.hamcrest.Matchers.is

/**
 * Tests for {@link DatabaseBackupImpl}
 *
 */
class DatabaseBackupImplTest
    extends Specification
{

  DatabaseManager databaseManager = Mock()

  DatabaseServer databaseServer = Mock()

  DatabaseRestorer databaseRestorer = Mock()

  ApplicationDirectories applicationDirectories = Mock()

  ApplicationVersion applicationVersion = Mock()

  def 'checks that a file can be accessed'() {
    databaseRestorer.isRestoreFromLocation(_) >> false
    applicationVersion.getVersion() >> "3.4.1"
    def databaseBackup = new DatabaseBackupImpl(databaseServer, databaseManager, databaseRestorer, applicationDirectories, applicationVersion)

    when: 'using a temp folder and a temp file'
      File temp = databaseBackup.checkTarget(System.getProperty("java.io.tmpdir"), "test", LocalDateTime.now())

    then: 'the file should be intact'
      assertThat(temp, notNullValue())
      assertThat(temp.delete(), is(true))
      1 * applicationDirectories.getWorkDirectory(_) >> { String name -> new File(name) }
  }

  def 'restore to location is disallowed'() {
    databaseRestorer.isRestoreFromLocation(_) >> true
    applicationVersion.getVersion() >> "3.4.1"
    def databaseBackup = new DatabaseBackupImpl(databaseServer, databaseManager, databaseRestorer, applicationDirectories, applicationVersion)

    when: 'the target is checked'
      databaseBackup.checkTarget(".", "test", LocalDateTime.now())

    then: 'an exception is thrown'
      thrown(IllegalArgumentException)
  }
}
