package org.sonatype.nexus.repository.cache;

import java.util.Date;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.common.collect.NestedAttributesMap;
import org.sonatype.nexus.repository.storage.Asset;

import com.google.common.collect.Maps;
import org.joda.time.DateTime;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.sonatype.nexus.repository.cache.CacheInfo.INVALIDATED;
import static org.sonatype.nexus.repository.storage.MetadataNodeEntityAdapter.P_ATTRIBUTES;

/**
 * Tests {@link CacheInfo}.
 */
public class CacheInfoTest
    extends TestSupport
{
  @Test
  public void nothingToExtract() {
    NestedAttributesMap attributes = new NestedAttributesMap(P_ATTRIBUTES, Maps.<String, Object>newHashMap());
    Asset asset = mock(Asset.class);
    when(asset.attributes()).thenReturn(attributes);
    CacheInfo cacheInfo = CacheInfo.extractFromAsset(asset);
    assertThat(cacheInfo, nullValue());
  }

  @Test
  public void lastVerifiedOnlyExtract() {
    final DateTime now = DateTime.now();
    NestedAttributesMap attributes = new NestedAttributesMap(P_ATTRIBUTES, Maps.<String, Object>newHashMap());
    attributes.child(CacheInfo.CACHE).set(CacheInfo.LAST_VERIFIED, now.toDate());
    Asset asset = mock(Asset.class);
    when(asset.attributes()).thenReturn(attributes);
    CacheInfo cacheInfo = CacheInfo.extractFromAsset(asset);
    assertThat(cacheInfo, notNullValue());
    assertThat(cacheInfo.getLastVerified(), equalTo(now));
  }

  @Test
  public void cacheTokenOnlyExtract() {
    final String cacheToken = "foo-bar";
    NestedAttributesMap attributes = new NestedAttributesMap(P_ATTRIBUTES, Maps.<String, Object>newHashMap());
    attributes.child(CacheInfo.CACHE).set(CacheInfo.CACHE_TOKEN, cacheToken);
    Asset asset = mock(Asset.class);
    when(asset.attributes()).thenReturn(attributes);
    CacheInfo cacheInfo = CacheInfo.extractFromAsset(asset);
    assertThat(cacheInfo, nullValue());
  }

  @Test
  public void lastVerifiedAndCacheTokenExtract() {
    final DateTime now = DateTime.now();
    final String cacheToken = "foo-bar";
    NestedAttributesMap attributes = new NestedAttributesMap(P_ATTRIBUTES, Maps.<String, Object>newHashMap());
    attributes.child(CacheInfo.CACHE).set(CacheInfo.LAST_VERIFIED, now.toDate());
    attributes.child(CacheInfo.CACHE).set(CacheInfo.CACHE_TOKEN, cacheToken);
    Asset asset = mock(Asset.class);
    when(asset.attributes()).thenReturn(attributes);
    CacheInfo cacheInfo = CacheInfo.extractFromAsset(asset);
    assertThat(cacheInfo, notNullValue());
    assertThat(cacheInfo.getLastVerified(), equalTo(now));
    assertThat(cacheInfo.getCacheToken(), equalTo(cacheToken));
  }

  @Test
  public void lastVerifiedAndCacheTokenApply() {
    final DateTime now = DateTime.now();
    final String cacheToken = "foo-bar";
    NestedAttributesMap attributes = new NestedAttributesMap(P_ATTRIBUTES, Maps.<String, Object>newHashMap());
    Asset asset = mock(Asset.class);
    when(asset.attributes()).thenReturn(attributes);
    CacheInfo cacheInfo = new CacheInfo(now, cacheToken);
    CacheInfo.applyToAsset(asset, cacheInfo);
    assertThat(asset.attributes().child(CacheInfo.CACHE).get(CacheInfo.LAST_VERIFIED, Date.class),
        equalTo(now.toDate()));
    assertThat(asset.attributes().child(CacheInfo.CACHE).get(CacheInfo.CACHE_TOKEN, String.class),
        equalTo(cacheToken));
  }

  @Test
  public void invalidateAsset() {
    Asset asset = mockAsset(DateTime.now(), "foo-bar");

    assertThat(CacheInfo.invalidateAsset(asset), equalTo(true));
    assertThat(asset.attributes().child(CacheInfo.CACHE).get(CacheInfo.CACHE_TOKEN, String.class),
        equalTo(INVALIDATED));
    assertThat(CacheInfo.invalidateAsset(asset), equalTo(false)); // already invalidated
    assertThat(asset.attributes().child(CacheInfo.CACHE).get(CacheInfo.CACHE_TOKEN, String.class),
        equalTo(INVALIDATED));
  }

  private Asset mockAsset(DateTime lastVerified, String cacheToken) {
    NestedAttributesMap attributes = new NestedAttributesMap(P_ATTRIBUTES, Maps.<String, Object>newHashMap());
    if (lastVerified != null) {
      attributes.child(CacheInfo.CACHE).set(CacheInfo.LAST_VERIFIED, lastVerified.toDate());
    }
    if (cacheToken != null) {
      attributes.child(CacheInfo.CACHE).set(CacheInfo.CACHE_TOKEN, cacheToken);
    }
    Asset asset = mock(Asset.class);
    when(asset.attributes()).thenReturn(attributes);

    return asset;
  }
}
