package org.sonatype.nexus.pax.exam;

/**
 * Denotes database to use for the test.
 */
public enum TestDatabase
{
  H2(true),
  POSTGRES(true),
  ORIENT(false);

  final boolean useContentStore;

  TestDatabase(final boolean useContentStore) {
    this.useContentStore = useContentStore;
  }

  public boolean isUseContentStore() {
    return useContentStore;
  }
}
