package org.sonatype.nexus.internal.wonderland;

import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicBoolean;

import org.sonatype.goodies.testsupport.TestSupport;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Tests for {@link AuthTicketCache}.
 */
public class AuthTicketCacheTest
    extends TestSupport
{
  @Test
  public void addRemoveIsRemoved() {
    AuthTicketCache tokens = new AuthTicketCache();
    tokens.add("user", "foo");
    assertThat(tokens.remove("user", "foo"), is(true));
  }

  @Test
  public void neverAddedRemove() {
    AuthTicketCache tokens = new AuthTicketCache();
    assertThat(tokens.remove("user","foo"), is(false));
  }

  @Test(expected = IllegalStateException.class)
  public void addDoesNotAllowDuplicates() {
    AuthTicketCache tokens = new AuthTicketCache();
    tokens.add("user","foo");
    // this should fail
    tokens.add("user","foo");
  }

  @Test
  public void testExpiredRemoveIsFalse() {
    final AtomicBoolean expired = new AtomicBoolean(false);
    AuthTicketCache tokens = new AuthTicketCache()
    {
      @Override
      protected boolean isTokenExpired(long now, Entry<UserAuthToken, Long> entry) {
        return expired.get();
      }
    };

    tokens.add("user", "foo");
    assertThat(tokens.remove("user", "foo"), is(true));

    tokens.add("user", "foo");
    // simulate expire
    expired.set(true);
    assertThat(tokens.remove("user", "foo"), is(false));
  }

  @Test
  public void testRemoveFailsIfDifferentUser() {
    AuthTicketCache cache = new AuthTicketCache();
    cache.add("user", "foo");
    assertThat(cache.remove("bad", "foo"), is(false));
  }
}
