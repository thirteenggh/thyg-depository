package org.sonatype.nexus.siesta;

import java.util.UUID;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;

import org.junit.Test;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.MediaType.APPLICATION_XML_TYPE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Tests related to happy paths for a resource.
 */
public class UserIT
    extends SiestaTestSupport
{
  private void put_happyPath(final MediaType mediaType) throws Exception {
    UserXO sent = new UserXO().withName(UUID.randomUUID().toString());

    WebTarget target = client().target(url("user"));
    Response response = target.request()
        .accept(mediaType)
        .put(Entity.entity(sent, mediaType), Response.class);
    log("Status: {}", response.getStatusInfo());

    assertThat(response.getStatusInfo().getFamily(), equalTo(Family.SUCCESSFUL));

    UserXO received = response.readEntity(UserXO.class);
    assertThat(received, is(notNullValue()));
    assertThat(received.getName(), is(equalTo(sent.getName())));
  }

  @Test
  public void put_happyPath_XML()
      throws Exception
  {
    put_happyPath(APPLICATION_XML_TYPE);
  }

  @Test
  public void put_happyPath_JSON()
      throws Exception
  {
    put_happyPath(APPLICATION_JSON_TYPE);
  }
}
