package org.sonatype.nexus.security.internal;

import java.util.Map;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.common.event.EventManager;
import org.sonatype.nexus.security.realm.RealmConfiguration;
import org.sonatype.nexus.security.realm.RealmConfigurationChangedEvent;
import org.sonatype.nexus.security.realm.RealmConfigurationEvent;
import org.sonatype.nexus.security.realm.RealmConfigurationStore;
import org.sonatype.nexus.security.realm.TestRealmConfiguration;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.Realm;
import org.eclipse.sisu.inject.BeanLocator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class RealmManagerImplTest
    extends TestSupport
{
  @Mock
  private EventManager eventManager;

  @Mock
  private RealmConfigurationStore configStore;

  @Mock
  private RealmSecurityManager securityManager;

  @Mock
  private Realm realmA;

  @Mock
  private Realm realmB;

  @Mock
  private RealmConfigurationEvent configEvent;

  @Mock
  private BeanLocator beanLocator;

  private RealmManagerImpl manager;

  @Before
  public void setUp() {
    Map<String, Realm> realms = ImmutableMap.of("A", realmA, "B", realmB);
    RealmConfiguration defaultConfig = new TestRealmConfiguration();
    defaultConfig.setRealmNames(ImmutableList.of("A"));
    manager =
        new RealmManagerImpl(beanLocator, eventManager, configStore, () -> defaultConfig, securityManager, realms);
  }

  @Test
  public void testOnStoreChanged_LocalEvent() {
    when(configEvent.isLocal()).thenReturn(true);
    manager.on(configEvent);
    verifyZeroInteractions(eventManager, configStore);
  }

  @Test
  public void testOnStoreChanged_RemoteEvent() {
    RealmConfiguration eventConfig = new TestRealmConfiguration();
    eventConfig.setRealmNames(ImmutableList.of("B"));
    when(configEvent.isLocal()).thenReturn(false);
    when(configEvent.getConfiguration()).thenReturn(eventConfig);

    manager.on(configEvent);

    ArgumentCaptor<RealmConfigurationChangedEvent> eventCaptor =
        ArgumentCaptor.forClass(RealmConfigurationChangedEvent.class);

    verify(eventManager).post(eventCaptor.capture());

    RealmConfiguration storeConfig = eventCaptor.getValue().getConfiguration();
    assertThat(storeConfig.getRealmNames(), is(eventConfig.getRealmNames()));
  }
}
