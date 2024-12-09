package org.sonatype.nexus.formfields;

import org.sonatype.goodies.testsupport.TestSupport;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class RepositoryComboboxTest
    extends TestSupport

{
  RepositoryCombobox underTest;

  @Before
  public void setUp() throws Exception {
    underTest = new RepositoryCombobox("test");
  }

  @Test
  public void includeAnEntryForAllRepositories() {
    underTest.includeAnEntryForAllRepositories();

    assertThat(underTest.getStoreFilters(), nullValue());
    assertThat(underTest.getStoreApi(), is("coreui_Repository.readReferencesAddingEntryForAll"));
  }

  @Test
  public void testFormatFilters() {
    underTest.excludingAnyOfFormats("nuget", "npm");
    underTest.includingAnyOfFormats("maven", "docker");

    assertThat(underTest.getStoreFilters().get("format"), is("maven,docker,!nuget,!npm"));
  }

  @Test
  public void testVersionPolicyFilters() {
    underTest.excludingAnyOfVersionPolicies("RELEASE");
    underTest.includingAnyOfVersionPolicies("MIXED", "SNAPSHOT");

    assertThat(underTest.getStoreFilters().get("versionPolicies"), is("MIXED,SNAPSHOT,!RELEASE"));
  }

  @Test
  public void testVersionPolicyFilters_onlyExclude() {
    underTest.excludingAnyOfVersionPolicies("RELEASE", "MIXED");

    assertThat(underTest.getStoreFilters().get("versionPolicies"), is("!RELEASE,!MIXED"));
  }

  @Test
  public void testVersionPolicyFilters_onlyInclude() {
    underTest.includingAnyOfVersionPolicies("RELEASE", "MIXED");

    assertThat(underTest.getStoreFilters().get("versionPolicies"), is("RELEASE,MIXED"));
  }

  @Test
  public void testFormatFilters_noVersionPolicies() {
    underTest.excludingAnyOfFormats("nuget", "npm");
    underTest.includingAnyOfFormats("maven", "docker");

    assertThat(underTest.getStoreFilters().containsKey("versionPolicies"), is(false));
  }

}
