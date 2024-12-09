package org.sonatype.nexus.repository.maven.internal;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.repository.http.HttpResponses;
import org.sonatype.nexus.repository.maven.MavenFacet;
import org.sonatype.nexus.repository.maven.MavenPath;
import org.sonatype.nexus.repository.maven.MavenPath.Coordinates;
import org.sonatype.nexus.repository.maven.VersionPolicy;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Handler;
import org.sonatype.nexus.repository.view.Response;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.repository.http.HttpMethods.GET;
import static org.sonatype.nexus.repository.http.HttpMethods.HEAD;

/**
 * Enforces the repository's version policy on all requests.
 *
 * @since 3.25
 */
@Named
public class VersionPolicyHandler
    extends ComponentSupport
    implements Handler
{
  private final VersionPolicyValidator versionPolicyValidator;

  @Inject
  public VersionPolicyHandler(final VersionPolicyValidator versionPolicyValidator) {
    this.versionPolicyValidator = checkNotNull(versionPolicyValidator);
  }

  @Nonnull
  @Override
  public Response handle(@Nonnull final Context context) throws Exception {
    final MavenPath path = context.getAttributes().require(MavenPath.class);
    final MavenFacet mavenFacet = context.getRepository().facet(MavenFacet.class);
    final VersionPolicy versionPolicy = mavenFacet.getVersionPolicy();
    final Coordinates coordinates = path.getCoordinates();
    if (coordinates != null && !versionPolicyValidator.validArtifactPath(versionPolicy, coordinates)) {
      return createResponse(context,
          "Repository version policy: " + versionPolicy + " does not allow version: " + coordinates.getVersion());
    }
    if (!versionPolicyValidator.validMetadataPath(versionPolicy, path.main().getPath())) {
      return createResponse(context,
          "Repository version policy: " + versionPolicy + " does not allow metadata in path: " + path.getPath());
    }
    return context.proceed();
  }

  private static Response createResponse(final Context context, final String message) {
    switch (context.getRequest().getAction()) {
      case GET:
      case HEAD:
        return HttpResponses.notFound(message);
      default:
        return HttpResponses.badRequest(message);
    }
  }
}
