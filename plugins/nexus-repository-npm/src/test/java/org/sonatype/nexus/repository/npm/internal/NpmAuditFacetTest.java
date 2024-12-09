package org.sonatype.nexus.repository.npm.internal;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.cache.CacheHelper;
import org.sonatype.nexus.common.event.EventManager;
import org.sonatype.nexus.repository.npm.internal.audit.report.ReportCreator;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Headers;
import org.sonatype.nexus.repository.view.Request;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.powermock.api.mockito.PowerMockito.when;


public class NpmAuditFacetTest
    extends TestSupport
{
  private NpmAuditFacet underTest;

  @Mock
  private Context context;

  @Mock
  private Request request;

  private Headers headers;

  @Mock
  private EventManager eventManager;

  @Mock
  private ReportCreator reportCreator;

  @Mock
  private CacheHelper cacheHelper;

  @Before
  public void setUp() throws Exception {

    underTest = new NpmAuditFacet(1, 1, 12, eventManager, reportCreator, cacheHelper);
  }

  @Test
  public void whenNoHeadersAppIdNotFound() {
    headers = new Headers();
    when(context.getRequest()).thenReturn(request);
    when(request.getHeaders()).thenReturn(headers);

    final String actual = underTest.maybeGetAppIdFromHeader(context);

    assertThat(actual, is(nullValue()));
  }

  @Test
  public void whenAppIdIsPresentInValue() {
    final ListMultimap multimap = ArrayListMultimap.create();
    multimap.put("0", "app_id: bar");
    headers = new Headers(multimap);
    when(context.getRequest()).thenReturn(request);
    when(request.getHeaders()).thenReturn(headers);

    final String actual = underTest.maybeGetAppIdFromHeader(context);

    assertThat(actual, is("bar"));
  }

  @Test
  public void whenAppIdIsPresentInValueWithNoSpace() {
    final ListMultimap multimap = ArrayListMultimap.create();
    multimap.put("0", "app_id:bar");
    headers = new Headers(multimap);
    when(context.getRequest()).thenReturn(request);
    when(request.getHeaders()).thenReturn(headers);

    final String actual = underTest.maybeGetAppIdFromHeader(context);

    assertThat(actual, is("bar"));
  }

  @Test
  public void whenAppIdIsNotPresent() {
    final ListMultimap multimap = ArrayListMultimap.create();
    multimap.put("0", "appid: bar");
    headers = new Headers(multimap);
    when(context.getRequest()).thenReturn(request);
    when(request.getHeaders()).thenReturn(headers);

    final String actual = underTest.maybeGetAppIdFromHeader(context);

    assertThat(actual, is(nullValue()));
  }

  @Test
  public void whenAppIdIsPresentInList() {
    final ListMultimap multimap = ArrayListMultimap.create();
    multimap.put("Content-Type", "application/json");
    multimap.put("0", "app_id: baz");
    multimap.put("hello", "world");
    headers = new Headers(multimap);
    when(context.getRequest()).thenReturn(request);
    when(request.getHeaders()).thenReturn(headers);

    final String actual = underTest.maybeGetAppIdFromHeader(context);

    assertThat(actual, is("baz"));
  }
}
