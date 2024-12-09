package org.sonatype.nexus.upgrade.internal.orient;

import org.sonatype.nexus.upgrade.plan.Dependency;
import org.sonatype.nexus.upgrade.plan.DependencyResolver;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Upgrade {@link Dependency} that can be resolved by the {@link DependencyResolver}.
 * 
 * @since 3.1
 */
public class UpgradeDependency
    implements Dependency<UpgradePoint>
{
  private final String model;

  private final String version;

  public UpgradeDependency(final String model, final String version) {
    this.model = checkNotNull(model);
    this.version = checkNotNull(version);
  }

  @Override
  public boolean satisfiedBy(final UpgradePoint upgradePoint) {
    return upgradePoint.satisfies(model, version);
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" +
        "model='" + model + '\'' +
        ", version='" + version + '\'' +
        '}';
  }
}
