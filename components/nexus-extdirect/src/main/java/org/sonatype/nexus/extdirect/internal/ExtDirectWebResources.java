package org.sonatype.nexus.extdirect.internal;

import java.io.File;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.app.ApplicationDirectories;
import org.sonatype.nexus.webresources.FileWebResource;
import org.sonatype.nexus.webresources.WebResource;
import org.sonatype.nexus.webresources.WebResourceBundle;

import com.google.common.collect.ImmutableList;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.webresources.WebResource.JAVASCRIPT;

/**
 * Ext.Direct web-resources.
 *
 * @since 3.0
 */
@Named
@Singleton
public class ExtDirectWebResources
    implements WebResourceBundle
{
  private final ApplicationDirectories directories;

  @Inject
  public ExtDirectWebResources(final ApplicationDirectories directories) {
    this.directories = checkNotNull(directories);
  }

  private WebResource create(final String fileName, final String path) {
    File file = new File(directories.getTemporaryDirectory(), fileName);
    return new FileWebResource(file, path, JAVASCRIPT, true);
  }

  // FIXME: Would like to replace the generation here instead of relying on file which could be changed, etc
  // FIXME: Also we need a bit more control over the generation of this content so we can set the baseUrl etc

  @Override
  public List<WebResource> getResources() {
    return ImmutableList.of(
        create("nexus-extdirect/api.js", "/static/rapture/extdirect-prod.js"),
        create("nexus-extdirect/api-debug.js", "/static/rapture/extdirect-debug.js")
    );
  }
}
