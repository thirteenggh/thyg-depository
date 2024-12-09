package org.sonatype.nexus.repository.security;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Response;

import com.google.common.annotations.VisibleForTesting;

/**
 * Security handler.
 *
 * @since 3.0
 */
@Named
@Singleton
public class SecurityHandler
    extends ComponentSupport
    implements org.sonatype.nexus.repository.view.handlers.SecurityHandler
{
  @VisibleForTesting
  static final String AUTHORIZED_KEY = "security.authorized";

  @Nonnull
  @Override
  public Response handle(@Nonnull final Context context) throws Exception {
    SecurityFacet securityFacet = context.getRepository().facet(SecurityFacet.class);

    //we employ the model that one security check per request is all that is necessary, if this handler is in a nested
    //repository (because this is a group repository), there is no need to check authz again
    if (context.getAttributes().get(AUTHORIZED_KEY) == null) {
      securityFacet.ensurePermitted(context.getRequest());
      context.getAttributes().set(AUTHORIZED_KEY, true);
    }

    return context.proceed();
  }
}
