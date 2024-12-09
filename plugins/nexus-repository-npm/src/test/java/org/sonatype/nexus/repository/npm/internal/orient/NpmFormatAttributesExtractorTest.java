package org.sonatype.nexus.repository.npm.internal.orient;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.common.collect.NestedAttributesMap;
import org.sonatype.nexus.repository.npm.internal.NpmFormatAttributesExtractor;

import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class NpmFormatAttributesExtractorTest
    extends TestSupport
{
  private NestedAttributesMap formatAttributes;

  @Before
  public void setUp() {
    formatAttributes = new NestedAttributesMap("format", new LinkedHashMap<>());
  }

  @Test
  public void testCopyFormatAttributes() {
    NpmFormatAttributesExtractor underTest = new NpmFormatAttributesExtractor(
        new ImmutableMap.Builder<String, Object>()
            .put("author", "Foo <foo@example.com> (http://www.example.com/foo)")
            .put("keywords", asList("one", "two"))
            .put("name", "foo")
            .put("version", "1.2.3")
            .put("description", "This is a test.")
            .put("license", "(MIT OR Apache-2.0)")
            .put("homepage", "https://www.example.com/homepage")
            .put("repository", new ImmutableMap.Builder<String, Object>()
                .put("type", "git")
                .put("url", "https://www.example.com/repository.git")
                .build())
            .put("bugs", new ImmutableMap.Builder<String, Object>()
                .put("email", "bugs@example.com")
                .put("url", "https://www.example.com/bugs")
                .build())
            .put("contributors", asList(
                "Contributor One <contrib1@example.com> (http://www.example.com/contrib1)",
                "Contributor Two <contrib2@example.com> (http://www.example.com/contrib2)"))
            .put("os", asList("darwin", "linux"))
            .put("cpu", asList("x64", "ia32"))
            .put("engines", new ImmutableMap.Builder<String, Object>()
                .put("engine1", "1.0.0")
                .put("engine2", "2.0.0")
                .build())
            .build());

    underTest.copyFormatAttributes(formatAttributes);

    assertThat(formatAttributes.get("author"), is("Foo <foo@example.com> (http://www.example.com/foo)"));
    assertThat(formatAttributes.get("contributors"), is(asList(
        "Contributor One <contrib1@example.com> (http://www.example.com/contrib1)",
        "Contributor Two <contrib2@example.com> (http://www.example.com/contrib2)")));
    assertThat(formatAttributes.get("keywords"), is("one two"));
    assertThat(formatAttributes.get("os"), is("darwin linux"));
    assertThat(formatAttributes.get("cpu"), is("x64 ia32"));
    assertThat(formatAttributes.child("engines").get("engine1"), is("1.0.0"));
    assertThat(formatAttributes.child("engines").get("engine2"), is("2.0.0"));
    assertThat(formatAttributes.get("name"), is("foo"));
    assertThat(formatAttributes.get("version"), is("1.2.3"));
    assertThat(formatAttributes.get("description"), is("This is a test."));
    assertThat(formatAttributes.get("license"), is("(MIT OR Apache-2.0)"));
    assertThat(formatAttributes.get("homepage"), is("https://www.example.com/homepage"));
    assertThat(formatAttributes.get("repository_url"), is("https://www.example.com/repository.git"));
    assertThat(formatAttributes.get("repository_type"), is("git"));
    assertThat(formatAttributes.get("bugs_url"), is("https://www.example.com/bugs"));
    assertThat(formatAttributes.get("bugs_email"), is("bugs@example.com"));
    assertThat(formatAttributes.get("tagged_is"), is(""));
    assertThat(formatAttributes.get("tagged_not"), is("unstable"));
    assertThat(formatAttributes.get("search_normalized_version"), is("00001.00002.00003"));
  }

  @Test
  public void testCopyFormatAttributesWithUnscopedName() {
    NpmFormatAttributesExtractor underTest = new NpmFormatAttributesExtractor(
        new ImmutableMap.Builder<String, Object>()
            .put("name", "thename")
            .build());

    underTest.copyFormatAttributes(formatAttributes);

    assertThat(formatAttributes.get("name"), is("thename"));
    assertThat(formatAttributes.get("scope"), is(nullValue()));
  }

  @Test
  public void testCopyFormatAttributesWithScopedName() {
    NpmFormatAttributesExtractor underTest = new NpmFormatAttributesExtractor(
        new ImmutableMap.Builder<String, Object>()
            .put("name", "@thescope/thename")
            .build());

    underTest.copyFormatAttributes(formatAttributes);

    assertThat(formatAttributes.get("name"), is("@thescope/thename"));
    assertThat(formatAttributes.get("scope"), is("thescope"));
  }

  @Test
  public void testCopyFormatAttributesWithAuthorNameAsString() {
    NpmFormatAttributesExtractor underTest = new NpmFormatAttributesExtractor(
        new ImmutableMap.Builder<String, Object>()
            .put("author", "Foo <foo@example.com> (http://www.example.com/foo)")
            .build());

    underTest.copyFormatAttributes(formatAttributes);

    assertThat(formatAttributes.get("author"), is("Foo <foo@example.com> (http://www.example.com/foo)"));
  }

  @Test
  public void testCopyFormatAttributesWithAuthorNameAsMap() {
    NpmFormatAttributesExtractor underTest = new NpmFormatAttributesExtractor(
        new ImmutableMap.Builder<String, Object>()
        .put("author", new ImmutableMap.Builder<String, Object>()
            .put("name", "Foo")
            .put("email", "foo@example.com")
            .put("url", "http://www.example.com/foo")
            .build())
        .build());

    underTest.copyFormatAttributes(formatAttributes);

    assertThat(formatAttributes.get("author"), is("Foo <foo@example.com> (http://www.example.com/foo)"));
  }

  @Test
  public void testCopyFormatAttributesWithContributorNameAsString() {
    NpmFormatAttributesExtractor underTest = new NpmFormatAttributesExtractor(
        new ImmutableMap.Builder<String, Object>()
            .put("contributors", "Foo <foo@example.com> (http://www.example.com/foo)")
            .build());

    underTest.copyFormatAttributes(formatAttributes);

    assertThat(formatAttributes.get("contributors"),
        is(singletonList("Foo <foo@example.com> (http://www.example.com/foo)")));
  }

  @Test
  public void testCopyFormatAttributesWithContributorNameAsMap() {
    NpmFormatAttributesExtractor underTest = new NpmFormatAttributesExtractor(
        new ImmutableMap.Builder<String, Object>()
            .put("contributors", new ImmutableMap.Builder<String, Object>()
                .put("name", "Foo")
                .put("email", "foo@example.com")
                .put("url", "http://www.example.com/foo")
                .build())
            .build());

    underTest.copyFormatAttributes(formatAttributes);

    assertThat(formatAttributes.get("contributors"),
        is(singletonList("Foo <foo@example.com> (http://www.example.com/foo)")));
  }

  @Test
  public void testCopyFormatAttributesWithContributorNamesAsList() {
    NpmFormatAttributesExtractor underTest = new NpmFormatAttributesExtractor(
        new ImmutableMap.Builder<String, Object>()
            .put("contributors", asList("Foo <foo@example.com> (http://www.example.com/foo)",
                new ImmutableMap.Builder<String, Object>()
                    .put("name", "Bar")
                    .put("email", "bar@example.com")
                    .put("url", "http://www.example.com/bar")
                    .build()
                ))
            .build());

    underTest.copyFormatAttributes(formatAttributes);

    assertThat(formatAttributes.get("contributors"), is(asList(
        "Foo <foo@example.com> (http://www.example.com/foo)",
        "Bar <bar@example.com> (http://www.example.com/bar)")));
  }

  @Test
  public void testCopyFormatAttributesWithContributorNameNull() {
    NpmFormatAttributesExtractor underTest =
        getContributorAttributesExtractor(null, "foo@example.com", "http://www.example.com/foo");

    underTest.copyFormatAttributes(formatAttributes);

    assertThat(formatAttributes.get("contributors"),
        is(singletonList("null <foo@example.com> (http://www.example.com/foo)")));
  }

  @Test
  public void testCopyFormatAttributesWithContributorEmailNull() {
    NpmFormatAttributesExtractor underTest =
        getContributorAttributesExtractor("Foo", null, "http://www.example.com/foo");

    underTest.copyFormatAttributes(formatAttributes);

    assertThat(formatAttributes.get("contributors"),
        is(singletonList("Foo <null> (http://www.example.com/foo)")));
  }

  @Test
  public void testCopyFormatAttributesWithContributorUrlNull() {
    NpmFormatAttributesExtractor underTest =
        getContributorAttributesExtractor("Foo", "foo@example.com", null);

    underTest.copyFormatAttributes(formatAttributes);

    assertThat(formatAttributes.get("contributors"),
        is(singletonList("Foo <foo@example.com> (null)")));
  }

  private NpmFormatAttributesExtractor getContributorAttributesExtractor(final String name,
                                                                         final String email,
                                                                         final String url)
  {
    return new NpmFormatAttributesExtractor(
        new ImmutableMap.Builder<String, Object>()
            .put("contributors", new HashMap<String, Object>() {{
              put("name", name);
              put("email", email);
              put("url", url);
            }})
            .build());
  }

  @Test
  public void testCopyFormatAttributesWithKeywordsAsString() {
    NpmFormatAttributesExtractor underTest = new NpmFormatAttributesExtractor(new ImmutableMap.Builder<String, Object>()
        .put("keywords", "one")
        .build());

    underTest.copyFormatAttributes(formatAttributes);

    assertThat(formatAttributes.get("keywords"), is("one"));
  }

  @Test
  public void testCopyFormatAttributesWithKeywordsAsCollection() {
    NpmFormatAttributesExtractor underTest = new NpmFormatAttributesExtractor(
        new ImmutableMap.Builder<String, Object>()
        .put("keywords", asList("one", "two"))
        .build());

    underTest.copyFormatAttributes(formatAttributes);

    assertThat(formatAttributes.get("keywords"), is("one two"));
  }

  @Test
  public void testCopyFormatAttributesWithOSAsString() {
    NpmFormatAttributesExtractor underTest = new NpmFormatAttributesExtractor(new ImmutableMap.Builder<String, Object>()
        .put("os", "darwin")
        .build());

    underTest.copyFormatAttributes(formatAttributes);

    assertThat(formatAttributes.get("os"), is("darwin"));
  }

  @Test
  public void testCopyFormatAttributesWithOSAsCollection() {
    NpmFormatAttributesExtractor underTest = new NpmFormatAttributesExtractor(
        new ImmutableMap.Builder<String, Object>()
            .put("os", asList("darwin", "linux"))
            .build());

    underTest.copyFormatAttributes(formatAttributes);

    assertThat(formatAttributes.get("os"), is("darwin linux"));
  }

  @Test
  public void testCopyFormatAttributesWithCpuAsString() {
    NpmFormatAttributesExtractor underTest = new NpmFormatAttributesExtractor(new ImmutableMap.Builder<String, Object>()
        .put("cpu", "x64")
        .build());

    underTest.copyFormatAttributes(formatAttributes);

    assertThat(formatAttributes.get("cpu"), is("x64"));
  }

  @Test
  public void testCopyFormatAttributesWithCpuAsCollection() {
    NpmFormatAttributesExtractor underTest = new NpmFormatAttributesExtractor(
        new ImmutableMap.Builder<String, Object>()
            .put("cpu", asList("x64", "ia32"))
            .build());

    underTest.copyFormatAttributes(formatAttributes);

    assertThat(formatAttributes.get("cpu"), is("x64 ia32"));
  }

  @Test
  public void testCopyFormatAttributesWithLicenseAsString() {
    NpmFormatAttributesExtractor underTest = new NpmFormatAttributesExtractor(new ImmutableMap.Builder<String, Object>()
        .put("license", "(MIT OR Apache-2.0)")
        .build());

    underTest.copyFormatAttributes(formatAttributes);

    assertThat(formatAttributes.get("license"), is("(MIT OR Apache-2.0)"));
  }

  @Test
  public void testCopyFormatAttributesWithLicenseAsMap() {
    NpmFormatAttributesExtractor underTest = new NpmFormatAttributesExtractor(new ImmutableMap.Builder<String, Object>()
        .put("license", new ImmutableMap.Builder<String, Object>()
            .put("type", "ISC")
            .put("url", "https://opensource.org/licenses/ISC")
            .build())
        .build());

    underTest.copyFormatAttributes(formatAttributes);

    assertThat(formatAttributes.get("license"), is("ISC"));
  }

  @Test
  public void testCopyFormatAttributesWithHomepage() {
    NpmFormatAttributesExtractor underTest = new NpmFormatAttributesExtractor(new ImmutableMap.Builder<String, Object>()
        .put("homepage", "https://www.example.com/homepage")
        .build());

    underTest.copyFormatAttributes(formatAttributes);

    assertThat(formatAttributes.get("homepage"), is("https://www.example.com/homepage"));
  }

  @Test
  public void testCopyFormatAttributesWithRepository() {
    NpmFormatAttributesExtractor underTest = new NpmFormatAttributesExtractor(new ImmutableMap.Builder<String, Object>()
        .put("repository", new ImmutableMap.Builder<String, Object>()
            .put("type", "git")
            .put("url", "https://www.example.com/repository.git")
            .build())
        .build());

    underTest.copyFormatAttributes(formatAttributes);

    assertThat(formatAttributes.get("repository_url"), is("https://www.example.com/repository.git"));
    assertThat(formatAttributes.get("repository_type"), is("git"));
  }

  @Test
  public void testCopyFormatAttributesWithBugs() {
    NpmFormatAttributesExtractor underTest = new NpmFormatAttributesExtractor(new ImmutableMap.Builder<String, Object>()
        .put("bugs", new ImmutableMap.Builder<String, Object>()
            .put("email", "bugs@example.com")
            .put("url", "https://www.example.com/bugs")
            .build())
        .build());

    underTest.copyFormatAttributes(formatAttributes);

    assertThat(formatAttributes.get("bugs_url"), is("https://www.example.com/bugs"));
    assertThat(formatAttributes.get("bugs_email"), is("bugs@example.com"));
  }

  @Test
  public void testCopyWhenUnstableVersion() {
    NpmFormatAttributesExtractor underTest = new NpmFormatAttributesExtractor(new ImmutableMap.Builder<String, Object>()
        .put("version", "0.1.1")
        .build());

    underTest.copyFormatAttributes(formatAttributes);

    assertThat(formatAttributes.get("tagged_is"), is("unstable"));
    assertThat(formatAttributes.get("tagged_not"), is(""));
  }

  @Test
  public void testCopyWhenInitialStableVersion() {
    NpmFormatAttributesExtractor underTest = new NpmFormatAttributesExtractor(new ImmutableMap.Builder<String, Object>()
        .put("version", "1.0.0")
        .build());

    underTest.copyFormatAttributes(formatAttributes);

    assertThat(formatAttributes.get("tagged_is"), is(""));
    assertThat(formatAttributes.get("tagged_not"), is("unstable"));
  }

  @Test
  public void testCopyWhenSubsequentStableVersion() {
    NpmFormatAttributesExtractor underTest = new NpmFormatAttributesExtractor(new ImmutableMap.Builder<String, Object>()
        .put("version", "1.0.1")
        .build());

    underTest.copyFormatAttributes(formatAttributes);

    assertThat(formatAttributes.get("tagged_is"), is(""));
    assertThat(formatAttributes.get("tagged_not"), is("unstable"));
  }

  @Test
  public void testCopyNormalizedVersionWithExtraDigits() {
    NpmFormatAttributesExtractor underTest = new NpmFormatAttributesExtractor(new ImmutableMap.Builder<String, Object>()
        .put("version", "10.20.1")
        .build());

    underTest.copyFormatAttributes(formatAttributes);

    assertThat(formatAttributes.get("search_normalized_version"), is("00010.00020.00001"));
  }

  @Test
  public void testCopyNormalizedVersionWithDashSuffix() {
    NpmFormatAttributesExtractor underTest = new NpmFormatAttributesExtractor(new ImmutableMap.Builder<String, Object>()
        .put("version", "1.2.3-foo")
        .build());

    underTest.copyFormatAttributes(formatAttributes);

    assertThat(formatAttributes.get("search_normalized_version"), is("00001.00002.00003-foo"));
  }

  @Test
  public void testCopyNormalizedVersionWithPlusSuffix() {
    NpmFormatAttributesExtractor underTest = new NpmFormatAttributesExtractor(new ImmutableMap.Builder<String, Object>()
        .put("version", "1.2.3+foo")
        .build());

    underTest.copyFormatAttributes(formatAttributes);

    assertThat(formatAttributes.get("search_normalized_version"), is("00001.00002.00003+foo"));
  }

  @Test
  public void testCopyNormalizedVersionWithPrefix() {
    NpmFormatAttributesExtractor underTest = new NpmFormatAttributesExtractor(new ImmutableMap.Builder<String, Object>()
        .put("version", "foo-1.2.3")
        .build());

    underTest.copyFormatAttributes(formatAttributes);

    assertThat(formatAttributes.get("search_normalized_version"), is("foo-00001.00002.00003"));
  }

  @Test
  public void testCopyNormalizedVersionWithRandomDigits() {
    NpmFormatAttributesExtractor underTest = new NpmFormatAttributesExtractor(new ImmutableMap.Builder<String, Object>()
        .put("version", "1b2b3")
        .build());

    underTest.copyFormatAttributes(formatAttributes);

    assertThat(formatAttributes.get("search_normalized_version"), is("00001b00002b00003"));
  }

  @Test
  public void testCopyFormatAttributesWithBugsAsString() {
    NpmFormatAttributesExtractor underTest = new NpmFormatAttributesExtractor(new ImmutableMap.Builder<String, Object>()
        .put("bugs", "https://www.example.com/bugString")
        .build());

    underTest.copyFormatAttributes(formatAttributes);

    assertThat(formatAttributes.get("bugs_url"), is("https://www.example.com/bugString"));
  }
}
