package org.sonatype.nexus.siesta;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.sonatype.nexus.rest.ExceptionMapperSupport;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Tests error handling.
 */
public class ErrorsIT
    extends SiestaTestSupport
{
  @Test
  public void errorResponseHasFaultId() throws Exception {
    WebTarget target = client().target(url("errors/406"));
    Response response = target.request().get(Response.class);
    log("Status: {}", response.getStatusInfo());

    assertThat(response.getStatusInfo().getStatusCode(), equalTo(406));
    String faultId = response.getHeaderString(ExceptionMapperSupport.X_SIESTA_FAULT_ID);
    log("Fault ID: {}", faultId);
    assertThat(faultId, notNullValue());
  }
}
