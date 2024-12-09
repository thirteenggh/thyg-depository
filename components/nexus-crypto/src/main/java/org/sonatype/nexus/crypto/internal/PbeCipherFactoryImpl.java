package org.sonatype.nexus.crypto.internal;

import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.crypto.CryptoHelper;
import org.sonatype.nexus.crypto.PbeCipherFactory;

import com.google.common.base.Throwables;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Default implementation of {@code PbeCipherFactory} interface to provide password-based-encryption.
 *
 * @since 3.0
 */
@Named
@Singleton
public class PbeCipherFactoryImpl
    extends ComponentSupport
    implements PbeCipherFactory
{
  private final CryptoHelper cryptoHelper;

  @Inject
  public PbeCipherFactoryImpl(final CryptoHelper cryptoHelper)
  {
    this.cryptoHelper = checkNotNull(cryptoHelper);
  }

  @Override
  public PbeCipher create(final String password, final String salt, final String iv) throws Exception {
    checkNotNull(password);
    checkNotNull(salt);
    checkNotNull(iv);
    return new PbeCipherImpl(cryptoHelper, password, salt, iv);
  }

  private static class PbeCipherImpl
      implements PbeCipher
  {
    private final CryptoHelper cryptoHelper;

    private final AlgorithmParameterSpec paramSpec;

    private final SecretKey secretKey;

    public PbeCipherImpl(final CryptoHelper cryptoHelper,
                         final String password,
                         final String salt,
                         final String iv) throws Exception
    {
      this.cryptoHelper = cryptoHelper;
      this.paramSpec = new IvParameterSpec(iv.getBytes());
      KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 1024, 128);
      SecretKeyFactory factory = cryptoHelper.createSecretKeyFactory("PBKDF2WithHmacSHA1");
      SecretKey tmp = factory.generateSecret(spec);
      this.secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
    }

    @Override
    public byte[] encrypt(final byte[] bytes) {
      return transform(Cipher.ENCRYPT_MODE, bytes);
    }

    @Override
    public byte[] decrypt(final byte[] bytes) {
      return transform(Cipher.DECRYPT_MODE, bytes);
    }

    private byte[] transform(final int mode, final byte[] bytes) {
      try {
        Cipher cipher = cryptoHelper.createCipher("AES/CBC/PKCS5Padding");
        cipher.init(mode, secretKey, paramSpec);
        return cipher.doFinal(bytes, 0, bytes.length);
      }
      catch (Exception e) {
        Throwables.throwIfUnchecked(e);
        throw new RuntimeException(e);
      }
    }
  }
}
