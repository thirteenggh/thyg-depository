package org.sonatype.nexus.upgrade.internal.orient;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.sonatype.nexus.common.entity.AbstractEntity;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The persisted versions of models that are clustered.
 * 
 * @since 3.1
 */
public class ClusteredModelVersions
    extends AbstractEntity
    implements Iterable<Map.Entry<String, String>>
{
  private Map<String, String> modelVersions = new HashMap<>();

  private boolean dirty;

  public Map<String, String> getModelVersions() {
    return modelVersions;
  }

  public void setModelVersions(Map<String, String> modelVersions) {
    this.modelVersions = checkNotNull(modelVersions);
  }

  public String get(final String model) {
    checkNotNull(model);
    return modelVersions.get(model);
  }

  public void put(final String model, final String version) {
    checkNotNull(model);
    checkNotNull(version);
    String oldVersion = modelVersions.put(model, version);
    dirty = dirty || !version.equals(oldVersion);
  }

  @Override
  public Iterator<Entry<String, String>> iterator() {
    return modelVersions.entrySet().iterator();
  }

  /**
   * @since 3.3
   */
  public boolean isDirty() {
    return dirty;
  }

  /**
   * @since 3.3
   */
  public void clearDirty() {
    dirty = false;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + modelVersions;
  }
}
