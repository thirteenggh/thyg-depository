package org.sonatype.nexus.blobstore.rest;

import org.sonatype.nexus.rest.WebApplicationMessageException;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

/**
 * @since 3.19
 */
public class BlobStoreResourceUtil
{
  public static void throwBlobStoreNotFoundException() throws WebApplicationMessageException {
    throw new WebApplicationMessageException(
        NOT_FOUND,
        "\"Unable to find blobstore\"",
        APPLICATION_JSON
    );
  }
}
