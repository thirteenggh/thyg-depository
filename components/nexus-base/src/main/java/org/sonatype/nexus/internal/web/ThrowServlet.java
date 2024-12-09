package org.sonatype.nexus.internal.web;

import java.io.IOException;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sonatype.nexus.common.text.Strings2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper servlet to throw an exception.
 *
 * @since 3.0
 */
@Named
@Singleton
public class ThrowServlet
    extends HttpServlet
{
  private static final Logger log = LoggerFactory.getLogger(ThrowServlet.class);

  private enum Type
  {
    RUNTIME,
    ERROR,
    IO,
    SERVLET;

    static Type parse(final String value) {
      if (value == null) {
        return RUNTIME;
      }
      return valueOf(Strings2.upper(value));
    }
  }

  @Override
  protected void service(final HttpServletRequest request, final HttpServletResponse response)
      throws ServletException, IOException
  {
    Type type = Type.parse(request.getParameter("type"));
    String message = request.getParameter("message");
    log.info("Throwing {} w/message: {}", type, message);

    switch (type) {
      case RUNTIME:
        throw new RuntimeException(message);

      case IO:
        throw new IOException(message);

      case SERVLET:
        throw new ServletException(message);

      case ERROR:
      default:
        throw new Error(message);
    }
  }
}
