package org.sonatype.nexus.repository.pypi.internal;

import org.sonatype.goodies.common.ComponentSupport;

import static org.sonatype.nexus.logging.task.TaskLoggingMarkers.TASK_LOG_ONLY;

public abstract class PyPiExportAssetFilterSupport
    extends ComponentSupport
{
  public boolean shouldSkipAsset(final String path, final String kind) {
    try {
      AssetKind assetKind = AssetKind.valueOf(kind);
      switch (assetKind) {
        case INDEX:
        case ROOT_INDEX:
        case SEARCH:
          log.trace("PyPI asset {} is NOT allowed for processing, will skip.", path);
          return true;
        default:
          log.trace("PyPI asset {} is allowed for processing.", path);
          return false;
      }
    }
    catch (Exception e) {
      log.error(TASK_LOG_ONLY, "PyPI asset {} has invalid assetkind '{}'. Will skip for export. {}", path,
          kind, log.isDebugEnabled() ? e : "");
      return true;
    }
  }
}
