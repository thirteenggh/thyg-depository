package com.sonatype.nexus.ssl.plugin.internal.rest;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Path;

import com.sonatype.nexus.ssl.plugin.internal.CertificateRetriever;

import org.sonatype.nexus.rest.APIConstants;
import org.sonatype.nexus.security.internal.rest.SecurityApiResourceBeta;
import org.sonatype.nexus.ssl.TrustStore;

import io.swagger.annotations.Api;

/**
 * @since 3.26
 * @deprecated beta prefix is being phased out, prefer starting new APIs with {@link APIConstants#V1_API_PREFIX} instead
 */
@Api(hidden = true)
@Named
@Singleton
@Path(CertificateApiResourceBeta.RESOURCE_URI)
@Deprecated
public class CertificateApiResourceBeta
    extends CertificateApiResource
{
  static final String RESOURCE_URI = SecurityApiResourceBeta.BETA_RESOURCE_URI + "ssl/";

  @Inject
  public CertificateApiResourceBeta(
      final TrustStore trustStore,
      final CertificateRetriever certificateRetriever)
  {
    super(trustStore, certificateRetriever);
  }
}
