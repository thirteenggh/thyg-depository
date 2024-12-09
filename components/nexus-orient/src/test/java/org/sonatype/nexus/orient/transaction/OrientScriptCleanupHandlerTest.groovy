package org.sonatype.nexus.orient.transaction

import com.orientechnologies.orient.core.db.ODatabase.STATUS
import com.orientechnologies.orient.core.db.ODatabaseDocumentInternal
import com.orientechnologies.orient.core.db.ODatabaseRecordThreadLocal
import spock.lang.Specification
import spock.lang.Unroll

class OrientScriptCleanupHandlerTest
    extends Specification
{

  @Unroll
  def 'it will close connections when an open connection exists after script execution'() {
    given: 'the handler and a database document'
      def handler = new OrientScriptCleanupHandler()
      def databaseDocument = Mock(ODatabaseDocumentInternal)
      databaseDocument.getStatus() >> status
      ODatabaseRecordThreadLocal.instance().set(databaseDocument)
    when: 'cleanup executes'
      handler.cleanup()
    then: 'close may be called'
      callClose * databaseDocument.close()

    where:
      status        | callClose
      STATUS.OPEN   | 1
      STATUS.CLOSED | 0
  }

  def 'it will do nothing if no open connection exists'() {
    given: 'the handler and no instance value'
      def handler = new OrientScriptCleanupHandler()
      ODatabaseRecordThreadLocal.instance().set(null)
    when: 'cleanup executes'
      handler.cleanup()
    then: 'nothing happens'
      noExceptionThrown()
  }
}
