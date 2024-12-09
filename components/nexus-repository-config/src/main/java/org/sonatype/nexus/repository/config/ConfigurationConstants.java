package org.sonatype.nexus.repository.config;

/**
 * Repository configuration constants.
 *
 * @since 3.22
 */
public interface ConfigurationConstants
{
  String STORAGE = "storage";

  String DATA_STORE_NAME = "dataStoreName";

  String BLOB_STORE_NAME = "blobStoreName";

  String WRITE_POLICY = "writePolicy";

  String WRITE_POLICY_DEFAULT = "ALLOW";

  String STRICT_CONTENT_TYPE_VALIDATION = "strictContentTypeValidation";

  String GROUP_WRITE_MEMBER = "groupWriteMember";
}
