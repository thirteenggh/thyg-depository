package org.sonatype.nexus.repository.npm.internal;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.npm.internal.orient.OrientNpmGroupHandler;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Response;

import static org.sonatype.nexus.repository.npm.internal.NpmAuditFacet.QUICK_AUDIT_ATTR_NAME;

/**
 * Handle 'npm audit' cmd in the group repository.
 *
 * @since 3.24
 */
@Named
@Singleton
public class NpmGroupAuditHandler
    extends OrientNpmGroupHandler
{
  @Nonnull
  @Override
  public Response handle(@Nonnull final Context context) throws Exception
  {
    NpmAuditFacet npmAuditFacet = context.getRepository().facet(NpmAuditFacet.class);
    context.getAttributes().set(QUICK_AUDIT_ATTR_NAME, false);
    return NpmResponses.ok(npmAuditFacet.audit(context.getRequest().getPayload(), context));
  }
}
