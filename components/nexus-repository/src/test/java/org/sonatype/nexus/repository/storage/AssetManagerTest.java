package org.sonatype.nexus.repository.storage;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.repository.capability.GlobalRepositorySettings;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.joda.time.Duration.standardHours;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.sonatype.goodies.common.Time.hours;
import static org.sonatype.nexus.repository.storage.AssetManager.DEFAULT_LAST_DOWNLOADED_INTERVAL;

public class AssetManagerTest
    extends TestSupport
{
  @Mock
  private Asset asset;

  private AssetManager underTest;

  @Before
  public void setUp() throws Exception {
    underTest = new AssetManager(new GlobalRepositorySettings());
  }

  @Test
  public void lastDownloadedIntervalDefaultsToTwelveHours() {
    assertThat(underTest.getLastDownloadedInterval().getStandardSeconds(), is(hours(12).toSeconds()));
  }

  @Test
  public void failWhenAssetLastDownloadedIsNotUpdated() {
    underTest.setLastDownloadedInterval(standardHours(0));

    when(asset.markAsDownloaded(standardHours(0))).thenReturn(false);

    assertThat(underTest.maybeUpdateLastDownloaded(asset), is(false));

    verify(asset).markAsDownloaded(standardHours(0));
  }

  @Test
  public void successWhenAssetLastDownloadedIsUpdated() {
    when(asset.markAsDownloaded(DEFAULT_LAST_DOWNLOADED_INTERVAL)).thenReturn(true);

    assertThat(underTest.maybeUpdateLastDownloaded(asset), is(true));

    verify(asset).markAsDownloaded(DEFAULT_LAST_DOWNLOADED_INTERVAL);
  }
}
