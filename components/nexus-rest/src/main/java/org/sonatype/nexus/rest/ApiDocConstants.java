package org.sonatype.nexus.rest;

/**
 * Constants for REST API documentation.
 *
 * @since 3.20
 */
public class ApiDocConstants
{
  private ApiDocConstants() {
  }

  public static final String AUTHENTICATION_REQUIRED = "Authentication required";

  public static final String INSUFFICIENT_PERMISSIONS = "Insufficient permissions";

  public static final String REPOSITORY_NOT_FOUND = "Repository not found";

  public static final String REPOSITORY_CREATED = "Repository created";

  public static final String REPOSITORY_UPDATED = "Repository updated";

  public static final String REPOSITORY_DELETED = "Repository deleted";

  public static final String API_REPOSITORY_MANAGEMENT = "Repository Management";

  public static final String API_BLOB_STORE = "Blob store";

  public static final String S3_BLOB_STORE_CREATED = "S3 blob store created";

  public static final String S3_BLOB_STORE_UPDATED = "S3 blob store updated";

  public static final String S3_BLOB_STORE_DELETED = "S3 blob store deleted";

  public static final String UNKNOWN_S3_BLOB_STORE = "Specified S3 blob store doesn't exist";

  public static final String DISABLED_IN_HIGH_AVAILABILITY = "Feature is disabled in High Availability";
}
