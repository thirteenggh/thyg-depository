package org.sonatype.nexus.repository.types;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.Type;

import com.google.common.annotations.VisibleForTesting;

/**
 * Proxy repository type.
 *
 * @since 3.0
 */
@Named(ProxyType.NAME)
@Singleton
public class ProxyType
  extends Type
{
  public static final String NAME = "proxy";

  @VisibleForTesting
  public ProxyType() {
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
