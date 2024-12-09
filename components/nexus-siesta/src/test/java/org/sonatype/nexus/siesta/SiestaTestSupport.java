package org.sonatype.nexus.siesta;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.sonatype.goodies.testsupport.TestSupport;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceFilter;
import com.google.inject.servlet.GuiceServletContextListener;
import org.eclipse.jetty.servlet.ServletTester;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * Support for Siesta tests.
 */
public class SiestaTestSupport
    extends TestSupport
{
  private ServletTester servletTester;

  private String url;

  private Client client;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void startJetty() throws Exception {
    servletTester = new ServletTester();
    servletTester.getContext().addEventListener(new GuiceServletContextListener()
    {
      final Injector injector = Guice.createInjector(new TestModule());

      @Override
      protected Injector getInjector() {
        return injector;
      }
    });

    url = servletTester.createConnector(true) + TestModule.MOUNT_POINT;
    servletTester.addFilter(GuiceFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));
    servletTester.addServlet(DummyServlet.class, "/*");
    servletTester.start();

    client = ClientBuilder.newClient();
  }

  @After
  public void stopJetty() throws Exception {
    if (servletTester != null) {
      servletTester.stop();
    }
  }

  protected Client client() {
    return client;
  }

  protected String url() {
    return url;
  }

  protected String url(final String path) {
    return url + "/" + path;
  }
}
