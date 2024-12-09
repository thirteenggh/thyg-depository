package org.sonatype.nexus.repository.pypi.orient.internal;

import java.io.IOException;
import java.util.Map;

import org.sonatype.nexus.repository.Facet;
import org.sonatype.nexus.repository.pypi.internal.SignablePyPiPackage;
import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.view.Content;
import org.sonatype.nexus.repository.view.payloads.TempBlob;
import org.sonatype.nexus.repository.view.payloads.TempBlobPartPayload;

/**
 * Persistence for PyPI hosted.
 *
 * @since 3.1
 */
@Facet.Exposed
public interface OrientPyPiHostedFacet
    extends Facet
{
  /**
   * Retrieve root index.
   *
   * @return simple root HTML
   */
  Content getRootIndex();

  /**
   * Retrieve index.
   *
   * @param name package name
   * @return simple package HTML
   */
  Content getIndex(String name) throws IOException;

  /**
   * Retrieve package.
   *
   * @param packagePath the full package path
   * @return the package content
   */
  Content getPackage(String packagePath);

  /**
   * Perform package upload.
   *
   * @param filename the name of the file
   * @param attributes the package attributes
   * @param payload uploaded file content
   * @return the created/updated asset
   */
  Asset upload(String filename, Map<String, String> attributes, TempBlobPartPayload payload) throws IOException;

  /**
   * Perform a signature file upload
   *
   * @since 3.26
   * @param name the package name
   * @param version the package version
   * @param payload uploaded file content
   * @return the asset content
   */
  Content uploadSignature(final String name, final String version, final TempBlobPartPayload payload);

  /**
   * Perform package and gpgSignature (if set) upload
   * @param pyPiPackage A PyPi package which may have a gpgSignature
   * @return the created/updated asset which represents the PyPi package wheel file
   */
  Asset upload(SignablePyPiPackage pyPiPackage) throws IOException;

  /**
   * Extract metadata from a package
   *
   * @param tempBlob the temporary blob
   * @return the created/updated asset
   */
  Map<String, String> extractMetadata(TempBlob tempBlob) throws IOException;

  /**
   * Create path to the package indicated by the name, version and filename.
   *
   * @param name the package name
   * @param version the package version
   * @param filename the filename
   * @return the path
   */
  String createPackagePath(String name, String version, String filename);
}
