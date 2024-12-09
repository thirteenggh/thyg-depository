package org.sonatype.nexus.repository.r.internal;

import org.sonatype.goodies.testsupport.TestSupport;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.sonatype.nexus.repository.cache.CacheControllerHolder.CONTENT;
import static org.sonatype.nexus.repository.cache.CacheControllerHolder.METADATA;

public class AssetKindTest
    extends TestSupport
{
  @Test
  public void cacheTypes() throws Exception {
    assertThat(AssetKind.ARCHIVE.getCacheType(), is(equalTo(CONTENT)));
    assertThat(AssetKind.RDS_METADATA.getCacheType(), is(equalTo(METADATA)));
    assertThat(AssetKind.PACKAGES.getCacheType(), is(equalTo(METADATA)));
  }
}
