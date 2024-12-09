package org.sonatype.nexus.internal.security.realm.orient;

import java.util.List;

import javax.annotation.Nullable;

import org.sonatype.nexus.common.entity.AbstractEntity;
import org.sonatype.nexus.security.realm.RealmConfiguration;

import com.google.common.collect.Lists;

/**
 * Orient specific Realm configuration.
 *
 * @since 3.20
 */
class OrientRealmConfiguration
    extends AbstractEntity
    implements RealmConfiguration, Cloneable
{
  private List<String> realmNames;

  OrientRealmConfiguration() {
    // Package private
  }

  @Override
  public List<String> getRealmNames() {
    if (realmNames == null) {
      realmNames = Lists.newArrayList();
    }
    return realmNames;
  }

  @Override
  public void setRealmNames(@Nullable final List<String> realmNames) {
    this.realmNames = realmNames;
  }

  @Override
  public OrientRealmConfiguration copy() {
    try {
      OrientRealmConfiguration copy = (OrientRealmConfiguration) clone();
      if (realmNames != null) {
        copy.realmNames = Lists.newArrayList(realmNames);
      }
      return copy;
    }
    catch (CloneNotSupportedException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" +
        "realmNames=" + realmNames +
        '}';
  }
}
