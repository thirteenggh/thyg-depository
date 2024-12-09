package org.sonatype.nexus.repository.r.internal.hosted;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.repository.http.HttpResponses;
import org.sonatype.nexus.repository.r.RHostedFacet;
import org.sonatype.nexus.repository.view.Content;
import org.sonatype.nexus.repository.view.Handler;
import org.sonatype.nexus.rest.ValidationErrorsException;

import static org.sonatype.nexus.repository.r.internal.util.PackageValidator.validateArchiveUploadPath;
import static org.sonatype.nexus.repository.r.internal.util.RPathUtils.extractRequestPath;
import static org.sonatype.nexus.repository.r.internal.util.RPathUtils.getBasePath;

/**
 * R hosted handlers.
 * @since 3.28
 */
@Named
@Singleton
public final class HostedHandlers
    extends ComponentSupport
{
  /**
   * Handle request for packages.
   */
  final Handler getPackages = context -> {
    String path = extractRequestPath(context);
    RHostedFacet hostedFacet = context.getRepository().facet(RHostedFacet.class);

    Content content;
    if ((content = hostedFacet.getStoredContent(path)) != null
        || (content = hostedFacet.buildAndPutPackagesGz(getBasePath(path))) != null) {
      return HttpResponses.ok(content);
    }

    return HttpResponses.notFound();
  };

  /**
   * Handle request for archive.
   */
  final Handler getArchive = context -> {
    String path = extractRequestPath(context);
    Content content = context.getRepository().facet(RHostedFacet.class).getStoredContent(path);
    if (content != null) {
      return HttpResponses.ok(content);
    }
    return HttpResponses.notFound();
  };

  /**
   * Handle request for upload.
   */
  final Handler putArchive = context -> {
    String path = extractRequestPath(context);
    try {
      validateArchiveUploadPath(path);
    }
    catch (ValidationErrorsException e) {
      return HttpResponses.badRequest(e.getMessage());
    }
    context.getRepository().facet(RHostedFacet.class).upload(path, context.getRequest().getPayload());
    return HttpResponses.ok();
  };
}
