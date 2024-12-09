package org.sonatype.nexus.repository.routing.internal;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.routing.RoutingRuleHelper;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Parameters;
import org.sonatype.nexus.repository.view.Request;
import org.sonatype.nexus.repository.view.Response;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RoutingRuleHandlerTest
    extends TestSupport
{
  private static final String SOME_PATH = "/some/path";

  private RoutingRuleHandler underTest;

  @Mock
  private RoutingRuleHelper routingRuleHelper;

  @Mock
  private Context context;

  @Mock
  private Request request;

  @Mock
  private Response contextResponse;

  @Before
  public void setup() throws Exception {
    underTest = new RoutingRuleHandler(routingRuleHelper);

    when(request.getPath()).thenReturn(SOME_PATH);
    when(context.getRequest()).thenReturn(request);
    when(request.getParameters()).thenReturn(new Parameters());
    when(context.proceed()).thenReturn(contextResponse);
  }

  @Test
  public void testHandle_allowed() throws Exception {
    when(routingRuleHelper.isAllowed(any(Repository.class), eq(SOME_PATH))).thenReturn(true);

    Response response = underTest.handle(context);
    assertThat(response, is(contextResponse));
    verify(context).proceed();
  }

  @Test
  public void testHandle_blocked() throws Exception {
    when(routingRuleHelper.isAllowed(any(Repository.class), eq(SOME_PATH))).thenReturn(false);

    Response response = underTest.handle(context);
    assertNotNull(response);
    assertThat(response.getStatus().getCode(), is(403));
    verify(context, times(0)).proceed();
  }

  @Test
  public void testHandle_parameters() throws Exception {
    ListMultimap<String, String> params = LinkedListMultimap.create();
    params.put("foo", "bar");
    params.put("bar", "foo");
    when(request.getParameters()).thenReturn(new Parameters(params));
    when(routingRuleHelper.isAllowed(any(Repository.class), eq("/some/path?foo=bar&bar=foo"))).thenReturn(true);

    Response response = underTest.handle(context);
    assertThat(response, is(contextResponse));
    verify(context).proceed();
    verify(routingRuleHelper).isAllowed(any(Repository.class), eq("/some/path?foo=bar&bar=foo"));
  }
}
