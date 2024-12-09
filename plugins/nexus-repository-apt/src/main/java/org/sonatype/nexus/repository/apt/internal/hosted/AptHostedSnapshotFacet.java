package org.sonatype.nexus.repository.apt.internal.hosted;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.sonatype.nexus.repository.apt.AptFacet;
import org.sonatype.nexus.repository.apt.internal.snapshot.AptSnapshotFacetSupport;
import org.sonatype.nexus.repository.apt.internal.snapshot.SnapshotItem;
import org.sonatype.nexus.repository.apt.internal.snapshot.SnapshotItem.ContentSpecifier;
import org.sonatype.nexus.repository.view.Content;

/**
 * @since 3.17
 */
@Named
public class AptHostedSnapshotFacet
    extends AptSnapshotFacetSupport
{
  @Override
  protected List<SnapshotItem> fetchSnapshotItems(final List<ContentSpecifier> specs) throws IOException {
      List<SnapshotItem> list = new ArrayList<>();
      for (ContentSpecifier spec : specs) {
        SnapshotItem item = getItem(spec);
        if (item != null) {
          list.add(item);
        }
      }
      return list;
  }

  private SnapshotItem getItem(final ContentSpecifier spec) throws IOException {
      AptFacet apt = getRepository().facet(AptFacet.class);
      Content content = apt.get(spec.path);
      if (content == null) {
        return null;
      }
      return new SnapshotItem(spec, content);
  }
}
