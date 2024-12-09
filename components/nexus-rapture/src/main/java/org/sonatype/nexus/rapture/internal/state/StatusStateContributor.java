package org.sonatype.nexus.rapture.internal.state;

import java.util.Map;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.common.app.ApplicationVersion;
import org.sonatype.nexus.rapture.StateContributor;

import com.google.common.collect.ImmutableMap;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Contributes {@code status} state.
 *
 * @since 3.0
 */
@Named
@Singleton
public class StatusStateContributor
    extends ComponentSupport
    implements StateContributor
{
  private static final String STATE_ID = "status";

  private final ApplicationVersion applicationVersion;

  @Inject
  public StatusStateContributor(final ApplicationVersion applicationVersion) {
    this.applicationVersion = checkNotNull(applicationVersion);
  }

  @Nullable
  @Override
  public Map<String, Object> getState() {
    return ImmutableMap.of(STATE_ID, calculateStatus());
  }

  private Object calculateStatus() {
    StatusXO result = new StatusXO();
    result.setVersion(applicationVersion.getVersion());
    result.setEdition(applicationVersion.getEdition());
    result.setBuildRevision(applicationVersion.getBuildRevision());
    result.setBuildTimestamp(applicationVersion.getBuildTimestamp());
    return result;
  }
}
