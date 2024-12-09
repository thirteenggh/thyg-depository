package org.sonatype.nexus.repository.view;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Nullable;

import com.google.common.io.ByteStreams;

/**
 * Payload.
 *
 * @since 3.0
 */
public interface Payload
    extends Closeable
{
  long UNKNOWN_SIZE = -1;

  InputStream openInputStream() throws IOException;

  long getSize();

  @Nullable
  String getContentType();

  /**
   * Closes this payload, relinquishing any underlying resources. Streams previously handed out by
   * {@link #openInputStream()} may not be affected by this method and should be closed separately.
   *
   * @since 3.1
   */
  @Override
  default void close() throws IOException {
    // no underlying resources to clean-up by default
  }

  /**
   * Provide a handle on the copying of the {@link InputStream} to the given {@link OutputStream}.
   * Callers are required to handle the flushing and closing of the streams. By default we
   * do a {@link ByteStreams#copy(InputStream, OutputStream)}.
   *
   * @param input  {@link InputStream}
   * @param output {@link OutputStream}
   */
  default void copy(final InputStream input, final OutputStream output) throws IOException {
    ByteStreams.copy(input, output);
  }
}
