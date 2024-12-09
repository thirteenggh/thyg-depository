package org.sonatype.nexus.internal.wonderland;

import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicBoolean;

import org.sonatype.goodies.testsupport.TestSupport;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link AuthTicketCache}.
 */
public class AuthTicketServiceImplTest
    extends TestSupport
{
  @Mock
  private AuthTicketGenerator authTicketGenerator;

  @Mock
  private AuthTicketCache authTicketCache;

  private AuthTicketServiceImpl underTest;

  @Before
  public void setup() {
    when(authTicketGenerator.generate()).thenReturn("ticket");
    when(authTicketCache.remove("user", "ticket")).thenReturn(true);

    underTest = new AuthTicketServiceImpl(authTicketGenerator, authTicketCache);
  }

  @Test
  public void testRedeemTicket_sameUser() {
    String ticket = underTest.createTicket("user");
    assertThat(underTest.redeemTicket("user", ticket), is(true));
  }

  @Test
  public void testRedeemTicket_differentUser() {
    String ticket = underTest.createTicket("user");
    assertThat(underTest.redeemTicket("bad", ticket), is(false));
  }
}
