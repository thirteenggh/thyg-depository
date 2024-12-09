package org.sonatype.nexus.internal.orient;

import org.sonatype.nexus.orient.DatabaseManager;
import org.sonatype.nexus.orient.DatabaseServer;
import org.sonatype.nexus.orient.EncryptedRecordIdObfuscator;
import org.sonatype.nexus.orient.RecordIdObfuscator;
import org.sonatype.nexus.transaction.RetryController;

import com.google.inject.AbstractModule;

/**
 * Orient module.
 *
 * @since 3.0
 */
public class OrientModule
  extends AbstractModule
{
  @Override
  protected void configure() {
    // configure default implementations
    bind(DatabaseServer.class).to(DatabaseServerImpl.class);
    bind(DatabaseManager.class).to(DatabaseManagerImpl.class);
    bind(RecordIdObfuscator.class).to(EncryptedRecordIdObfuscator.class);
    bind(RetryController.class).toInstance(RetryController.INSTANCE);
  }
}
