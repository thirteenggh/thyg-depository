package org.sonatype.nexus.pax.logging;

import ch.qos.logback.access.PatternLayout;
import ch.qos.logback.access.pattern.RemoteUserConverter;
import ch.qos.logback.access.spi.IAccessEvent;
import ch.qos.logback.core.pattern.PatternLayoutEncoderBase;

/**
 * Encoder that configures {@code %u}, {@code %user}, and {@code %thread} converter patterns to be converted by
 * {@link NexusUserIdConverter}, and {@link NexusThreadConverter} instead of {@link RemoteUserConverter}.
 *
 * @since 3.0
 */
public class AccessPatternLayoutEncoder
    extends PatternLayoutEncoderBase<IAccessEvent>
{
  @Override
  public void start() {
    PatternLayout patternLayout = new PatternLayout();
    patternLayout.getDefaultConverterMap().put("u", NexusUserIdConverter.class.getName());
    patternLayout.getDefaultConverterMap().put("user", NexusUserIdConverter.class.getName());
    patternLayout.getDefaultConverterMap().put("thread", NexusThreadConverter.class.getName());
    patternLayout.setContext(context);
    patternLayout.setPattern(getPattern());
    patternLayout.start();
    this.layout = patternLayout;
    super.start();
  }
}
