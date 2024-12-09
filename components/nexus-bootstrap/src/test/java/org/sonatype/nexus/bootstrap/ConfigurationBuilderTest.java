package org.sonatype.nexus.bootstrap;

import java.util.Map;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.bootstrap.ConfigurationBuilder.Customizer;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;

/**
 * Tests for {@link ConfigurationBuilder}.
 */
public class ConfigurationBuilderTest
    extends TestSupport
{
  private ConfigurationBuilder underTest;

  @Before
  public void setUp() throws Exception {
    this.underTest = new ConfigurationBuilder();
  }

  @Test(expected = IllegalStateException.class)
  public void build_notConfigured() throws Exception {
    // at least 1 property must be configured before calling build()
    underTest.build();
  }

  @Test
  public void set_withNulls() throws Exception {
    try {
      underTest.set("foo", null);
      fail();
    }
    catch (NullPointerException expected) {
      // ignore
    }

    try {
      underTest.set(null, "bar");
      fail();
    }
    catch (NullPointerException expected) {
      // ignore
    }
  }

  @Test
  public void set_overridesPrevious() throws Exception {
    underTest.set("foo", "bar");
    underTest.set("foo", "baz");

    Map<String, String> config = underTest.build();

    assertThat(config.entrySet(), hasSize(1));
    assertThat(config.get("foo"), is("baz"));
  }

  @Test
  public void properties_withMap() throws Exception {
    Map<String,String> properties = ImmutableMap.of(
        "foo", "bar",
        "a", "b"
    );
    underTest.properties(properties);

    Map<String, String> config = underTest.build();

    assertThat(config.entrySet(), hasSize(2));
    assertThat(config.get("foo"), is("bar"));
    assertThat(config.get("a"), is("b"));
  }

  @Test
  public void override() throws Exception {
    underTest.set("foo", "bar");

    // override "foo" value, but provide other values which will not be set
    Map<String, String> overrides = Maps.newHashMap();
    overrides.put("a", "b"); // this is not in original set and will not apply to configuration
    overrides.put("foo", "baz");
    underTest.override(overrides);

    Map<String, String> config = underTest.build();

    assertThat(config.entrySet(), hasSize(1));
    assertThat(config.get("foo"), is("baz"));
    // ^^^ implies config.containsKey("a") is false
  }

  @Test
  public void interpolation() throws Exception {
    underTest.set("foo", "bar");
    underTest.set("baz", "${foo}");

    Map<String, String> config = underTest.build();

    assertThat(config.entrySet(), hasSize(2));
    assertThat(config.get("foo"), is("bar"));
    assertThat(config.get("baz"), is("bar"));
  }

  @Test
  public void customizer() throws Exception {
    underTest.custom(new Customizer() {
      @Override
      public void apply(final ConfigurationBuilder builder) throws Exception {
        builder.set("foo", "bar");
      }
    });

    Map<String, String> config = underTest.build();

    assertThat(config.entrySet(), hasSize(1));
    assertThat(config.get("foo"), is("bar"));
  }
}
