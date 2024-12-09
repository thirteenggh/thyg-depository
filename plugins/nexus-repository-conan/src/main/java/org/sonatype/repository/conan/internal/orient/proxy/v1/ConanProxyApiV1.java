package org.sonatype.repository.conan.internal.orient.proxy.v1;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.view.Router;
import org.sonatype.repository.conan.internal.common.PingController;
import org.sonatype.repository.conan.internal.orient.common.UserController;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @since 3.28
 */
@Named
@Singleton
public class ConanProxyApiV1
{
  private static final String VERSION = "v1";

  private PingController pingController;

  private UserController userController;

  private ConanProxyControllerV1 conanProxyControllerV1;

  @Inject
  public ConanProxyApiV1(final PingController pingController,
                         final UserController userController,
                         final ConanProxyControllerV1 conanProxyControllerV1) {
    this.pingController = checkNotNull(pingController);
    this.userController = checkNotNull(userController);
    this.conanProxyControllerV1 = checkNotNull(conanProxyControllerV1);
  }

  public void create(final Router.Builder builder) {
    pingController.attach(builder, VERSION);
    userController.attach(builder, VERSION);
    conanProxyControllerV1.attach(builder);
  }
}
