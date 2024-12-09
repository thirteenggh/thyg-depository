package org.sonatype.nexus.internal.wonderland;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.common.wonderland.AuthTicketService;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Default {@link AuthTicketService} implementation.
 *
 * @since 2.7
 */
@Named
@Singleton
public class AuthTicketServiceImpl
    extends ComponentSupport
    implements AuthTicketService
{
  private final AuthTicketGenerator authTicketGenerator;

  private final AuthTicketCache authTicketCache;

  @Inject
  public AuthTicketServiceImpl(final AuthTicketGenerator authTicketGenerator,
                               final AuthTicketCache authTicketCache)
  {
    this.authTicketGenerator = checkNotNull(authTicketGenerator);
    this.authTicketCache = checkNotNull(authTicketCache);
  }

  @Override
  public String createTicket(final String user) {
    String ticket = authTicketGenerator.generate();
    authTicketCache.add(user, ticket);
    return ticket;
  }

  @Override
  @Nullable
  public String createTicket() {
    Subject subject = SecurityUtils.getSubject();
    return subject != null ? createTicket(subject.getPrincipal().toString()) : null;
  }

  @Override
  public boolean redeemTicket(final String user, final String ticket) {
    checkNotNull(ticket);
    return authTicketCache.remove(user, ticket);
  }

  @Override
  public boolean redeemTicket(String ticket) {
    Subject subject = SecurityUtils.getSubject();

    return subject != null && redeemTicket(subject.getPrincipal().toString(), ticket);
  }
}
