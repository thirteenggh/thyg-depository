package org.sonatype.nexus.internal.selector;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.sonatype.nexus.selector.SelectorConfiguration;
import org.sonatype.nexus.supportzip.datastore.JsonExporter;

import com.google.common.collect.ImmutableMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.anyOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests validity of Serialization/Deserialization {@link SelectorConfiguration} by {@link SelectorConfigurationExport}
 */
public class SelectorConfigurationExportTest
{
  private final JsonExporter jsonExporter = new JsonExporter();

  private File jsonFile;

  @Before
  public void setup() throws IOException {
    jsonFile = File.createTempFile("SamlUser", ".json");
  }

  @After
  public void tearDown() {
    jsonFile.delete();
  }

  @Test
  public void testExportImportToJson() throws Exception {
    List<SelectorConfiguration> selectorConfigurations = Arrays.asList(
        createSelectorConfiguration("config_1"),
        createSelectorConfiguration("config_2"));

    SelectorConfigurationStore store = mock(SelectorConfigurationStore.class);
    when(store.browse()).thenReturn(selectorConfigurations);

    SelectorConfigurationExport exporter = new SelectorConfigurationExport(store);
    exporter.export(jsonFile);
    List<SelectorConfigurationData> importedData =
        jsonExporter.importFromJson(jsonFile, SelectorConfigurationData.class);

    assertThat(importedData.size(), is(2));
    importedData.forEach(data -> assertThat(data, anyOf(
        is(selectorConfigurations.get(0)),
        is(selectorConfigurations.get(1)))));
  }

  private SelectorConfiguration createSelectorConfiguration(final String name) {
    SelectorConfigurationData configurationData = new SelectorConfigurationData();
    configurationData.setName(name);
    configurationData.setDescription("Description");
    configurationData.setType("type");
    configurationData.setAttributes(ImmutableMap.of("prop1", "value1", "prop2", "value2"));

    return configurationData;
  }
}
