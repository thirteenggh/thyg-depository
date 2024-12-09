package org.sonatype.nexus.rapture.internal.settings;

import java.util.Map;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.rapture.StateContributor;
import org.sonatype.nexus.rapture.UiSettingsManager;

import com.google.common.collect.ImmutableMap;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Contributes {@code uiSettings} state.
 *
 * @since 3.0
 */
@Named
@Singleton
public class UiSettingsStateContributor
  extends ComponentSupport
  implements StateContributor
{
  private static final String STATE_ID = "uiSettings";

  private final UiSettingsManager rapture;

  @Inject
  public UiSettingsStateContributor(final UiSettingsManager rapture) {
    this.rapture = checkNotNull(rapture);
  }

  @Nullable
  @Override
  public Map<String, Object> getState() {
    return ImmutableMap.of(STATE_ID, calculateSettings());
  }

  private Object calculateSettings() {
    return rapture.getSettings();
  }
}
