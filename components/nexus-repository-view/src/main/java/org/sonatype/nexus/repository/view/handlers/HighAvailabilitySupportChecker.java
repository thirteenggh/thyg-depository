package org.sonatype.nexus.repository.view.handlers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.node.NodeAccess;

import static java.lang.String.format;
import static org.sonatype.nexus.common.property.SystemPropertiesHelper.getBoolean;

/**
 * Verifies availability of the repository dependent on clustering mode of NXRM
 * and the clustering configuration property of the repository.
 *
 * @since 3.17
 */
@Named
@Singleton
public class HighAvailabilitySupportChecker
{
  private final boolean isNexusClustered;

  private final Map<String, Boolean> formatHAStates = new ConcurrentHashMap<>();

  @Inject
  public HighAvailabilitySupportChecker(final NodeAccess nodeAccess) {
    isNexusClustered = nodeAccess.isClustered();
  }

  public boolean isSupported(final String formatName) {
    boolean formatForcedEnabled =
        formatHAStates.computeIfAbsent(formatName,
            f -> getBoolean(format("nexus.%s.ha.supported", formatName), false));
    return !isNexusClustered || formatForcedEnabled;
  }
}
