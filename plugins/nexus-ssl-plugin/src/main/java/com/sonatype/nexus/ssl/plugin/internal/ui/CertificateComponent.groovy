package com.sonatype.nexus.ssl.plugin.internal.ui

import org.apache.shiro.authz.annotation.RequiresAuthentication

import java.security.cert.Certificate

import javax.annotation.Nullable
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

import com.sonatype.nexus.ssl.plugin.PemCertificate
import org.sonatype.nexus.ssl.TrustStore
import com.sonatype.nexus.ssl.plugin.internal.CertificateRetriever

import org.sonatype.nexus.extdirect.DirectComponent
import org.sonatype.nexus.extdirect.DirectComponentSupport
import org.sonatype.nexus.validation.Validate

import com.codahale.metrics.annotation.ExceptionMetered
import com.codahale.metrics.annotation.Timed
import com.softwarementors.extjs.djn.config.annotations.DirectAction
import com.softwarementors.extjs.djn.config.annotations.DirectMethod
import org.hibernate.validator.constraints.NotBlank
import org.hibernate.validator.constraints.NotEmpty

import static com.sonatype.nexus.ssl.plugin.internal.ui.TrustStoreComponent.asCertificateXO
import static org.sonatype.nexus.ssl.CertificateUtil.calculateFingerprint
import static org.sonatype.nexus.ssl.CertificateUtil.decodePEMFormattedCertificate

/**
 * SSL Certificate {@link DirectComponent}.
 *
 * @since 3.0
 */
@Named
@Singleton
@DirectAction(action = 'ssl_Certificate')
class CertificateComponent
extends DirectComponentSupport
{
  @Inject
  TrustStore trustStore

  @Inject
  CertificateRetriever certificateRetriever

  /**
   * Retrieves certificate given a host/port.
   * @param host to get certificate from
   * @param port
   * @param protocolHint
   * @return certificate
   */
  @DirectMethod
  @Timed
  @ExceptionMetered
  @Validate
  @RequiresAuthentication
  CertificateXO retrieveFromHost(final @NotEmpty String host,
                                 final @Nullable Integer port,
                                 final @Nullable String protocolHint)
  {
    Certificate[] chain
    try {
      chain = certificateRetriever.retrieveCertificates(host, port, protocolHint)
    }
    catch (Exception e) {
      String errorMessage = e.message
      if (e instanceof UnknownHostException) {
        errorMessage = "Unknown host '${host}'"
      }
      throw new IOException(errorMessage)
    }
    if (!chain || chain.length == 0) {
      int actualPort = port ?: 443
      throw new IOException("Could not retrieve an SSL certificate from '${host}:${actualPort}'")
    }
    return asCertificateXO(chain[0], isInTrustStore(chain[0]))
  }

  /**
   * Retrieves certificate given a certificate pem.
   * @param pem certificate in PEM format
   * @return certificate
   */
  @DirectMethod
  @Timed
  @ExceptionMetered
  @Validate
  @RequiresAuthentication
  CertificateXO details(final @NotBlank @PemCertificate String pem) {
    Certificate certificate = decodePEMFormattedCertificate(pem)
    return asCertificateXO(certificate, isInTrustStore(certificate))
  }

  boolean isInTrustStore(final Certificate certificate) {
    try {
      return trustStore.getTrustedCertificate(calculateFingerprint(certificate)) != null
    }
    catch (Exception ignore) {
      return false
    }
  }
}
