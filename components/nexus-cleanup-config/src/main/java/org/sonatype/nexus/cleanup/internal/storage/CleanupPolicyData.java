package org.sonatype.nexus.cleanup.internal.storage;

import java.util.Map;

import org.sonatype.nexus.cleanup.storage.CleanupPolicy;
import org.sonatype.nexus.common.entity.HasName;

/**
 * {@link CleanupPolicy} data.
 *
 * @since 3.21
 */
public class CleanupPolicyData
    implements HasName, CleanupPolicy
{
  private String name;

  private String notes;

  /**
   * Format that the policy is associated with or All
   */
  private String format;

  /**
   * The mode that the policy should take, e.g. deletion
   */
  private String mode;

  private Map<String, String> criteria;

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(final String name) {
    this.name = name;
  }

  @Override
  public String getNotes() {
    return notes;
  }

  @Override
  public void setNotes(final String notes) {
    this.notes = notes;
  }

  @Override
  public String getFormat() {
    return format;
  }

  @Override
  public void setFormat(final String format) {
    this.format = format;
  }

  @Override
  public String getMode() {
    return mode;
  }

  @Override
  public void setMode(final String mode) {
    this.mode = mode;
  }

  @Override
  public Map<String, String> getCriteria() {
    return criteria;
  }

  @Override
  public void setCriteria(final Map<String, String> criteria) {
    this.criteria = criteria;
  }

  @Override
  public String toString() {
    return "CleanupPolicy{" +
        "name='" + name + '\'' +
        ", format='" + format + '\'' +
        ", mode='" + mode + '\'' +
        ", criteria=" + criteria +
        '}';
  }
}
