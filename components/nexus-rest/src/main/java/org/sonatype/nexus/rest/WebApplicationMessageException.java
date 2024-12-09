package org.sonatype.nexus.rest;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import static com.google.common.base.Preconditions.checkNotNull;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

/**
 * {@link WebApplicationException} with {@link Status} and a text message.
 *
 * @since 3.8
 */
public class WebApplicationMessageException
    extends WebApplicationException
{
  public WebApplicationMessageException(final Status status, final String message) {
    this(status, message, TEXT_PLAIN);
  }

  public WebApplicationMessageException(final Status status, final Object message, final String mediaType) {
    super(Response.status(checkNotNull(status)).entity(checkNotNull(message)).type(mediaType).build());
  }
}
