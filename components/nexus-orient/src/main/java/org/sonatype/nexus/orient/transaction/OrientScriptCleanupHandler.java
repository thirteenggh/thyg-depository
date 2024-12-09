package org.sonatype.nexus.orient.transaction;

import javax.annotation.Priority;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.common.script.ScriptCleanupHandler;

import com.orientechnologies.orient.core.db.ODatabase.STATUS;
import com.orientechnologies.orient.core.db.ODatabaseRecordThreadLocal;
import com.orientechnologies.orient.core.db.document.ODatabaseDocument;

/**
 * Orient handler that cleans up open transactions
 *
 * @since 3.17
 */
@Named("orient")
@Priority(Integer.MAX_VALUE)
@Singleton
public class OrientScriptCleanupHandler
    extends ComponentSupport
    implements ScriptCleanupHandler
{
  @Override
  public void cleanup(final String context) {
    ODatabaseDocument database = ODatabaseRecordThreadLocal.instance().getIfDefined();
    if (database != null && database.getStatus() == STATUS.OPEN) {
      database.close();
      log.warn("{} left a database connection open. Any opened connections should also be explicitly closed.", context);
    }
  }
}
