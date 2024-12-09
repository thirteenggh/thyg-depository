package org.sonatype.nexus.repository.npm.internal;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.group.GroupHandler;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Response;
import org.sonatype.nexus.repository.view.Status;

import static org.sonatype.nexus.repository.npm.internal.NpmResponses.failureWithStatusPayload;
import static org.sonatype.nexus.repository.npm.internal.NpmResponses.forbidden;

/**
 * Override certain behaviours of the standard group handler to be able to write to a group.
 *
 * @since 3.28
 */
@Named
@Singleton
public class NpmGroupWriteHandler
    extends GroupHandler
{
  @Inject
  @Named("groupWriteHandler")
  Provider<GroupHandler> groupWriteHandler;

  @Nonnull
  @Override
  public Response handle(@Nonnull final Context context) throws Exception {
    if (groupWriteHandler.get() != null) {
      Response response = groupWriteHandler.get().handle(context);

      Status status = response.getStatus();
      if (status.isSuccessful()) {
        return response;
      }
      return failureWithStatusPayload(status.getCode(), status.getMessage());
    }
    return forbidden(INSUFFICIENT_LICENSE);
  }
}
