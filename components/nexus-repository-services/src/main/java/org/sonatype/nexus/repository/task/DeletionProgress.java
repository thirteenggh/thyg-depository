package org.sonatype.nexus.repository.task;

/**
 * Tracks deletion progress.
 *
 * @since 3.25
 */
public class DeletionProgress
{
  private long count = 0L;

  private boolean failed;

  private int attempts = 0;

  private int retryLimit = 0;

  public DeletionProgress() {
    // use defaults
  }

  public DeletionProgress(final int retryLimit) {
    this.retryLimit = retryLimit;
  }

  public long getCount() {
    return count;
  }

  public void addCount(final long count) {
    this.count += count;
  }

  public boolean isFailed() {
    return failed;
  }

  public void setFailed(final boolean completed) {
    this.failed = completed;
  }

  public int getAttempts() {
    return attempts;
  }

  public void setAttempts(final int attempts) {
    this.attempts = attempts;
  }

  public void update(final DeletionProgress progress) {
    failed = progress.isFailed();
    count += progress.getCount();
    if (progress.isFailed()) {
      attempts++;
    }
  }

  public boolean isFinished() {
    return !isFailed() || getAttempts() >= retryLimit;
  }
}
