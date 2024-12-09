package org.sonatype.nexus.internal.log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.common.log.LoggerLevel;

/**
 * In-memory {@link LoggerOverrides}.
 */
public class MemoryLoggerOverrides
  extends ComponentSupport
  implements LoggerOverrides
{
  private final Map<String, LoggerLevel> backing = new HashMap<>();

  public Map<String, LoggerLevel> getBacking() {
    return backing;
  }

  @Override
  public void load() {
    // empty
  }

  @Override
  public void save() {
    // empty
  }

  @Override
  public void reset() {
    backing.clear();
  }

  @Override
  public void set(final String name, final LoggerLevel level) {
    backing.put(name, level);
  }

  @Override
  @Nullable
  public LoggerLevel get(final String name) {
    return backing.get(name);
  }

  @Override
  @Nullable
  public LoggerLevel remove(final String name) {
    return backing.remove(name);
  }

  @Override
  public boolean contains(final String name) {
    return backing.containsKey(name);
  }

  @Override
  public Iterator<Entry<String, LoggerLevel>> iterator() {
    return backing.entrySet().iterator();
  }
}
