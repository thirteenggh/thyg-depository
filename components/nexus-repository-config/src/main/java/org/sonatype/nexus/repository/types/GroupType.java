package org.sonatype.nexus.repository.types;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.Type;

import com.google.common.annotations.VisibleForTesting;

/**
 * Group repository type.
 *
 * @since 3.0
 */
@Named(GroupType.NAME)
@Singleton
public class GroupType
  extends Type
{
  public static final String NAME = "group";

  @VisibleForTesting
  public GroupType() {
    super(NAME);
  }

  @Override
  public Class<?> getValidationGroup() {
    return ValidationGroup.class;
  }

  public interface ValidationGroup {
    // empty
  }
}
