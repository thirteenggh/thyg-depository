package org.sonatype.nexus.internal.security.realm.orient;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.entity.EntityEvent;
import org.sonatype.nexus.orient.OClassNameBuilder;
import org.sonatype.nexus.orient.entity.AttachedEntityMetadata;
import org.sonatype.nexus.orient.entity.SingletonEntityAdapter;
import org.sonatype.nexus.security.realm.RealmConfiguration;

import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.orientechnologies.orient.core.record.impl.ODocument;

/**
 * {@link RealmConfiguration} entity-adapter.
 *
 * @since 3.0
 */
@Named
@Singleton
public class OrientRealmConfigurationEntityAdapter
    extends SingletonEntityAdapter<OrientRealmConfiguration>
{
  private static final String DB_CLASS = new OClassNameBuilder()
      .type("realm")
      .build();

  private static final String P_REALM_NAMES = "realm_names";

  public OrientRealmConfigurationEntityAdapter() {
    super(DB_CLASS);
  }

  @Override
  protected void defineType(final OClass type) {
    type.createProperty(P_REALM_NAMES, OType.EMBEDDEDLIST);
  }

  @Override
  protected OrientRealmConfiguration newEntity() {
    return new OrientRealmConfiguration();
  }

  @Override
  protected void readFields(final ODocument document, final OrientRealmConfiguration entity) {
    List<String> realms = document.field(P_REALM_NAMES, OType.EMBEDDEDLIST);

    entity.setRealmNames(realms);
  }

  @Override
  protected void writeFields(final ODocument document, final OrientRealmConfiguration entity) {
    document.field(P_REALM_NAMES, entity.getRealmNames());
  }

  @Override
  public EntityEvent newEvent(final ODocument document, final EventKind eventKind) {
    AttachedEntityMetadata metadata = new AttachedEntityMetadata(this, document);
    log.debug("Emitted {} event with metadata {}", eventKind, metadata);
    switch (eventKind) {
      case CREATE:
        return new OrientRealmConfigurationCreatedEvent(metadata);
      case UPDATE:
        return new OrientRealmConfigurationUpdatedEvent(metadata);
      case DELETE:
        return new OrientRealmConfigurationDeletedEvent(metadata);
      default:
        return null;
    }
  }
}
