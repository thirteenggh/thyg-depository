package org.sonatype.nexus.internal.security.apikey;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.scheduling.Cancelable;
import org.sonatype.nexus.scheduling.TaskSupport;
import org.sonatype.nexus.security.authc.apikey.ApiKeyStore;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Purge orphaned API keys task.
 *
 * @since 3.0
 * @see ApiKeyStore#purgeApiKeys()
 */
@Named
public class PurgeApiKeysTask
    extends TaskSupport
    implements Cancelable
{
  private final ApiKeyStore store;

  @Inject
  public PurgeApiKeysTask(final ApiKeyStore store) {
    this.store = checkNotNull(store);
  }

  @Override
  protected Void execute() throws Exception {
    store.purgeApiKeys();
    return null;
  }

  @Override
  public String getMessage() {
    return "Deleting orphaned API keys";
  }
}
