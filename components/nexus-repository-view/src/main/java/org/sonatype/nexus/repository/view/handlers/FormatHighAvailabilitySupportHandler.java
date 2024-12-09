package org.sonatype.nexus.repository.view.handlers;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Handler;
import org.sonatype.nexus.repository.view.Response;

import static java.lang.String.format;
import static org.sonatype.nexus.repository.http.HttpResponses.badRequest;

/**
 * Returns the message for client in case format doesn't support High Availability infrastructure.
 *
 * @since 3.17
 */
@Named
@Singleton
public class FormatHighAvailabilitySupportHandler
    implements Handler
{
  private HighAvailabilitySupportChecker highAvailabilitySupportChecker;

  @Inject
  public FormatHighAvailabilitySupportHandler(final HighAvailabilitySupportChecker highAvailabilitySupportChecker) {
    this.highAvailabilitySupportChecker = highAvailabilitySupportChecker;
  }

  @Nonnull
  @Override
  public Response handle(@Nonnull final Context context) throws Exception {
    String format = context.getRepository().getFormat().getValue();
    if (!highAvailabilitySupportChecker.isSupported(format)) {
      return badRequest(format("%s is not supported in clustered mode.", format));
    }

    return context.proceed();
  }
}
