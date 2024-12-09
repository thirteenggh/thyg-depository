package org.sonatype.nexus.upgrade.internal.orient;

import java.io.File;
import java.util.Collections;
import java.util.Map;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.common.app.ApplicationDirectories;
import org.sonatype.nexus.common.entity.EntityVersion;
import org.sonatype.nexus.common.property.PropertiesFile;
import org.sonatype.nexus.orient.testsupport.DatabaseInstanceRule;

import com.google.common.collect.ImmutableMap;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

public class ModelVersionStoreTest
    extends TestSupport
{
  @Rule
  public DatabaseInstanceRule database = DatabaseInstanceRule.inMemory("test");

  @Mock
  private UpgradeManager upgradeManager;

  @Mock
  private ApplicationDirectories appDirs;

  private ClusteredModelVersionsEntityAdapter entityAdapter;

  private ModelVersionStore store;

  private File dbFolder;

  @Before
  public void setUp() throws Exception {
    dbFolder = util.createTempDir();
    when(appDirs.getWorkDirectory("db")).thenReturn(dbFolder);
    when(upgradeManager.getLocalModels()).thenReturn(Collections.singleton("local"));
    when(upgradeManager.getClusteredModels()).thenReturn(Collections.singleton("clustered"));
    entityAdapter = new ClusteredModelVersionsEntityAdapter();
    store = new ModelVersionStore(upgradeManager, database.getInstanceProvider(), entityAdapter, appDirs);
  }

  @Test
  public void testLoad_PristineInstallation() throws Exception {
    store.start();
    Map<String, String> versions = store.load();
    assertThat(versions.entrySet(), hasSize(0));
  }

  @Test
  public void testLoad_ExistingInstallation() throws Exception {
    try (ODatabaseDocumentTx db = database.getInstance().connect()) {
      ClusteredModelVersions versions = new ClusteredModelVersions();
      versions.put("clustered", "1.2");
      versions.put("local", "99");
      entityAdapter.set(db, versions);
    }
    PropertiesFile modelProperties = new PropertiesFile(new File(dbFolder, ModelVersionStore.MODEL_PROPERTIES));
    modelProperties.put("clustered", "99");
    modelProperties.put("local", "2.1");
    modelProperties.store();
    store.start();
    Map<String, String> versions = store.load();
    assertThat(versions, hasEntry("clustered", "1.2"));
    assertThat(versions, hasEntry("local", "2.1"));
    assertThat(versions.entrySet(), hasSize(2));
  }

  @Test
  public void testSave() throws Exception {
    store.start();
    store.save(ImmutableMap.of("clustered", "1.2", "local", "2.1"));
    try (ODatabaseDocumentTx db = database.getInstance().connect()) {
      ClusteredModelVersions versions = entityAdapter.get(db);
      assertThat(versions.getModelVersions(), hasEntry("clustered", "1.2"));
      assertThat(versions.getModelVersions().entrySet(), hasSize(1));
    }
    PropertiesFile modelProperties = new PropertiesFile(new File(dbFolder, ModelVersionStore.MODEL_PROPERTIES));
    assertThat(modelProperties.getFile().isFile(), is(true));
    modelProperties.load();
    assertThat(modelProperties, hasEntry("local", "2.1"));
    assertThat(modelProperties.entrySet(), hasSize(1));
  }

  @Test
  public void testSameDataOnlySavedOnce() throws Exception {
    store.start();
    store.save(ImmutableMap.of("clustered", "1.3"));

    EntityVersion persistedVersion;
    try (ODatabaseDocumentTx db = database.getInstance().connect()) {
      ClusteredModelVersions versions = entityAdapter.get(db);
      persistedVersion = versions.getEntityMetadata().getVersion();
    }

    // force complete reload of entity
    store.stop();
    store.start();

    // trying to save the same data should not bump the database record
    store.save(ImmutableMap.of("clustered", "1.3"));

    try (ODatabaseDocumentTx db = database.getInstance().connect()) {
      ClusteredModelVersions versions = entityAdapter.get(db);
      assertThat(versions.getEntityMetadata().getVersion(), is(persistedVersion));
    }
  }
}
