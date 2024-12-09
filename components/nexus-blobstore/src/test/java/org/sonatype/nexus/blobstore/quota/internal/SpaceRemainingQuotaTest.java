package org.sonatype.nexus.blobstore.quota.internal;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.blobstore.api.BlobStore;
import org.sonatype.nexus.blobstore.api.BlobStoreConfiguration;
import org.sonatype.nexus.blobstore.api.BlobStoreMetrics;
import org.sonatype.nexus.common.collect.NestedAttributesMap;
import org.sonatype.nexus.rest.ValidationErrorsException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.sonatype.nexus.blobstore.quota.BlobStoreQuotaSupport.LIMIT_KEY;
import static org.sonatype.nexus.blobstore.quota.BlobStoreQuotaSupport.ROOT_KEY;

public class SpaceRemainingQuotaTest
    extends TestSupport
{
  SpaceRemainingQuota quota;

  @Mock
  BlobStore blobStore;

  @Mock
  BlobStoreMetrics metrics;

  @Mock
  BlobStoreConfiguration config;

  @Mock
  NestedAttributesMap attributesMap;

  @Before
  public void setup() {
    when(blobStore.getMetrics()).thenReturn(metrics);
    when(blobStore.getBlobStoreConfiguration()).thenReturn(config);
    when(config.getName()).thenReturn("test");
    when(config.attributes(ROOT_KEY)).thenReturn(attributesMap);
    when(attributesMap.get(eq(LIMIT_KEY), eq(Number.class))).thenReturn(10L);

    quota = new SpaceRemainingQuota();
  }

  @Test
  public void sufficientSpaceRemaining() {
    when(metrics.isUnlimited()).thenReturn(false);
    when(metrics.getAvailableSpace()).thenReturn(20L);

    assertFalse(quota.check(blobStore).isViolation());
  }

  @Test
  public void insufficientSpaceRemaining() {
    when(metrics.isUnlimited()).thenReturn(false);
    when(metrics.getAvailableSpace()).thenReturn(5L);

    assertTrue(quota.check(blobStore).isViolation());
  }

  @Test
  public void unlimitedSpaceRemaining() {
    when(metrics.isUnlimited()).thenReturn(true);
    when(metrics.getAvailableSpace()).thenReturn(5L);

    assertFalse(quota.check(blobStore).isViolation());
  }

  @Test
  public void greaterThanZeroLimitIsValid() {
    when(attributesMap.get(eq(LIMIT_KEY), eq(Number.class))).thenReturn(10L);
    quota.validateConfig(config);
  }

  @Test(expected = ValidationErrorsException.class)
  public void zeroLimitIsInvalid() {
    when(attributesMap.get(eq(LIMIT_KEY), eq(Number.class))).thenReturn(0);
    quota.validateConfig(config);
  }

  @Test(expected = IllegalArgumentException.class)
  public void noLimitIsInvalid() {
    when(attributesMap.get(eq(LIMIT_KEY), eq(Number.class))).thenReturn(null);
    quota.validateConfig(config);
  }
}
