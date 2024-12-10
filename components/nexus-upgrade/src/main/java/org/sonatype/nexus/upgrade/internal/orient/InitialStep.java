package org.sonatype.nexus.upgrade.internal.orient;

import java.util.List;
import java.util.Map;

import org.sonatype.nexus.common.app.VersionComparator;
import org.sonatype.nexus.upgrade.plan.Dependency;
import org.sonatype.nexus.upgrade.plan.DependencySource;

import com.google.common.collect.ImmutableList;

import static com.google.common.base.Preconditions.checkNotNull;

public class InitialStep
    implements UpgradePoint, DependencySource<UpgradePoint>
{
  private final Map<String, String> modelVersions;

  public InitialStep(final Map<String, String> modelVersions) {
    this.modelVersions = checkNotNull(modelVersions);
  }

  @Override
  public List<Dependency<UpgradePoint>> getDependencies() {
    return ImmutableList.of();
  }

  @Override
  public boolean satisfies(final String model, final String version) {
    return VersionComparator.INSTANCE.compare(modelVersions.getOrDefault(model, "1.0"), version) >= 0;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" +
        "modelVersions=" + modelVersions +
        '}';
  }
}
