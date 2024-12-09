package org.sonatype.nexus.repository.httpbridge.internal;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;

import org.sonatype.nexus.repository.view.PartPayload;
import org.sonatype.nexus.repository.view.Payload;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Servlet multipart-payload adapter.
 *
 * @since 3.0
 */
class HttpPartIteratorAdapter
    implements Iterable<PartPayload>
{
  private final HttpServletRequest httpRequest;

  public HttpPartIteratorAdapter(final HttpServletRequest httpRequest) {
    this.httpRequest = checkNotNull(httpRequest);
  }

  @Override
  public Iterator<PartPayload> iterator() {
    try {
      final FileItemIterator itemIterator = new ServletFileUpload().getItemIterator(httpRequest);
      return new PayloadIterator(itemIterator);
    }
    catch (FileUploadException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * {@link FileItemStream} payload.
   */
  private static class FileItemStreamPayload
      implements PartPayload
  {
    private final FileItemStream next;

    public FileItemStreamPayload(final FileItemStream next) {
      this.next = next;
    }

    @Override
    public InputStream openInputStream() throws IOException {
      return next.openStream();
    }

    @Override
    public long getSize() {
      return -1;
    }

    @Nullable
    @Override
    public String getContentType() {
      return next.getContentType();
    }

    @Nullable
    @Override
    public String getName() {
      return next.getName();
    }

    @Override
    public String getFieldName() {
      return next.getFieldName();
    }

    @Override
    public boolean isFormField() {
      return next.isFormField();
    }
  }

  /**
   * {@link Payload} iterator.
   */
  private static class PayloadIterator
      implements Iterator<PartPayload>
  {
    private final FileItemIterator itemIterator;

    public PayloadIterator(final FileItemIterator itemIterator) {
      this.itemIterator = itemIterator;
    }

    @Override
    public boolean hasNext() {
      try {
        return itemIterator.hasNext();
      }
      catch (FileUploadException | IOException e) {
        throw new RuntimeException(e);
      }
    }

    @Override
    public PartPayload next() {
      try {
        return new FileItemStreamPayload(itemIterator.next());
      }
      catch (FileUploadException | IOException e) {
        throw new RuntimeException(e);
      }
    }

    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

  /**
   * Determine if given request is multipart.
   */
  public static boolean isMultipart(final HttpServletRequest httpRequest) {
    // We're circumventing ServletFileUpload.isMultipartContent as some clients (nuget) use PUT for multipart uploads
    return FileUploadBase.isMultipartContent(new ServletRequestContext(httpRequest));
  }
}
