package org.sonatype.nexus.internal.app;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.common.app.ManagedLifecycle.Phase;
import org.sonatype.nexus.common.app.ManagedLifecycleManager;
import org.sonatype.nexus.jmx.reflect.ManagedAttribute;
import org.sonatype.nexus.jmx.reflect.ManagedObject;
import org.sonatype.nexus.jmx.reflect.ManagedOperation;

import static com.google.common.base.Preconditions.checkNotNull;

@Named
@Singleton
@ManagedObject
public class ManagedLifecycleBean
    extends ComponentSupport
{
  private final ManagedLifecycleManager lifecycleManager;

  @Inject
  public ManagedLifecycleBean(final ManagedLifecycleManager lifecycleManager) {
    this.lifecycleManager = checkNotNull(lifecycleManager);
  }

  @ManagedAttribute
  public String getPhase() {
    return lifecycleManager.getCurrentPhase().name();
  }

  @ManagedAttribute
  public void setPhase(final String phase) {
    try {
      lifecycleManager.to(Phase.valueOf(phase));
    }
    catch (Exception e) {
      log.warn("Problem moving to phase {}", phase, e);
      throw new RuntimeException("Problem moving to phase " + phase + ": " + e);
    }
  }

  @ManagedOperation
  public void bounce(final String phase) {
    try {
      lifecycleManager.bounce(Phase.valueOf(phase));
    }
    catch (Exception e) {
      log.warn("Problem bouncing phase {}", phase, e);
      throw new RuntimeException("Problem bouncing phase " + phase + ": " + e);
    }
  }
}
