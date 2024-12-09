package org.sonatype.nexus.pax.logging;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.PatternLayoutEncoderBase;

/**
 * Adds the ability to use %node in an encoder pattern
 *
 * @since 3.6.1
 */
public class NexusLayoutEncoder
    extends PatternLayoutEncoderBase<ILoggingEvent>
{
  @Override
  public void start() {
    PatternLayout patternLayout = new PatternLayout();
    patternLayout.getDefaultConverterMap().put("node", NexusNodeNameConverter.class.getName());
    patternLayout.setContext(context);
    patternLayout.setPattern(getPattern());
    patternLayout.start();
    this.layout = patternLayout;
    super.start();
  }
}
