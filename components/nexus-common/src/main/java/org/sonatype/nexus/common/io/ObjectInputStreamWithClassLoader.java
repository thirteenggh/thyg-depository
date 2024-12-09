package org.sonatype.nexus.common.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Allows a custom class loader to be used with ObjectInputStream.
 *
 * @since 3.6
 */
public class ObjectInputStreamWithClassLoader
    extends ObjectInputStream
{
  @FunctionalInterface
  public interface LoadingFunction
  {
    Class<?> loadClass(String name) throws ClassNotFoundException;
  }

  private final LoadingFunction classLoading;

  public ObjectInputStreamWithClassLoader(final InputStream inputStream, final LoadingFunction classLoading)
      throws IOException
  {
    super(inputStream);
    this.classLoading = checkNotNull(classLoading);
  }

  public ObjectInputStreamWithClassLoader(final InputStream inputStream, final ClassLoader loader)
      throws IOException
  {
    super(inputStream);
    checkNotNull(loader);
    this.classLoading = name -> Class.forName(name, false, loader);
  }

  @Override
  protected Class<?> resolveClass(final ObjectStreamClass classDesc)
      throws ClassNotFoundException
  {
    return classLoading.loadClass(classDesc.getName());
  }
}
