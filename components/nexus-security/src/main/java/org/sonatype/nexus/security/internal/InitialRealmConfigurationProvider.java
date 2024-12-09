package org.sonatype.nexus.security.internal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.sonatype.nexus.security.realm.RealmConfiguration;
import org.sonatype.nexus.security.realm.RealmConfigurationStore;

import com.google.common.collect.Lists;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Initial {@link RealmConfiguration} provider.
 *
 * @since 3.0
 */
@Named("initial")
@Singleton
public class InitialRealmConfigurationProvider
    implements Provider<RealmConfiguration>
{
  private final RealmConfigurationStore store;

  @Inject
  public InitialRealmConfigurationProvider(final RealmConfigurationStore store) {
    this.store = checkNotNull(store);
  }

  @Override
  public RealmConfiguration get() {
    RealmConfiguration configuration = store.newEntity();
    configuration.setRealmNames(Lists.newArrayList(
        AuthenticatingRealmImpl.NAME,
        AuthorizingRealmImpl.NAME
    ));
    return configuration;
  }
}
