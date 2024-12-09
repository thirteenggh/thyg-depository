package org.sonatype.nexus.repository.httpbridge;

import java.io.IOException;

import javax.annotation.Nullable;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.sonatype.nexus.repository.view.Request;
import org.sonatype.nexus.repository.view.Response;

/**
 * Allows repository format specific handling of HTTP response sending.
 *
 * @since 3.0
 */
public interface HttpResponseSender
{
  void send(@Nullable Request request, Response response, HttpServletResponse httpServletResponse)
      throws ServletException, IOException;
}
