package org.sonatype.nexus.upgrade.internal.orient;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.orient.OClassNameBuilder;
import org.sonatype.nexus.orient.entity.SingletonEntityAdapter;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.record.impl.ODocument;

/**
 * {@link ClusteredModelVersions} entity-adapter.
 * 
 * @since 3.1
 */
@Named
@Singleton
public class ClusteredModelVersionsEntityAdapter
    extends SingletonEntityAdapter<ClusteredModelVersions>
{
  private static final String DB_CLASS = new OClassNameBuilder().prefix("upgrade").type("model_versions").build();

  public ClusteredModelVersionsEntityAdapter() {
    super(DB_CLASS);
  }

  @Override
  protected void defineType(OClass type) {
    // no schema
  }

  @Override
  protected ClusteredModelVersions newEntity() {
    return new ClusteredModelVersions();
  }

  @Override
  protected void readFields(ODocument document, ClusteredModelVersions entity) throws Exception {
    document.forEach(entry -> entity.put(entry.getKey(), entry.getValue().toString()));
    entity.clearDirty(); // make sure this is reset after populating entity
  }

  @Override
  protected void writeFields(ODocument document, ClusteredModelVersions entity) throws Exception {
    entity.forEach(entry -> document.field(entry.getKey(), entry.getValue()));
  }

  public ClusteredModelVersions get(ODatabaseDocumentTx db) {
    ClusteredModelVersions entity = super.get(db);
    if (entity == null) {
      entity = newEntity();
    }
    return entity;
  }
}
