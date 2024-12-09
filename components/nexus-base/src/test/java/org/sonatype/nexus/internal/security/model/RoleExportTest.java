package org.sonatype.nexus.internal.security.model;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.sonatype.nexus.security.config.CRole;
import org.sonatype.nexus.security.config.SecurityConfiguration;
import org.sonatype.nexus.supportzip.datastore.JsonExporter;

import com.google.common.collect.ImmutableSet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests validity of Serialization/Deserialization {@link CRole} by {@link RoleExport}
 */
public class RoleExportTest
{
  private final JsonExporter jsonExporter = new JsonExporter();

  private File jsonFile;

  @Before
  public void setup() throws IOException {
    jsonFile = File.createTempFile("CRole", ".json");
  }

  @After
  public void tearDown() {
    jsonFile.delete();
  }

  @Test
  public void testExportImportToJson() throws Exception {
    List<CRole> roles = Arrays.asList(
        createCRole("Role 1"),
        createCRole("Role 2"));

    SecurityConfiguration source = mock(SecurityConfiguration.class);
    when(source.getRoles()).thenReturn(roles);

    RoleExport exporter = new RoleExport(source);
    exporter.export(jsonFile);
    List<CRoleData> importedData = jsonExporter.importFromJson(jsonFile, CRoleData.class);

    assertThat(importedData.size(), is(2));
    assertThat(importedData.toString(), allOf(
        containsString("Role 1"),
        containsString("Role 2"),
        containsString("Description"),
        containsString("nx-all"),
        containsString("nx-admin"),
        containsString("nx-user"),
        containsString("custom-role"),
        containsString("admin-role"),
        containsString("user-role")));
  }

  private CRole createCRole(final String name) {
    CRoleData role = new CRoleData();
    role.setId(UUID.randomUUID().toString());
    role.setName(name);
    role.setDescription("Description");
    role.setReadOnly(true);
    role.setVersion(1);
    role.setPrivileges(ImmutableSet.of("nx-all", "nx-admin", "nx-user"));
    role.setRoles(ImmutableSet.of("custom-role", "admin-role", "user-role"));

    return role;
  }
}
