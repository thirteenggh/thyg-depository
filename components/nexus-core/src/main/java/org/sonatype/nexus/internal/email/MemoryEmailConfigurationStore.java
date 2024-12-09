package org.sonatype.nexus.internal.email;

import javax.annotation.Nullable;
import javax.annotation.Priority;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.email.EmailConfiguration;

import com.google.common.annotations.VisibleForTesting;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * In-memory {@link EmailConfigurationStore}.
 *
 * @since 3.0
 */
@Named("memory")
@Singleton
@Priority(Integer.MIN_VALUE)
@VisibleForTesting
public class MemoryEmailConfigurationStore
  extends ComponentSupport
  implements EmailConfigurationStore
{
  private EmailConfiguration model;

  @Nullable
  @Override
  public synchronized EmailConfiguration load() {
    return model;
  }

  @Override
  public synchronized void save(final EmailConfiguration configuration) {
    this.model = checkNotNull(configuration);
  }

  @Override
  public EmailConfiguration newConfiguration() {
    return new MemoryEmailConfiguration();
  }
}
