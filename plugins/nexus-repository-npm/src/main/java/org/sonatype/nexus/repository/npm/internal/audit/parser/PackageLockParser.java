package org.sonatype.nexus.repository.npm.internal.audit.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

/**
 * Parser for package-lock.json, shrinkwrap.json is also parsable by this parser as it has same format
 *
 * @since 3.24
 */
public class PackageLockParser
{
  public static final String ROOT = "root";

  private static final Gson gson = new Gson();

  private PackageLockParser() {
  }

  public static PackageLock parse(final String jsonString) {
    RootPackageLockNode rootNode = gson.fromJson(jsonString, RootPackageLockNode.class);
    return new PackageLock(getAllDependencies(ROOT, rootNode, null, new HashMap<>()));
  }

  private static Map<String, List<PackageLockNode>> getAllDependencies(
      final String key,
      final PackageLockNode packageLockNode,
      final String parentNode,
      final Map<String, List<PackageLockNode>> npmDependencies)
  {
    packageLockNode.setParentNodeName(parentNode);
    npmDependencies.computeIfAbsent(key, k -> new ArrayList<>()).add(packageLockNode);
    if (packageLockNode.getDependencies() != null) {
      packageLockNode.getDependencies().forEach((dependencyKey, dependencyValue) -> {
            String parentKey = !ROOT.equals(key) ? key : null;
            getAllDependencies(dependencyKey, dependencyValue, parentKey, npmDependencies);
          }
      );
    }

    return npmDependencies;
  }
}
