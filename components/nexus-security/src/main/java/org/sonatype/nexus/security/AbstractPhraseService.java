package org.sonatype.nexus.security;

import org.sonatype.goodies.common.ComponentSupport;

/**
 * Common support for marking and checking customized pass-phrases.
 *
 * @since 3.8
 */
public abstract class AbstractPhraseService
    extends ComponentSupport
    implements PhraseService
{
  private final boolean hasMasterPhrase;

  public AbstractPhraseService(final boolean hasMasterPhrase) {
    this.hasMasterPhrase = hasMasterPhrase;
  }

  protected abstract String getMasterPhrase();

  @Override
  public String getPhrase(final String defaultPhrase) {
    return hasMasterPhrase ? getMasterPhrase() : defaultPhrase;
  }

  @Override
  public String mark(final String value) {
    if (hasMasterPhrase && value != null) {
      if (!value.contains("~{")) {
        return value.replace("{", "~{").replace("}", "}~");
      }
    }
    return value;
  }

  @Override
  public boolean usesLegacyEncoding(final String value) {
    if (hasMasterPhrase && value != null) {
      if (value.contains("~{")) {
        return false;
      }
    }
    return true;
  }
}
