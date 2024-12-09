package org.sonatype.nexus.internal.orient;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.jmx.reflect.ManagedAttribute;
import org.sonatype.nexus.jmx.reflect.ManagedObject;
import org.sonatype.nexus.transaction.RetryController;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * JMX management of the {@link RetryController}.
 *
 * @since 3.16
 */
@Named
@Singleton
@ManagedObject
public class RetryControllerBean
{
  private final RetryController retryController;

  @Inject
  public RetryControllerBean(final RetryController retryController) {
    this.retryController = checkNotNull(retryController);
  }

  @ManagedAttribute
  public int getRetryLimit() {
    return retryController.getRetryLimit();
  }

  @ManagedAttribute
  public void setRetryLimit(final int retryLimit) {
    retryController.setRetryLimit(retryLimit);
  }

  @ManagedAttribute
  public int getMinSlots() {
    return retryController.getMinSlots();
  }

  @ManagedAttribute
  public void setMinSlots(final int minSlots) {
    retryController.setMinSlots(minSlots);
  }

  @ManagedAttribute
  public int getMaxSlots() {
    return retryController.getMaxSlots();
  }

  @ManagedAttribute
  public void setMaxSlots(final int maxSlots) {
    retryController.setMaxSlots(maxSlots);
  }

  @ManagedAttribute
  public int getMinorDelayMillis() {
    return retryController.getMinorDelayMillis();
  }

  @ManagedAttribute
  public void setMinorDelayMillis(final int minorDelayMillis) {
    retryController.setMinorDelayMillis(minorDelayMillis);
  }

  @ManagedAttribute
  public int getMajorDelayMillis() {
    return retryController.getMajorDelayMillis();
  }

  @ManagedAttribute
  public void setMajorDelayMillis(final int majorDelayMillis) {
    retryController.setMajorDelayMillis(majorDelayMillis);
  }

  @ManagedAttribute
  public String getMajorExceptionFilter() {
    return retryController.majorExceptionFilter().getFilterString();
  }

  @ManagedAttribute
  public void setMajorExceptionFilter(final String majorExceptionFilter) {
    retryController.majorExceptionFilter().setFilterString(majorExceptionFilter);
  }

  @ManagedAttribute
  public String getNoisyExceptionFilter() {
    return retryController.noisyExceptionFilter().getFilterString();
  }

  @ManagedAttribute
  public void setNoisyExceptionFilter(final String noisyExceptionFilter) {
    retryController.noisyExceptionFilter().setFilterString(noisyExceptionFilter);
  }
}
