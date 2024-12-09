package org.sonatype.nexus.internal.security.apikey;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.crypto.RandomBytesGenerator;
import org.sonatype.nexus.security.authc.apikey.ApiKeyFactory;

import org.apache.shiro.subject.PrincipalCollection;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Default {@link ApiKeyFactory} that creates random UUID.
 *
 * @since 3.0
 */
@Named("default")
@Singleton
public class DefaultApiKeyFactory
    extends ComponentSupport
    implements ApiKeyFactory
{
  private final RandomBytesGenerator randomBytesGenerator;

  @Inject
  public DefaultApiKeyFactory(final RandomBytesGenerator randomBytesGenerator) {
    this.randomBytesGenerator = checkNotNull(randomBytesGenerator);
  }

  @Override
  public char[] makeApiKey(final PrincipalCollection principals) {
    final String salt = new BigInteger(randomBytesGenerator.generate(4)).toString(32);
    final byte[] code = ("~nexus~default~" + principals + salt).getBytes(StandardCharsets.UTF_8);
    final String apiKey = UUID.nameUUIDFromBytes(code).toString();
    return apiKey.toCharArray();
  }
}
