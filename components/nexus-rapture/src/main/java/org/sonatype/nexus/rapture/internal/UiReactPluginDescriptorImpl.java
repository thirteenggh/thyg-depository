package org.sonatype.nexus.rapture.internal;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.ui.UiPluginDescriptor;
import org.sonatype.nexus.ui.UiUtil;

import org.eclipse.sisu.Priority;
import org.eclipse.sisu.space.ClassSpace;

import static java.util.Arrays.asList;

/**
 * {@link UiPluginDescriptor} for {@code nexus-rapture} react code.
 *
 * @since 3.25
 */
@Named
@Singleton
@Priority(Integer.MAX_VALUE) // always load first
public class UiReactPluginDescriptorImpl
    implements UiPluginDescriptor
{
  private final List<String> scripts;

  private final List<String> debugScripts;

  private final List<String> styles;

  @Inject
  public UiReactPluginDescriptorImpl(final ClassSpace space) {
    scripts = asList(UiUtil.getPathForFile("nexus-rapture-bundle.js", space));
    debugScripts = asList(UiUtil.getPathForFile("nexus-rapture-bundle.debug.js", space));
    styles = asList(UiUtil.getPathForFile("nexus-rapture-bundle.css", space));
  }

  @Override
  public String getName() {
    return "nexus-rapture";
  }

  @Nullable
  @Override
  public List<String> getScripts(final boolean isDebug) {
    return isDebug ? debugScripts : scripts;
  }

  @Nullable
  @Override
  public List<String> getStyles() {
    return styles;
  }
}
