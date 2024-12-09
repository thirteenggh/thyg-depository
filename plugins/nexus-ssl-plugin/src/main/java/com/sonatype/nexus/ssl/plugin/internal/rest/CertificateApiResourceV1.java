package com.sonatype.nexus.ssl.plugin.internal.rest;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Path;

import com.sonatype.nexus.ssl.plugin.internal.CertificateRetriever;

import org.sonatype.nexus.security.internal.rest.SecurityApiResourceV1;
import org.sonatype.nexus.ssl.TrustStore;

/**
 * @since 3.26
 */
@Named
@Singleton
@Path(CertificateApiResourceV1.RESOURCE_URI)
public class CertificateApiResourceV1
    extends CertificateApiResource
{
  static final String RESOURCE_URI = SecurityApiResourceV1.V1_RESOURCE_URI + "ssl/";

  @Inject
  public CertificateApiResourceV1(
      final TrustStore trustStore,
      final CertificateRetriever certificateRetriever)
  {
    super(trustStore, certificateRetriever);
  }
}
