package org.sonatype.nexus.orient.internal

import org.sonatype.nexus.common.app.ApplicationDirectories
import org.sonatype.nexus.common.node.NodeAccess

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

/**
 * Tests for {@link DatabaseRestorerImpl}
 *
 */
class DatabaseRestorerImplTest
    extends Specification
{

  @Rule
  TemporaryFolder tempFolder = new TemporaryFolder()

  File workdir

  ApplicationDirectories applicationDirectories = Mock()

  NodeAccess nodeAccess = Mock()

  ODatabaseDocumentTx db = Mock()

  DatabaseRestorerImpl databaseRestorer

  def setup() {
    workdir = tempFolder.newFolder('work')
    applicationDirectories.getWorkDirectory(_) >> { String path ->
      new File(workdir, path).with {
        mkdir()
        it
      }
    }
    nodeAccess.oldestNode >> true
    databaseRestorer = new DatabaseRestorerImpl(applicationDirectories, nodeAccess)
  }

  def 'restore with empty backup directory silently succeeds'() {
    when: 'an empty backup directory exists'
      def backupDir = new File(workdir, 'restore-from-backup')
      backupDir.mkdir()
      def didBackup = databaseRestorer.maybeRestoreDatabase(db, 'config')

    then: 'no restores occurs'
      didBackup == false
      0 * db.restore(*_)
  }

  def 'restore with one matching file in backup directory restores the db'() {
    when: 'a backup directory exists with one matching file plus some non-matching'
      def backupDir = new File(workdir, 'restore-from-backup')
      backupDir.mkdir()
      new File(backupDir, 'config-2016-11-23-09-20-40.bak') << 'backupdata'
      new File(backupDir, 'config-2016-11-23-09-20-40.') << 'bogusfile1'
      new File(backupDir, 'config2016-11-23-09-20-40.bak') << 'bogusfile2'

      def didBackup = databaseRestorer.maybeRestoreDatabase(db, 'config')

    then: 'the restore occurs'
      didBackup == true
      1 * db.restore(*_)
  }

  def 'restore with two matching files in backup directory throws exception'() {
    when: 'a backup directory exists with two matching files'
      def backupDir = new File(workdir, 'restore-from-backup')
      backupDir.mkdir()
      new File(backupDir, 'config-2016-11-23-09-20-40.bak') << 'backupdata1'
      new File(backupDir, 'config-2016-11-23-09-20-41.bak') << 'backupdata2'

      def didBackup = databaseRestorer.maybeRestoreDatabase(db, 'config')

    then: 'an exception is thrown'
      thrown(IllegalStateException)
      0 * db.restore(*_)
  }

  def 'restore is skipped when joining existing cluster even if backup exists'() {
    when: 'a backup directory exists'
      def backupDir = new File(workdir, 'restore-from-backup')
      backupDir.mkdir()
      new File(backupDir, 'config-2016-11-23-09-20-40.bak') << 'backupdata'

    and: 'we are joining an existing cluster'
      nodeAccess.oldestNode >> false

      def didBackup = databaseRestorer.maybeRestoreDatabase(db, 'config')

    then: 'the restore is skipped'
      didBackup == false
      0 * db.restore(*_)
  }
}
