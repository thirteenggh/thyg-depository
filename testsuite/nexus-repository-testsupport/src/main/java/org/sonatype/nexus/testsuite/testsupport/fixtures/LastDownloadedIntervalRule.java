package org.sonatype.nexus.testsuite.testsupport.fixtures;

import java.time.Duration;

import javax.inject.Provider;

import org.sonatype.nexus.repository.capability.GlobalRepositorySettings;

import org.junit.rules.ExternalResource;

public class LastDownloadedIntervalRule
    extends ExternalResource
{
  private final Provider<GlobalRepositorySettings> repositorySettings;

  private Duration lastDownloadedInterval;

  public LastDownloadedIntervalRule(final Provider<GlobalRepositorySettings> repositorySettings) {
    this.repositorySettings = repositorySettings;
  }

  @Override
  protected void before() {
    lastDownloadedInterval = repositorySettings.get().getLastDownloadedInterval();
  }

  @Override
  protected void after() {
    repositorySettings.get().setLastDownloadedInterval(lastDownloadedInterval);
  }

  public void setLastDownloadedInterval(final Duration duration) {
    repositorySettings.get().setLastDownloadedInterval(duration);
  }
}
