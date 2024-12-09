package org.sonatype.nexus.repository.cocoapods.internal;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.Format;

/**
 * @since 3.19
 */
@Named(CocoapodsFormat.NAME)
@Singleton
public class CocoapodsFormat
    extends Format
{

  public static final String CDN_METADATA_PREFIX = "cdn_metadata/";

  public static final String NAME = "cocoapods";

  public static final String POD_REMOTE_ATTRIBUTE_NAME = "ccp_remote";

  public static final String PACKAGE_NAME_KEY = "package_name";

  public static final String PACKAGE_VERSION_KEY = "package_version";

  public CocoapodsFormat() {
    super(NAME);
  }

  public static String removeInitialSlashFromPath(final String path) {
    return path.startsWith("/") ? path.substring(1) : path;
  }
}
