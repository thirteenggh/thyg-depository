package org.sonatype.nexus.quartz.internal.orient;

/**
 * Test value for marshalling purposes.
 */
public class TestMarshalledValue
{
  private String text;

  public void setText(final String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }
}
