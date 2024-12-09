package org.sonatype.repository.conan.internal.orient.hosted.v1;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.view.Router;
import org.sonatype.repository.conan.internal.orient.common.v1.ConanControllerV1;
import org.sonatype.repository.conan.internal.common.v1.ConanRoutes;

import static org.sonatype.repository.conan.internal.AssetKind.CONAN_EXPORT;
import static org.sonatype.repository.conan.internal.AssetKind.CONAN_FILE;
import static org.sonatype.repository.conan.internal.AssetKind.CONAN_INFO;
import static org.sonatype.repository.conan.internal.AssetKind.CONAN_MANIFEST;
import static org.sonatype.repository.conan.internal.AssetKind.CONAN_PACKAGE;
import static org.sonatype.repository.conan.internal.AssetKind.CONAN_SOURCES;
import static org.sonatype.repository.conan.internal.AssetKind.DIGEST;
import static org.sonatype.repository.conan.internal.AssetKind.DOWNLOAD_URL;

/**
 * @since 3.28
 */
@Named
@Singleton
public class ConanHostedControllerV1
    extends ConanControllerV1
{
  public void attach(final Router.Builder builder) {
    createRoute(builder, ConanRoutes.uploadUrls(), DOWNLOAD_URL, HostedHandlers.uploadUrl);
    createRoute(builder, ConanRoutes.uploadManifest(), CONAN_MANIFEST, HostedHandlers.uploadContentHandler);
    createRoute(builder, ConanRoutes.uploadConanfile(), CONAN_FILE, HostedHandlers.uploadContentHandler);
    createRoute(builder, ConanRoutes.uploadConanInfo(), CONAN_INFO, HostedHandlers.uploadContentHandler);
    createRoute(builder, ConanRoutes.uploadConanPackageZip(), CONAN_PACKAGE, HostedHandlers.uploadContentHandler);
    createRoute(builder, ConanRoutes.uploadConanSource(), CONAN_SOURCES, HostedHandlers.uploadContentHandler);
    createRoute(builder, ConanRoutes.uploadConanExportZip(), CONAN_EXPORT, HostedHandlers.uploadContentHandler);

    createRoute(builder, ConanRoutes.digest(), DIGEST, HostedHandlers.getDigest);
    createGetRoutes(builder,
        HostedHandlers.getDownloadUrl,
        HostedHandlers.getAssets,
        HostedHandlers.getPackageSnapshot
    );
  }
}
