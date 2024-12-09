package org.sonatype.repository.conan.internal

import org.sonatype.nexus.repository.cache.CacheControllerHolder.CacheType

import static org.sonatype.nexus.repository.cache.CacheControllerHolder.METADATA

/**
 * @since 3.28
 */
enum AssetKind {
  DOWNLOAD_URL(METADATA, "download_urls"),
  DIGEST(METADATA, "digest"),
  CONAN_MANIFEST(METADATA, "conanmanifest.txt"),
  CONAN_FILE(METADATA, "conanfile.py"),
  CONAN_INFO(METADATA, "conaninfo.txt"),
  CONAN_PACKAGE(METADATA, "conan_package.tgz"),
  CONAN_SOURCES(METADATA, "conan_sources.tgz"),
  CONAN_EXPORT(METADATA, "conan_export.tgz"),
  CONAN_PACKAGE_SNAPSHOT(METADATA, null)

  private final CacheType cacheType

  private final String filename

  AssetKind(final CacheType cacheType, final String filename) {
    this.cacheType = cacheType
    this.filename = filename
  }

  CacheType getCacheType() {
    return cacheType
  }

  String getFilename() {
    return filename
  }

  static AssetKind valueFromFileName(final String filename) {
    values().find { it.filename == filename }
  }
}
