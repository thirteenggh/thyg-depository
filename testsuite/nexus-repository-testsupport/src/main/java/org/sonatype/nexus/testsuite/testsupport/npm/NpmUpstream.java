package org.sonatype.nexus.testsuite.testsupport.npm;

import org.sonatype.goodies.httpfixture.server.fluent.Server;
import org.sonatype.goodies.lifecycle.LifecycleSupport;

import static org.sonatype.goodies.httpfixture.server.jetty.behaviour.Content.content;

public class NpmUpstream
    extends LifecycleSupport
{
  private Server remote;

  public NpmUpstream() {
  }

  @Override
  public void doStart() throws Exception {
    remote = Server.withPort(0).start();
  }

  @Override
  public void doStop() throws Exception {
    if (remote != null) {
      remote.stop();
    }
  }

  public String repositoryUrl() {
    ensureStarted();
    return "http://localhost:" + remote.getPort() + "/";
  }

  public void registerPackageRoot(final String proxyPackageRoot, final byte[] bytes) {
    remote.serve(proxyPackageRoot).withBehaviours(content(bytes));
  }
}
