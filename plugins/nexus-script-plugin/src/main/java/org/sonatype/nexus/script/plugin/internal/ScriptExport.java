package org.sonatype.nexus.script.plugin.internal;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.script.Script;
import org.sonatype.nexus.supportzip.ExportConfigData;
import org.sonatype.nexus.supportzip.ImportData;
import org.sonatype.nexus.supportzip.datastore.JsonExporter;

/**
 * Write/Read {@link Script} data to/from a JSON file.
 *
 * @since 3.29
 */
@Named("scriptExport")
@Singleton
public class ScriptExport
    extends JsonExporter
    implements ExportConfigData, ImportData
{
  private final ScriptStore store;

  @Inject
  public ScriptExport(final ScriptStore store) {
    this.store = store;
  }

  @Override
  public void export(final File file) throws IOException {
    log.debug("Export Script data to {}", file);
    List<Script> scripts = store.list();
    exportToJson(scripts, file);
  }

  @Override
  public void restore(final File file) throws IOException {
    log.debug("Restoring Script data from {}", file);
    importFromJson(file, ScriptData.class).forEach(store::create);
  }
}

