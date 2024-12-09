package org.sonatype.nexus.repository.pypi.datastore.internal.browse;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.pypi.datastore.internal.ContentPypiPathUtils;
import org.sonatype.nexus.repository.browse.node.BrowsePath;
import org.sonatype.nexus.repository.browse.node.BrowsePathBuilder;
import org.sonatype.nexus.repository.content.Asset;
import org.sonatype.nexus.repository.content.browse.DefaultBrowseNodeGenerator;
import org.sonatype.nexus.repository.pypi.internal.PyPiFormat;

import com.google.common.base.Splitter;

import static org.sonatype.nexus.repository.browse.node.BrowsePath.SLASH_CHAR;

/**
 * @since 3.29
 */
@Singleton
@Named(PyPiFormat.NAME)
public class PypiBrowseNodeGenerator
    extends DefaultBrowseNodeGenerator
{
  @Override
  public List<BrowsePath> computeAssetPaths(final Asset asset) {
    if (ContentPypiPathUtils.indexPath().equals(asset.path())) {
      List<String> pathSegments = Splitter.on(SLASH_CHAR).omitEmptyStrings().splitToList(asset.path());
      return BrowsePathBuilder.fromPaths(pathSegments, true);
    }
    return super.computeAssetPaths(asset);
  }
}
