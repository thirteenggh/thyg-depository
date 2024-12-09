package org.sonatype.repository.helm.internal;

import java.util.HashMap;
import java.util.Map;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.common.collect.NestedAttributesMap;
import org.sonatype.repository.helm.HelmAttributes;
import org.sonatype.repository.helm.internal.database.HelmProperties;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.sonatype.nexus.repository.storage.MetadataNodeEntityAdapter.P_ATTRIBUTES;

public class HelmAssetAttributePopulatorTest
    extends TestSupport
{
  @Test
  public void testPopulateNestedAttributesMapFromHelmAttributes() throws Exception {
    NestedAttributesMap nestedAttributesMap = new NestedAttributesMap(P_ATTRIBUTES, new HashMap<>());

    setUpHelmAttributes().populate(nestedAttributesMap);

    assertThat(nestedAttributesMap.get(HelmProperties.ICON.getPropertyName()), is(equalTo("icon")));
    assertThat(nestedAttributesMap.get(HelmProperties.DESCRIPTION.getPropertyName()), is(equalTo("description")));
    assertThat(nestedAttributesMap.get(HelmProperties.NAME.getPropertyName()), is(equalTo("name")));
    assertThat(nestedAttributesMap.get(HelmProperties.VERSION.getPropertyName()), is(equalTo("1.0.0")));
    assertThat(nestedAttributesMap.get(HelmProperties.APP_VERSION.getPropertyName()), is(equalTo("0.0.1")));
    assertThat(nestedAttributesMap.get(HelmProperties.MAINTAINERS.getPropertyName()), is(notNullValue()));
    assertThat(nestedAttributesMap.get(HelmProperties.SOURCES.getPropertyName()), is(notNullValue()));
  }

  private HelmAttributes setUpHelmAttributes() {
    Map<String, Object> properties = new HashMap<>();
    properties.put(HelmProperties.DESCRIPTION.getPropertyName(), "description");
    properties.put(HelmProperties.ICON.getPropertyName(), "icon");
    properties.put(HelmProperties.NAME.getPropertyName(), "name");
    properties.put(HelmProperties.VERSION.getPropertyName(), "1.0.0");
    properties.put(HelmProperties.APP_VERSION.getPropertyName(), "0.0.1");
    properties.put(HelmProperties.SOURCES.getPropertyName(), HelmListTestHelper.getSourcesList());
    properties.put(HelmProperties.MAINTAINERS.getPropertyName(), HelmListTestHelper.getMaintainersList());

    return new HelmAttributes(properties);
  }
}
