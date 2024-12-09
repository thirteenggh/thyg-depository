package org.sonatype.nexus.ssl.internal.geronimo;

import org.sonatype.nexus.ssl.KeystoreException;

/**
 * Exception indicating that the keystore you tried to do something with is
 * locked.  It must be unlocked before it can be used in this way.
 *
 * @version $Rev$ $Date$
 */
public class KeystoreIsLocked
    extends KeystoreException
{

  public KeystoreIsLocked(String message) {
    super(message);
  }

  public KeystoreIsLocked(String message, Throwable cause) {
    super(message, cause);
  }
}
