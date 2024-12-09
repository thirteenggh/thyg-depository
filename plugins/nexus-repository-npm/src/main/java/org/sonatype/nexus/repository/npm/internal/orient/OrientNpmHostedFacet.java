package org.sonatype.nexus.repository.npm.internal.orient;

import java.io.IOException;
import java.util.Map;

import org.sonatype.nexus.repository.Facet;
import org.sonatype.nexus.repository.npm.internal.NpmHostedFacet;
import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.view.payloads.TempBlob;

/**
 * npm hosted facet.
 *
 * @since 3.0
 */
@Facet.Exposed
public interface OrientNpmHostedFacet
    extends Facet, NpmHostedFacet
{
  /**
   * Add the package using the package.json and <code>TempBlob</code>.
   *
   * @since 3.7
   */
  Asset putPackage(Map<String, Object> packageJson, TempBlob tempBlob) throws IOException;
}
