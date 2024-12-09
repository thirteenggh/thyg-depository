package org.sonatype.nexus.repository.npm.internal;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Nonnull;

import org.sonatype.nexus.repository.IllegalOperationException;
import org.sonatype.nexus.repository.InvalidContentException;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.npm.internal.search.legacy.NpmSearchIndexFacet;
import org.sonatype.nexus.repository.npm.internal.search.v1.NpmSearchFacet;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Handler;
import org.sonatype.nexus.repository.view.Parameters;
import org.sonatype.nexus.repository.view.Response;
import org.sonatype.nexus.repository.view.matchers.token.TokenMatcher;
import org.sonatype.nexus.repository.view.matchers.token.TokenMatcher.State;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.sonatype.nexus.repository.http.HttpMethods.PUT;

/**
 * npm protocol handlers.
 *
 * @since 3.0
 */
public final class NpmHandlers
{
  private NpmHandlers() {
    // nop
  }

  private static final Logger log = LoggerFactory.getLogger(NpmHandlers.class);

  public static Handler npmErrorHandler = new Handler()
  {
    @Nonnull
    @Override
    public Response handle(@Nonnull final Context context) throws Exception {
      try {
        return context.proceed();
      }
      catch (IllegalOperationException | IllegalArgumentException e) {
        Response error = NpmResponses.badRequest(e.getMessage());
        log.warn("Error: {} {}: {} - {}",
            context.getRequest().getAction(),
            context.getRequest().getPath(),
            error.getStatus(),
            e.getMessage(),
            e);
        return error;
      }
      catch (InvalidContentException e) {
        Response error;
        if (PUT.equals(context.getRequest().getAction())) {
          error = NpmResponses.badRequest(e.getMessage());
        }
        else {
          error = NpmResponses.notFound(e.getMessage());
        }
        log.warn("Error: {} {}: {} - {}",
            context.getRequest().getAction(),
            context.getRequest().getPath(),
            error.getStatus(),
            e.getMessage(),
            e);
        return error;
      }
    }
  };

  public static Handler getPackage = new Handler()
  {
    @Nonnull
    @Override
    public Response handle(@Nonnull final Context context) throws Exception {
      State state = context.getAttributes().require(TokenMatcher.State.class);
      Repository repository = context.getRepository();
      log.debug("[getPackage] repository: {} tokens: {}", repository.getName(), state.getTokens());

      NpmPackageId packageId = NpmPaths.packageId(state);
      return repository.facet(NpmHostedFacet.class)
          .getPackage(packageId)
          .map(NpmResponses::ok)
          .orElseGet(() -> NpmResponses.packageNotFound(packageId));
    }
  };

  public static Handler putPackage = new Handler()
  {
    @Nonnull
    @Override
    public Response handle(@Nonnull final Context context) throws Exception {
      State state = context.getAttributes().require(TokenMatcher.State.class);
      Repository repository = context.getRepository();
      log.debug("[putPackage] repository: {} tokens: {}", repository.getName(), state.getTokens());

      repository.facet(NpmHostedFacet.class)
          .putPackage(NpmPaths.packageId(state), NpmPaths.revision(state), context.getRequest().getPayload());
      return NpmResponses.ok();
    }
  };

  public static Handler deletePackage = new Handler()
  {
    @Nonnull
    @Override
    public Response handle(@Nonnull final Context context) throws Exception {
      State state = context.getAttributes().require(TokenMatcher.State.class);
      Repository repository = context.getRepository();
      log.debug("[deletePackage] repository: {} tokens: {}", repository.getName(), state.getTokens());

      NpmPackageId packageId = NpmPaths.packageId(state);
      Set<String> deleted = repository.facet(NpmHostedFacet.class).deletePackage(packageId, NpmPaths.revision(state));
      if (!deleted.isEmpty()) {
        return NpmResponses.ok();
      }
      else {
        return NpmResponses.packageNotFound(packageId);
      }
    }
  };

  public static Handler getTarball = new Handler()
  {
    @Nonnull
    @Override
    public Response handle(@Nonnull final Context context) throws Exception {
      State state = context.getAttributes().require(TokenMatcher.State.class);
      Repository repository = context.getRepository();
      log.debug("[getTarball] repository: {} tokens: {}", repository.getName(), state.getTokens());

      NpmPackageId packageId = NpmPaths.packageId(state);
      String tarballName = NpmPaths.tarballName(state);
      return repository.facet(NpmHostedFacet.class)
          .getTarball(packageId, tarballName)
          .map(NpmResponses::ok)
          .orElseGet(() -> NpmResponses.packageNotFound(packageId));
    }
  };

  public static Handler deleteTarball = new Handler()
  {
    @Nonnull
    @Override
    public Response handle(@Nonnull final Context context) throws Exception {
      State state = context.getAttributes().require(TokenMatcher.State.class);
      Repository repository = context.getRepository();
      log.debug("[deleteTarball] repository: {} tokens: {}", repository.getName(), state.getTokens());

      NpmPackageId packageId = NpmPaths.packageId(state);
      String tarballName = NpmPaths.tarballName(state);
      Optional<String> deleted = repository.facet(NpmHostedFacet.class).deleteTarball(packageId, tarballName);
      if (deleted.isPresent()) {
        return NpmResponses.ok();
      }
      else {
        return NpmResponses.tarballNotFound(packageId, tarballName);
      }
    }
  };

  /**
   * @deprecated No longer actively used by npm upstream, replaced by v1 search api (NEXUS-13150).
   */
  @Deprecated
  public static Handler searchIndex = new Handler()
  {
    @Nonnull
    @Override
    public Response handle(@Nonnull final Context context) throws Exception {
      Repository repository = context.getRepository();
      Parameters parameters = context.getRequest().getParameters();
      log.debug("[searchIndex] repository: {} parameters: {}", repository.getName(), parameters);

      return NpmResponses.ok(repository.facet(NpmSearchIndexFacet.class).searchIndex(NpmPaths.indexSince(parameters)));
    }
  };

  public static Handler searchV1 = new Handler()
  {
    @Nonnull
    @Override
    public Response handle(@Nonnull final Context context) throws Exception {
      Repository repository = context.getRepository();
      Parameters parameters = context.getRequest().getParameters();
      log.debug("[searchV1] repository: {} parameters: {}", repository.getName(), parameters);

      return NpmResponses.ok(repository.facet(NpmSearchFacet.class).searchV1(parameters));
    }
  };

  public static Handler createToken = new Handler()
  {
    @Nonnull
    @Override
    public Response handle(@Nonnull final Context context) throws Exception {
      State state = context.getAttributes().require(TokenMatcher.State.class);
      Repository repository = context.getRepository();
      log.debug("[createToken] repository: {} tokens: {}", repository.getName(), state.getTokens());

      return repository.facet(NpmTokenFacet.class).login(context);
    }
  };

  public static Handler deleteToken = new Handler()
  {
    @Nonnull
    @Override
    public Response handle(@Nonnull final Context context) throws Exception {
      State state = context.getAttributes().require(TokenMatcher.State.class);
      Repository repository = context.getRepository();
      log.debug("[deleteToken] repository: {} tokens: {}", repository.getName(), state.getTokens());

      return repository.facet(NpmTokenFacet.class).logout(context);
    }
  };

  public static Handler getDistTags = new Handler()
  {
    @Nonnull
    @Override
    public Response handle(@Nonnull final Context context) throws Exception {
      State state = context.getAttributes().require(TokenMatcher.State.class);
      Repository repository = context.getRepository();
      log.debug("[getPackage] repository: {} tokens: {}", repository.getName(), state.getTokens());

      NpmPackageId packageId = NpmPaths.packageId(state);
      return repository.facet(NpmHostedFacet.class)
          .getDistTags(packageId)
          .map(NpmResponses::ok)
          .orElseGet(() -> NpmResponses.packageNotFound(packageId));
    }
  };

  public static Handler putDistTags = new Handler()
  {
    @Nonnull
    @Override
    public Response handle(@Nonnull final Context context) throws Exception {
      State state = context.getAttributes().require(TokenMatcher.State.class);
      Repository repository = context.getRepository();
      log.debug("[putDistTags] repository: {} tokens: {}", repository.getName(), state.getTokens());

      try {
        repository.facet(NpmHostedFacet.class)
            .putDistTags(NpmPaths.packageId(state), state.getTokens().get(NpmPaths.T_PACKAGE_TAG), context.getRequest().getPayload());
        return NpmResponses.ok();
      }
      catch (IOException e) { //NOSONAR
        return NpmResponses.badRequest(e.getMessage());
      }
    }
  };

  public static Handler deleteDistTags = new Handler()
  {
    @Nonnull
    @Override
    public Response handle(@Nonnull final Context context) throws Exception {
      State state = context.getAttributes().require(TokenMatcher.State.class);
      Repository repository = context.getRepository();
      log.debug("[putDistTags] repository: {} tokens: {}", repository.getName(), state.getTokens());

      try {
        repository.facet(NpmHostedFacet.class)
            .deleteDistTags(NpmPaths.packageId(state), state.getTokens().get(NpmPaths.T_PACKAGE_TAG), context.getRequest().getPayload());
        return NpmResponses.ok();
      }
      catch (IOException e) { //NOSONAR
        return NpmResponses.badRequest(e.getMessage());
      }
    }
  };
}
