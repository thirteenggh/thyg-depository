package org.sonatype.nexus.siesta;

import java.util.List;

import javax.ws.rs.client.WebTarget;

import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Test use of {@link Echo} proxy to access {@link EchoResource}.
 */
public class EchoIT
    extends SiestaTestSupport
{
  @Test
  public void basic() throws Exception {
    WebTarget target = client().target(url());
    Echo echo = ((ResteasyWebTarget)target).proxy(Echo.class);
    List<String> result = echo.get("hi");
    assertThat(result, notNullValue());
    assertThat(result, hasItem("foo=hi"));
  }
}
