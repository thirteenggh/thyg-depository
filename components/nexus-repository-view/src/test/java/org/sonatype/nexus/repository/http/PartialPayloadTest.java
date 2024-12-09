package org.sonatype.nexus.repository.http;

import java.io.IOException;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.repository.view.payloads.BytesPayload;

import com.google.common.collect.Range;
import com.google.common.io.ByteStreams;
import com.google.common.primitives.Bytes;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests {@link PartialPayload}.
 */
public class PartialPayloadTest
    extends TestSupport
{
  private final byte[] input = Bytes.toArray(asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));

  private final BytesPayload bytesPayload = new BytesPayload(input, "n/a");

  @Test
  public void oneBytePartial() throws IOException {
    final byte[] output = partial(bytesPayload, Range.closed(0L, 0L));

    assertThat(output, is(Bytes.toArray(asList(0))));
  }

  @Test
  public void threeBytePartial() throws IOException {
    final byte[] output = partial(bytesPayload, Range.closed(0L, 2L));

    assertThat(output, is(Bytes.toArray(asList(0, 1, 2))));
  }

  @Test
  public void entireStream() throws IOException {
    final byte[] output = partial(bytesPayload, Range.closed(0L, 9L));

    assertThat(output, is(input));
  }

  private byte[] partial(final BytesPayload bytes, final Range<Long> closed) throws IOException {
    try (final PartialPayload partial = new PartialPayload(bytes, closed)) {
      return ByteStreams.toByteArray(partial.openInputStream());
    }
  }
}
