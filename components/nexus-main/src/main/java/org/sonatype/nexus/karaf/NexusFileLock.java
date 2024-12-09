package org.sonatype.nexus.karaf;

import java.io.File;
import java.io.RandomAccessFile;
import java.lang.management.ManagementFactory;
import java.nio.channels.FileLock;

import org.apache.felix.utils.properties.Properties;
import org.apache.karaf.main.ConfigProperties;
import org.apache.karaf.main.lock.Lock;

/**
 * Nexus implementation of Karaf's {@link Lock} that exits this process if another process has the lock.
 * 
 * @since 3.0
 */
public class NexusFileLock
    implements Lock
{
  private final String dataDir;

  private final File file;

  private FileLock lock;

  private RandomAccessFile lockFile;

  public NexusFileLock(final Properties properties) {
    if (properties == null) {
      throw new NullPointerException();
    }

    this.dataDir = properties.getProperty(ConfigProperties.PROP_KARAF_DATA);
    this.file = new File(dataDir, "lock");
  }

  public synchronized boolean isAlive() {
    return lock != null && lock.isValid() && file.exists();
  }

  public synchronized boolean lock() {
    if (!doLock()) {
      // logging is not configured yet, so use console
      System.err.println("Nexus data directory already in use: " + dataDir);
      System.exit(-1);
    }
    return true;
  }

  private boolean doLock() {
    if (lock != null) {
      return true;
    }
    try {
      lockFile = new RandomAccessFile(file, "rws");
      lock = lockFile.getChannel().tryLock(0L, 1L, false);
      if (lock != null) {
        byte[] payload = ManagementFactory.getRuntimeMXBean().getName().getBytes("UTF-8");
        lockFile.setLength(0);
        lockFile.seek(0);
        lockFile.write(payload);
      }
    }
    catch (Exception e) {
      // logging is not configured yet, so use console
      System.err.println("Failed to write lock file: " + file.getAbsolutePath());
      e.printStackTrace();
      // handle it as null result
      lock = null;
    }
    finally {
      if (lock == null) {
        release();
        return false;
      }
    }
    return true;
  }

  public synchronized void release() {
    if (lock != null) {
      try {
        lock.close();
      }
      catch (Exception e) {
        // ignore
      }
      lock = null;
    }

    if (lockFile != null) {
      try {
        lockFile.close();
      }
      catch (Exception e) {
        // ignore
      }
      lockFile = null;
    }
  }
}
