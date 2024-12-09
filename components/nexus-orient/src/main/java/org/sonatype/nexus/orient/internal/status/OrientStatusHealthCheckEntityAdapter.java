package org.sonatype.nexus.orient.internal.status;

import java.util.Date;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.entity.AbstractEntity;
import org.sonatype.nexus.orient.OClassNameBuilder;
import org.sonatype.nexus.orient.OIndexNameBuilder;
import org.sonatype.nexus.orient.entity.IterableEntityAdapter;
import org.sonatype.nexus.orient.entity.action.ReadEntityByPropertyAction;
import org.sonatype.nexus.orient.internal.status.OrientStatusHealthCheckEntityAdapter.NodeHealthCheck;

import com.orientechnologies.orient.core.collate.OCaseInsensitiveCollate;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OClass.INDEX_TYPE;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.orientechnologies.orient.core.record.impl.ODocument;

/**
 * Entity adapter used to store node health check timestamps
 *
 * @since 3.15
 */
@Named
@Singleton
public class OrientStatusHealthCheckEntityAdapter
    extends IterableEntityAdapter<NodeHealthCheck>
{
  private static final String P_NODE_ID = "node_id";

  private static final String P_LAST_HEALTHCHECK = "last_health_check";

  private static final String DB_CLASS = new OClassNameBuilder()
      .type("statushealthcheck")
      .build();

  private static final String I_NODE_ID = new OIndexNameBuilder()
      .type(DB_CLASS)
      .property(P_NODE_ID)
      .build();

  private final ReadEntityByPropertyAction<NodeHealthCheck> readByNodeId = new ReadEntityByPropertyAction<>(this,
      P_NODE_ID);

  OrientStatusHealthCheckEntityAdapter() {
    super(DB_CLASS);
  }

  @Override
  protected void defineType(final OClass type) {
    type.createProperty(P_NODE_ID, OType.STRING)
        .setCollate(new OCaseInsensitiveCollate())
        .setMandatory(true)
        .setNotNull(true);
    type.createProperty(P_LAST_HEALTHCHECK, OType.DATETIME)
        .setMandatory(true)
        .setNotNull(true);
    type.createIndex(I_NODE_ID, INDEX_TYPE.UNIQUE, P_NODE_ID);
  }

  @Override
  protected NodeHealthCheck newEntity() {
    return new NodeHealthCheck();
  }

  @Override
  protected void readFields(final ODocument document, final NodeHealthCheck entity) {
    entity.nodeId = document.field(P_NODE_ID, OType.STRING);
    entity.lastHealthCheck = document.field(P_LAST_HEALTHCHECK, OType.DATETIME);
  }

  @Override
  protected void writeFields(final ODocument document, final NodeHealthCheck entity) {
    document.field(P_NODE_ID, entity.nodeId, OType.STRING);
    document.field(P_LAST_HEALTHCHECK, entity.lastHealthCheck, OType.DATETIME);
  }

  @Nullable
  public NodeHealthCheck read(final ODatabaseDocumentTx db, final String nodeId) {
    return readByNodeId.execute(db, nodeId);
  }

  /**
   * glue class to work with {@link org.sonatype.nexus.orient.entity.EntityAdapter}
   */
  static class NodeHealthCheck
      extends AbstractEntity
  {
    String nodeId;

    Date lastHealthCheck;
  }
}
