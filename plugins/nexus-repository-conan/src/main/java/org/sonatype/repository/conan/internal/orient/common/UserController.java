package org.sonatype.repository.conan.internal.orient.common;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.http.PartialFetchHandler;
import org.sonatype.nexus.repository.security.SecurityHandler;
import org.sonatype.nexus.repository.storage.UnitOfWorkHandler;
import org.sonatype.nexus.repository.view.Handler;
import org.sonatype.nexus.repository.view.Route.Builder;
import org.sonatype.nexus.repository.view.Router;
import org.sonatype.nexus.repository.view.handlers.ConditionalRequestHandler;
import org.sonatype.nexus.repository.view.handlers.ContentHeadersHandler;
import org.sonatype.nexus.repository.view.handlers.ExceptionHandler;
import org.sonatype.nexus.repository.view.handlers.HandlerContributor;
import org.sonatype.nexus.repository.view.handlers.TimingHandler;
import org.sonatype.nexus.repository.view.matchers.ActionMatcher;
import org.sonatype.nexus.repository.view.matchers.token.TokenMatcher;
import org.sonatype.nexus.repository.view.matchers.token.TokenMatcher.State;
import org.sonatype.repository.conan.internal.security.token.ConanTokenFacet;

import static java.lang.String.format;
import static org.sonatype.nexus.repository.http.HttpMethods.GET;
import static org.sonatype.nexus.repository.view.matchers.logic.LogicMatchers.and;

/**
 * @since 3.28
 */
@Named
@Singleton
public class UserController
    extends ComponentSupport
{
  private static final String CHECK_CREDENTIALS_URL = "/%s/users/check_credentials";

  private static final String AUTHENTICATE_URL = "/%s/users/authenticate";

  @Inject
  private TimingHandler timingHandler;

  @Inject
  private SecurityHandler securityHandler;

  @Inject
  private ExceptionHandler exceptionHandler;

  @Inject
  private HandlerContributor handlerContributor;

  @Inject
  private ConditionalRequestHandler conditionalRequestHandler;

  @Inject
  private PartialFetchHandler partialFetchHandler;

  @Inject
  private ContentHeadersHandler contentHeadersHandler;

  @Inject
  private UnitOfWorkHandler unitOfWorkHandler;

  /**
   * Matches on authentication endpoint
   */
  private static Builder checkCredentials(final String version) {
    return new Builder().matcher(
        and(
            new ActionMatcher(GET),
            new TokenMatcher(format(CHECK_CREDENTIALS_URL, version))
        )
    );
  }

  /**
   * Matches on credential checking endpoint
   */
  private static Builder authenticate(final String version) {
    return new Builder().matcher(
        and(
            new ActionMatcher(GET),
            new TokenMatcher(format(AUTHENTICATE_URL, version))
        )
    );
  }

  /**
   * Checks if there is a Bearer Authentication: token
   * otherwise returns 401
   */
  final Handler checkCredentials = context -> {
    State state = context.getAttributes().require(TokenMatcher.State.class);
    Repository repository = context.getRepository();
    log.debug("[checkCredentials] repository: {} tokens: {}", repository.getName(), state.getTokens());

    return repository.facet(ConanTokenFacet.class).user(context);
  };

  /**
   * Authenticates the endpoint, generates a response containing the token to be used by the client
   */
  final Handler authenticate = context -> {
    State state = context.getAttributes().require(TokenMatcher.State.class);
    Repository repository = context.getRepository();
    log.debug("[authenticate] repository: {} tokens: {}", repository.getName(), state.getTokens());

    return repository.facet(ConanTokenFacet.class).login(context);
  };

  public void attach(final Router.Builder builder, final String version) {
      builder.route(checkCredentials(version)
        .handler(timingHandler)
        .handler(securityHandler)
        .handler(exceptionHandler)
        .handler(handlerContributor)
        .handler(conditionalRequestHandler)
        .handler(partialFetchHandler)
        .handler(contentHeadersHandler)
        .handler(unitOfWorkHandler)
        .handler(checkCredentials)
        .create());

    builder.route(authenticate(version)
        .handler(timingHandler)
        .handler(securityHandler)
        .handler(exceptionHandler)
        .handler(handlerContributor)
        .handler(conditionalRequestHandler)
        .handler(partialFetchHandler)
        .handler(contentHeadersHandler)
        .handler(unitOfWorkHandler)
        .handler(authenticate)
        .create());
  }
}
