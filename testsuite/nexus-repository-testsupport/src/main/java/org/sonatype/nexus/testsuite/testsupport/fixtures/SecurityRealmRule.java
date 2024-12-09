package org.sonatype.nexus.testsuite.testsupport.fixtures;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Provider;

import org.sonatype.nexus.security.realm.RealmConfiguration;
import org.sonatype.nexus.security.realm.RealmManager;

import org.junit.rules.ExternalResource;

public class SecurityRealmRule
    extends ExternalResource
{
  final Provider<RealmManager> realmManagerProvider;

  private RealmConfiguration configuration;

  public SecurityRealmRule(final Provider<RealmManager> realmManagerProvider) {
    this.realmManagerProvider = realmManagerProvider;
  }

  @Override
  protected void before() {
    configuration = realmManagerProvider.get().getConfiguration().copy();
  }

  @Override
  protected void after() {
    realmManagerProvider.get().setConfiguration(configuration);
  }

  public void addSecurityRealm(final String realm) {
    RealmConfiguration configuration = realmManagerProvider.get().getConfiguration().copy();
    List<String> realms = new ArrayList<>(configuration.getRealmNames());
    realms.add(realm);
    configuration.setRealmNames(realms);
    realmManagerProvider.get().setConfiguration(configuration);
  }

  public void removeSecurityRealm(final String realm) {
    RealmConfiguration configuration = realmManagerProvider.get().getConfiguration().copy();
    List<String> realms = new ArrayList<>(configuration.getRealmNames());
    realms.remove(realm);
    configuration.setRealmNames(realms);
    realmManagerProvider.get().setConfiguration(configuration);
  }
}
