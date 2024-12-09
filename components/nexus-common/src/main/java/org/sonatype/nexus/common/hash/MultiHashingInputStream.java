package org.sonatype.nexus.common.hash;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nonnull;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hasher;
import com.google.common.hash.HashingInputStream;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * An {@link InputStream} that maintains multiple hashes and the number of bytes of data read from it.
 *
 * @see HashingInputStream
 * @since 3.0
 */
public class MultiHashingInputStream
    extends FilterInputStream
{
  private final Map<HashAlgorithm, Hasher> hashers = new LinkedHashMap<>();

  private long count;

  public MultiHashingInputStream(final Iterable<HashAlgorithm> algorithms, final InputStream inputStream) {
    super(checkNotNull(inputStream));
    checkNotNull(algorithms);
    for (HashAlgorithm algorithm : algorithms) {
      hashers.put(algorithm, algorithm.function().newHasher());
    }
  }

  @Override
  public int read() throws IOException {
    int b = in.read();
    if (b != -1) {
      for (Hasher hasher : hashers.values()) {
        hasher.putByte((byte) b);
      }
      count++;
    }
    return b;
  }

  @Override
  public int read(@Nonnull final byte[] bytes, final int off, final int len) throws IOException {
    int numRead = in.read(bytes, off, len);
    if (numRead != -1) {
      for (Hasher hasher : hashers.values()) {
        hasher.putBytes(bytes, off, numRead);
      }
      count += numRead;
    }
    return numRead;
  }

  @Override
  public boolean markSupported() {
    return false;
  }

  @Override
  public void mark(final int readlimit) {
    // no-op
  }

  @Override
  public void reset() throws IOException {
    throw new IOException("reset not supported");
  }

  /**
   * Gets the {@link HashCode}s based on the data read from this stream.
   */
  public Map<HashAlgorithm, HashCode> hashes() {
    Map<HashAlgorithm, HashCode> hashes = new HashMap<>(hashers.size());
    for (Entry<HashAlgorithm, Hasher> entry : hashers.entrySet()) {
      hashes.put(entry.getKey(), entry.getValue().hash());
    }
    return hashes;
  }

  /**
   * Gets the number of bytes read from this stream.
   */
  public long count() {
    return count;
  }
}
