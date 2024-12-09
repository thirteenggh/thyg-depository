package org.sonatype.nexus.repository.upgrade;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.sonatype.nexus.common.upgrade.Upgrades;
import org.sonatype.nexus.orient.DatabaseInstance;
import org.sonatype.nexus.orient.DatabaseInstanceNames;
import org.sonatype.nexus.orient.DatabaseUpgradeSupport;
import org.sonatype.nexus.orient.OClassNameBuilder;
import org.sonatype.nexus.orient.OIndexNameBuilder;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OSchema;
import com.orientechnologies.orient.core.metadata.schema.OClass.INDEX_TYPE;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.repository.storage.ComponentEntityAdapter.P_VERSION;
import static org.sonatype.nexus.repository.storage.MetadataNodeEntityAdapter.P_BUCKET;
import static org.sonatype.nexus.repository.storage.MetadataNodeEntityAdapter.P_NAME;

/**
 * Upgrade step to add bucket/name index to the component database.
 *
 * @since 3.2.1
 */
@Named
@Singleton
@Upgrades(model = DatabaseInstanceNames.COMPONENT, from = "1.1", to = "1.2")
public class ComponentDatabaseUpgrade_1_2 // NOSONAR
    extends DatabaseUpgradeSupport
{
  static final String COMPONENT_CLASS = new OClassNameBuilder()
      .type("component")
      .build();

  static final String I_BUCKET_NAME_VERSION = new OIndexNameBuilder()
      .type(COMPONENT_CLASS)
      .property(P_BUCKET)
      .property(P_NAME)
      .property(P_VERSION)
      .build();

  private final Provider<DatabaseInstance> componentDatabaseInstance;

  @Inject
  public ComponentDatabaseUpgrade_1_2(
      @Named(DatabaseInstanceNames.COMPONENT) final Provider<DatabaseInstance> componentDatabaseInstance)
  {
    this.componentDatabaseInstance = checkNotNull(componentDatabaseInstance);
  }

  @Override
  public void apply() throws Exception {
    createBucketNameIndex();
  }

  private void createBucketNameIndex() {
    try (ODatabaseDocumentTx db = componentDatabaseInstance.get().connect()) {
      if (db.getMetadata().getIndexManager().getIndex(I_BUCKET_NAME_VERSION) == null) {
        OSchema schema = db.getMetadata().getSchema();
        OClass type = schema.getClass(COMPONENT_CLASS);
        if (type != null) {
          type.createIndex(I_BUCKET_NAME_VERSION, INDEX_TYPE.NOTUNIQUE, new String[] { P_BUCKET, P_NAME, P_VERSION });
        }
      }
    }
  }
}
