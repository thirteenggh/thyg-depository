package org.sonatype.nexus.internal.metrics;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.google.common.net.HttpHeaders.CONTENT_DISPOSITION;

/**
 * Customized {@link com.codahale.metrics.servlets.ThreadDumpServlet} to support download.
 *
 * @since 3.0
 */
public class ThreadDumpServlet
  extends com.codahale.metrics.servlets.ThreadDumpServlet
{
  @Override
  protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
      throws ServletException, IOException
  {
    boolean download = Boolean.parseBoolean(req.getParameter("download"));
    if (download) {
      resp.addHeader(CONTENT_DISPOSITION, "attachment; filename='threads.txt'");
    }

    super.doGet(req, resp);
  }
}
