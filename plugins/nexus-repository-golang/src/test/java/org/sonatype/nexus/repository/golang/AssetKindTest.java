package org.sonatype.nexus.repository.golang;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.repository.cache.CacheControllerHolder;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class AssetKindTest
    extends TestSupport
{
  private AssetKind GO_PACKAGE;

  private AssetKind GO_MODULE;

  private AssetKind GO_INFO;

  @Before
  public void setUp() throws Exception {
    GO_PACKAGE = AssetKind.PACKAGE;
    GO_MODULE = AssetKind.MODULE;
    GO_INFO = AssetKind.INFO;
  }

  @Test
  public void getCacheType() {
    assertThat(GO_PACKAGE.getCacheType(), is(equalTo(CacheControllerHolder.CONTENT)));
    assertThat(GO_MODULE.getCacheType(), is(equalTo(CacheControllerHolder.METADATA)));
    assertThat(GO_INFO.getCacheType(), is(equalTo(CacheControllerHolder.METADATA)));
  }
}
