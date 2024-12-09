package org.sonatype.nexus.repository.npm.internal.audit.parser;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.sonatype.nexus.repository.npm.internal.audit.report.Resolve;
import org.sonatype.nexus.repository.vulnerability.AuditComponent;

import com.google.common.collect.ImmutableSet;
import com.google.common.io.Resources;
import org.apache.commons.io.Charsets;
import org.junit.Test;

import static com.google.common.io.Resources.getResource;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.sonatype.nexus.repository.npm.internal.NpmFormat.NAME;

public class NpmParserTest
{
  private static final String NPM_AUDIT_JSON =
      "org/sonatype/nexus/repository/npm/internal/audit/parser/package-lock.json";

  private static final String NPM_AUDIT_COMPLEX_JSON =
      "org/sonatype/nexus/repository/npm/internal/audit/parser/package-lock-complex-path.json";

  private final Set<AuditComponent> expectedComponents = ImmutableSet.of(
      new AuditComponent(null, NAME, "marked", "0.6.3"),
      new AuditComponent(null, NAME, "debug", "2.3.3"),
      new AuditComponent(null, NAME, "lodash", "2.4.2")
  );

  private final Set<AuditComponent> expectedComplexComponents = ImmutableSet.of(
      new AuditComponent(null, NAME, "marked", "0.6.3"),
      new AuditComponent(null, NAME, "debug", "2.3.3"),
      new AuditComponent(null, NAME, "lodash", "2.4.2"),
      new AuditComponent(null, NAME, "inherits", "2.0.3"),
      new AuditComponent(null, NAME, "debug", "0.0.1")
  );

  @Test
  public void testParsing() throws IOException {
    PackageLock packageLock =
        PackageLockParser.parse(Resources.toString(getResource(NPM_AUDIT_JSON), Charsets.UTF_8));
    Set<AuditComponent> components = packageLock.getComponents(packageLock.getRoot().getApplicationId());
    assertFalse(components.isEmpty());
    assertEquals(expectedComponents, components);
  }

  @Test
  public void testComplexParsing() throws IOException {
    PackageLock packageLock =
        PackageLockParser.parse(Resources.toString(getResource(NPM_AUDIT_COMPLEX_JSON), Charsets.UTF_8));
    Set<AuditComponent> components = packageLock.getComponents(packageLock.getRoot().getApplicationId());
    assertFalse(components.isEmpty());
    assertEquals(expectedComplexComponents, components);
  }

  @Test
  public void testResolve() throws IOException {
    PackageLock packageLock =
        PackageLockParser.parse(Resources.toString(getResource(NPM_AUDIT_COMPLEX_JSON), Charsets.UTF_8));
    checkResolve(packageLock, "debug", "0.0.1", "marked>inherits>debug");
    checkResolve(packageLock, "inherits", "2.0.3", "marked>inherits");
  }

  private void checkResolve(
      final PackageLock packageLock,
      final String name,
      final String version,
      final String expectedPath)
  {
    List<Resolve> resolve = packageLock.createResolve(1, name, version);
    assertEquals(expectedPath, resolve.get(0).getPath());
  }
}
