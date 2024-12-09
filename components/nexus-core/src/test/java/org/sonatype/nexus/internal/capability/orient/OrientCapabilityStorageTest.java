package org.sonatype.nexus.internal.capability.orient;

import java.util.Map;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.capability.CapabilityIdentity;
import org.sonatype.nexus.common.event.EventManager;
import org.sonatype.nexus.internal.capability.storage.CapabilityStorageItem;
import org.sonatype.nexus.internal.capability.storage.orient.OrientCapabilityStorageItemCreatedEvent;
import org.sonatype.nexus.internal.capability.storage.orient.OrientCapabilityStorageItemDeletedEvent;
import org.sonatype.nexus.internal.capability.storage.orient.OrientCapabilityStorageItemEntityAdapter;
import org.sonatype.nexus.internal.capability.storage.CapabilityStorageItemEvent;
import org.sonatype.nexus.internal.capability.storage.orient.OrientCapabilityStorageItemUpdatedEvent;
import org.sonatype.nexus.internal.capability.storage.orient.OrientCapabilityStorage;
import org.sonatype.nexus.orient.HexRecordIdObfuscator;
import org.sonatype.nexus.orient.entity.EntityHook;
import org.sonatype.nexus.orient.testsupport.DatabaseInstanceRule;

import com.google.common.collect.Maps;
import com.orientechnologies.orient.core.Orient;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link OrientCapabilityStorage}.
 */
public class OrientCapabilityStorageTest
  extends TestSupport
{
  @Rule
  public DatabaseInstanceRule database = DatabaseInstanceRule.inMemory("test");

  private OrientCapabilityStorage underTest;

  @Mock
  private EventManager eventManager;

  private EntityHook entityHook;

  @Before
  public void setUp() throws Exception {
    entityHook = new EntityHook(eventManager);

    Orient.instance().addDbLifecycleListener(entityHook);

    OrientCapabilityStorageItemEntityAdapter entityAdapter = new OrientCapabilityStorageItemEntityAdapter();
    entityAdapter.enableObfuscation(new HexRecordIdObfuscator());
    entityAdapter.enableEntityHook(entityHook);

    this.underTest = new OrientCapabilityStorage(
        database.getInstanceProvider(),
        entityAdapter
    );

    underTest.start();
  }

  @After
  public void tearDown() throws Exception {
    if (underTest != null) {
      underTest.stop();
      underTest = null;
    }
  }

  @Test
  public void getAll_empty() throws Exception {
    Map<CapabilityIdentity, CapabilityStorageItem> items = underTest.getAll();
    assertThat(items, notNullValue());
    assertThat(items.isEmpty(), is(true));
  }

  @Test
  public void basicLifecycle() throws Exception {
    Map<String,String> props = Maps.newHashMap();
    props.put("foo", "bar");
    CapabilityStorageItem item1 = underTest.newStorageItem(0, "test", false, "hello", props);
    log("Item: {}", item1);

    // add
    CapabilityIdentity id = underTest.add(item1);
    log("Id: {}", id);

    // list
    Map<CapabilityIdentity, CapabilityStorageItem> items1 = underTest.getAll();
    log("Items: {}", items1);
    assertThat(items1, notNullValue());
    assertThat(items1.entrySet(), hasSize(1));

    // verify that item has same attributes as original
    CapabilityStorageItem item2 = items1.values().iterator().next();
    assertThat(item2.getType(), is(item1.getType()));
    assertThat(item2.getVersion(), is(item1.getVersion()));
    assertThat(item2.getNotes(), is(item1.getNotes()));
    assertThat(item2.isEnabled(), is(item1.isEnabled()));
    assertThat(item2.getProperties(), hasEntry("foo", "bar"));

    // update
    item2.setEnabled(true);
    boolean updated = underTest.update(id, item2);
    assertThat("failed to update", updated, is(true));

    // verify that all is the same, except for enabled flag
    CapabilityStorageItem item3 = underTest.getAll().values().iterator().next();
    assertThat(item3, notNullValue());
    assertThat(item3.getType(), is(item1.getType()));
    assertThat(item3.getVersion(), is(item1.getVersion()));
    assertThat(item3.getNotes(), is(item1.getNotes()));
    assertThat(item3.isEnabled(), is(true));
    assertThat(item3.getProperties(), hasEntry("foo", "bar"));

    // delete
    boolean removed = underTest.remove(id);
    assertThat("failed to remove", removed, is(true));

    Map<CapabilityIdentity, CapabilityStorageItem> items2 = underTest.getAll();
    log("Items: {}", items2);
    assertThat(items2, notNullValue());
    assertThat(items2.isEmpty(), is(true));

    // events (entity + batch)
    ArgumentCaptor<?> eventCaptor = ArgumentCaptor.forClass(Object.class);
    verify(eventManager, times(3)).post(eventCaptor.capture());

    Object[] events = eventCaptor.getAllValues().toArray();

    assertThat(events[0], instanceOf(OrientCapabilityStorageItemCreatedEvent.class));
    checkEventDetails((CapabilityStorageItemEvent) events[0], id, item1);

    assertThat(events[1], instanceOf(OrientCapabilityStorageItemUpdatedEvent.class));
    checkEventDetails((CapabilityStorageItemEvent) events[1], id, item3);

    assertThat(events[2], instanceOf(OrientCapabilityStorageItemDeletedEvent.class));
    checkEventDetails((CapabilityStorageItemEvent) events[2], id, item3);
  }

  private static void checkEventDetails(CapabilityStorageItemEvent event,
                                        CapabilityIdentity id,
                                        CapabilityStorageItem item)
  {
    assertThat(event.getCapabilityId(), is(id));
    CapabilityStorageItem eventItem = event.getCapabilityStorageItem();
    assertThat(eventItem.getType(), is(item.getType()));
    assertThat(eventItem.getVersion(), is(item.getVersion()));
    assertThat(eventItem.getNotes(), is(item.getNotes()));
    assertThat(eventItem.isEnabled(), is(item.isEnabled()));
    assertThat(eventItem.getProperties(), is(item.getProperties()));
  }
}
