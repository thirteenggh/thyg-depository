package org.sonatype.nexus.coreui.internal.node;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.app.FreezeService;
import org.sonatype.nexus.rapture.StateContributor;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Contributes freeze state to the UI.
 *
 * @since 3.24
 */
@Named
@Singleton
public class FreezeStateContributor
    implements StateContributor
{
  private final FreezeService freezeService;

  @Inject
  public FreezeStateContributor(final FreezeService freezeService) {
    this.freezeService = checkNotNull(freezeService);
  }

  @Override
  public Map<String, Object> getState() {
    Map<String, Object> state = new HashMap<>();
    state.put("frozen", freezeService.isFrozen());
    state.put("frozenManually", freezeService.isFrozenByUser());
    return state;
  }
}
