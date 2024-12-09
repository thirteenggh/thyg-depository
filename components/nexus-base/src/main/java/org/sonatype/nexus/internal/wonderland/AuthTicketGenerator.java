package org.sonatype.nexus.internal.wonderland;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.crypto.RandomBytesGenerator;

import com.google.common.io.BaseEncoding;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Generates random authentication tickets.
 *
 * @since 2.7
 */
@Named
public class AuthTicketGenerator
    extends ComponentSupport
{
  private static final String CPREFIX = "${wonderland.authTicketGenerator";

  private final RandomBytesGenerator randomBytes;

  private final int defaultSize;

  // NOTE: Default size is 66 to make full use of base64 encoding w/o padding

  @Inject
  public AuthTicketGenerator(final RandomBytesGenerator randomBytes,
                             @Named(CPREFIX + ".defaultSize:-66}") final int defaultSize)
  {
    this.randomBytes = checkNotNull(randomBytes);
    this.defaultSize = defaultSize;
    log.debug("Default size: {}", defaultSize);
  }

  protected String encode(final byte[] bytes) {
    return BaseEncoding.base64().encode(bytes);
  }

  public String generate(final int size) {
    byte[] bytes = randomBytes.generate(size);
    return encode(bytes);
  }

  public String generate() {
    return generate(defaultSize);
  }
}
