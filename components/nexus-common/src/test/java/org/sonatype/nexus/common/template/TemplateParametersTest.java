package org.sonatype.nexus.common.template;

import java.util.Map;

import org.sonatype.goodies.testsupport.TestSupport;

import com.google.common.collect.Maps;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link TemplateParameters}
 */
public class TemplateParametersTest
    extends TestSupport
{
  @Test
  public void empty() {
    Map<String, Object> params = new TemplateParameters().get();
    log(params);

    assertNotNull(params);
    assertThat(params.size(), is(0));
  }

  @Test
  public void mixedTypes() {
    Map<String, Object> params = new TemplateParameters()
        .set("a", "1")
        .set("b", 2)
        .get();
    log(params);

    assertNotNull(params);
    assertThat(params.size(), is(2));
    assertThat(params.get("a"), is((Object) "1"));
    assertThat(params.get("b"), is((Object) 2));
  }

  @Test
  public void setAll() {
    Map<String, Object> other = Maps.newHashMap();
    other.put("a", "1");
    other.put("b", 2);

    Map<String, Object> params = new TemplateParameters()
        .setAll(other)
        .get();
    log(params);

    assertNotNull(params);
    assertThat(params.size(), is(2));
    assertThat(params.get("a"), is((Object) "1"));
    assertThat(params.get("b"), is((Object) 2));
  }
}
