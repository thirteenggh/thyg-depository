package org.apache.shiro.nexus;

import java.util.concurrent.TimeUnit;

import javax.cache.configuration.Factory;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.expiry.EternalExpiryPolicy;
import javax.cache.expiry.ExpiryPolicy;

import org.sonatype.goodies.common.Time;
import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.cache.CacheHelper;

import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Tests {@link ShiroJCacheManagerAdapter}
 */
public class ShiroJCacheManagerAdapterTest
    extends TestSupport
{

  @Mock
  private CacheHelper cacheHelper;

  @Captor
  private ArgumentCaptor<Factory<ExpiryPolicy>> confCaptor;

  private ShiroJCacheManagerAdapter underTest;

  @Before
  public void setUp() {
    underTest = new ShiroJCacheManagerAdapter(() -> cacheHelper, () -> Time.minutes(2L));
  }

  @Test
  public void defaultCacheConfigurationTest() throws Exception {
    when(cacheHelper.maybeCreateCache(anyString(), confCaptor.capture())).thenReturn(null);
    underTest.maybeCreateCache("foo");
    assertThat(confCaptor.getValue(), is(CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.MINUTES, 2L))));
  }

  @Test
  public void defaultShiroActiveSessionCacheConfigurationTest() throws Exception {
    when(cacheHelper.maybeCreateCache(anyString(), confCaptor.capture())).thenReturn(null);
    underTest.maybeCreateCache(CachingSessionDAO.ACTIVE_SESSION_CACHE_NAME);
    assertThat(confCaptor.getValue(), is(EternalExpiryPolicy.factoryOf()));
  }

}
