package org.sonatype.nexus.crypto.internal;

import java.security.SecureRandom;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.crypto.CryptoHelper;
import org.sonatype.nexus.crypto.RandomBytesGenerator;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Default {@link RandomBytesGenerator} implementation.
 *
 * @since 3.0
 */
@Named
public class RandomBytesGeneratorImpl
    extends ComponentSupport
    implements RandomBytesGenerator
{
  private final SecureRandom random;

  @Inject
  public RandomBytesGeneratorImpl(final CryptoHelper crypto) {
    this.random = checkNotNull(crypto).createSecureRandom();
  }

  public byte[] generate(final int size) {
    checkArgument(size > 0);
    byte[] bytes = new byte[size];
    random.nextBytes(bytes);
    return bytes;
  }
}
