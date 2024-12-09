package org.sonatype.nexus.repository.r.internal;

/**
 * R format specific attributes. This is far from an exhaustive list but contains the most important ones.
 *
 * @since 3.28
 */
public final class RAttributes
{

  public static final String P_PACKAGE = "Package";

  public static final String P_VERSION = "Version";

  public static final String P_DEPENDS = "Depends";

  public static final String P_IMPORTS = "Imports";

  public static final String P_SUGGESTS = "Suggests";

  public static final String P_LINKINGTO = "LinkingTo";

  public static final String P_LICENSE = "License";

  public static final String P_NEEDS_COMPILATION = "NeedsCompilation";

  private RAttributes() {
    // empty
  }
}
