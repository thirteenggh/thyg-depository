package org.sonatype.nexus.ui;

import java.util.Collections;
import java.util.List;

import org.sonatype.goodies.common.ComponentSupport;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Support for {@link UiPluginDescriptor} implementations.
 *
 * @since 3.20
 */
public abstract class UiPluginDescriptorSupport
  extends ComponentSupport
  implements UiPluginDescriptor
{
  private final String name;

  public UiPluginDescriptorSupport(final String name) {
    this.name = checkNotNull(name);
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public List<String> getScripts(final boolean isDebug) {
    return Collections.emptyList();
  }

  @Override
  public List<String> getStyles() {
    return Collections.emptyList();
  }
}
