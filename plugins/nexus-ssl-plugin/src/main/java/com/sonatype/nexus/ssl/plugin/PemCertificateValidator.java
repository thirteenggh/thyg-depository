package com.sonatype.nexus.ssl.plugin;

import java.security.cert.CertificateException;

import javax.validation.ConstraintValidatorContext;

import org.sonatype.nexus.ssl.CertificateUtil;
import org.sonatype.nexus.validation.ConstraintValidatorSupport;

/**
 * {@link PemCertificate} validator.
 *
 * @since 3.0
 */
public class PemCertificateValidator
    extends ConstraintValidatorSupport<PemCertificate, String>
{
  @Override
  public boolean isValid(final String value, final ConstraintValidatorContext context) {
    try {
      CertificateUtil.decodePEMFormattedCertificate(value);
      return true;
    }
    catch (CertificateException e) {
      return false;
    }
  }
}
