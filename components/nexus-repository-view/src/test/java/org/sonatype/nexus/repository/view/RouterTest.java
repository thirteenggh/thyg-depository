package org.sonatype.nexus.repository.view;

import java.util.Collections;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.repository.Repository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.sonatype.nexus.repository.view.Router.LOCAL_ATTRIBUTE_PREFIX;

public class RouterTest
    extends TestSupport
{
  private Router underTest;

  @Mock
  Repository repository;

  @Mock
  Request request;

  @Mock
  Route route;

  @Mock
  DefaultRoute defaultRoute;


  @Before
  public void setup() throws Exception {
    underTest = new Router(Collections.singletonList(route), defaultRoute);
  }

  @Test
  public void testMaybeCopyContextAttributes() throws Exception {
    Context existingContext = new Context(repository, request);
    existingContext.getAttributes().set("somekey", "somevalue");
    existingContext.getAttributes().set(LOCAL_ATTRIBUTE_PREFIX + "anotherkey", "anothervalue");
    Context newContext = underTest.maybeCopyContextAttributes(repository, request, existingContext);
    assertThat(newContext.getAttributes().get("somekey"), is("somevalue"));
    assertThat(newContext.getAttributes().get(LOCAL_ATTRIBUTE_PREFIX + "anotherkey"), nullValue());
  }
}
