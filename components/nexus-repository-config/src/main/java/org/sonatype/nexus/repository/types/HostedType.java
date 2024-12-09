package org.sonatype.nexus.repository.types;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.Type;

import com.google.common.annotations.VisibleForTesting;

/**
 * Hosted repository type.
 *
 * @since 3.0
 */
@Named(HostedType.NAME)
@Singleton
public class HostedType
  extends Type
{
  public static final String NAME = "hosted";

  @VisibleForTesting
  public HostedType() {
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
