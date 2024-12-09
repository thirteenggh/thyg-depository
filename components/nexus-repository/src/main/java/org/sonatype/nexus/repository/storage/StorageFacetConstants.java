package org.sonatype.nexus.repository.storage;

/**
 * Repository configuration attribute constants related to {@link StorageFacet}.
 *
 * @since 3.11
 * @deprecated use {@link org.sonatype.nexus.repository.config.ConfigurationConstants}
 */
public class StorageFacetConstants
{
  public static final String STORAGE = "storage";

  /**
   * @since 3.19
   */
  public static final String DATA_STORE_NAME = "dataStoreName";

  public static final String BLOB_STORE_NAME = "blobStoreName";

  public static final String WRITE_POLICY = "writePolicy";

  public static final String STRICT_CONTENT_TYPE_VALIDATION = "strictContentTypeValidation";

  private StorageFacetConstants() {
    // static class
  }
}
