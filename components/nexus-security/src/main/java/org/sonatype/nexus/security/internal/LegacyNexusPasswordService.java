package org.sonatype.nexus.security.internal;

import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.format.HexFormat;

/**
 * Legacy {@link PasswordService}.
 *
 * PasswordService for handling legacy passwords (SHA-1 and MD5).
 */
@Named("legacy")
@Singleton
public class LegacyNexusPasswordService
    implements PasswordService
{
  private final DefaultPasswordService sha1PasswordService;

  private final DefaultPasswordService md5PasswordService;

  public LegacyNexusPasswordService() {
    //Initialize and configure sha1 password service
    this.sha1PasswordService = new DefaultPasswordService();
    DefaultHashService sha1HashService = new DefaultHashService();
    sha1HashService.setHashAlgorithmName("SHA-1");
    sha1HashService.setHashIterations(1);
    sha1HashService.setGeneratePublicSalt(false);
    this.sha1PasswordService.setHashService(sha1HashService);
    this.sha1PasswordService.setHashFormat(new HexFormat());

    //Initialize and configure md5 password service
    this.md5PasswordService = new DefaultPasswordService();
    DefaultHashService md5HashService = new DefaultHashService();
    md5HashService.setHashAlgorithmName("MD5");
    md5HashService.setHashIterations(1);
    md5HashService.setGeneratePublicSalt(false);
    this.md5PasswordService.setHashService(md5HashService);
    this.md5PasswordService.setHashFormat(new HexFormat());
  }

  @Override
  public String encryptPassword(final Object plaintextPassword) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean passwordsMatch(final Object submittedPlaintext, final String encrypted) {
    //Legacy passwords can be hashed with sha-1 or md5, check both

    return sha1PasswordService.passwordsMatch(submittedPlaintext, encrypted) ||
        md5PasswordService.passwordsMatch(submittedPlaintext, encrypted);
  }
}
