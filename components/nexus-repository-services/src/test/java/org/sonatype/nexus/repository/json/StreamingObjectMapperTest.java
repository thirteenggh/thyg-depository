package org.sonatype.nexus.repository.json;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.sonatype.goodies.testsupport.TestSupport;

import org.junit.Test;

import static com.fasterxml.jackson.databind.SerializationFeature.FLUSH_AFTER_WRITE_VALUE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StreamingObjectMapperTest
    extends TestSupport
{
  private StreamingObjectMapper underTest = new StreamingObjectMapper();

  @Test
  public void should_Write_Exactly_What_Was_Read() throws IOException {
    String json = "{}";
    ByteArrayInputStream input = new ByteArrayInputStream(json.getBytes());
    ByteArrayOutputStream output = new ByteArrayOutputStream();

    underTest.readAndWrite(input, output);

    assertThat(json, equalTo(new String(output.toByteArray())));

    json = "{\"_id\":\"simple\",\"name\":\"simple\",\"description\":\"simplestuff\"}";
    input = new ByteArrayInputStream(json.getBytes());
    output = new ByteArrayOutputStream();
    underTest.readAndWrite(input, output);

    assertThat(json, equalTo(new String(output.toByteArray())));
  }

  @Test
  public void should_Write_MinimizedJson_Of_What_Was_Read() throws IOException {
    String prettyJson = "{\n\"_id\": \"simple\",\n\"name\": \"simple\",\n\"description\": \"simplestuff\"}";

    ByteArrayInputStream input = new ByteArrayInputStream(prettyJson.getBytes());
    ByteArrayOutputStream output = new ByteArrayOutputStream();

    underTest.readAndWrite(input, output);

    String minifiedJson = prettyJson.replaceAll("\n", "").replace(" ", "");
    assertThat(minifiedJson, equalTo(new String(output.toByteArray())));
  }

  @Test
  public void should_Configure_SerializationFeature() {
    assertTrue(underTest.isEnabled(FLUSH_AFTER_WRITE_VALUE));

    underTest.configure(FLUSH_AFTER_WRITE_VALUE, false);

    assertFalse(underTest.isEnabled(FLUSH_AFTER_WRITE_VALUE));
  }
}
