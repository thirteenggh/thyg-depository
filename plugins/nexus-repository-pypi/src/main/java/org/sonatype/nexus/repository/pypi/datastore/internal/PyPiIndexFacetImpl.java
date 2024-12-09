package org.sonatype.nexus.repository.pypi.datastore.internal;

import javax.inject.Named;

import org.sonatype.nexus.repository.pypi.datastore.PypiContentFacet;
import org.sonatype.nexus.repository.FacetSupport;
import org.sonatype.nexus.repository.content.fluent.FluentAsset;
import org.sonatype.nexus.repository.pypi.internal.PyPiIndexFacet;

import static org.sonatype.nexus.repository.pypi.datastore.internal.ContentPypiPathUtils.indexPath;
import static org.sonatype.nexus.repository.pypi.internal.PyPiPathUtils.normalizeName;

/**
 * {@link PyPiIndexFacet} implementation.
 *
 * @since 3.29
 */
@Named
public class PyPiIndexFacetImpl
    extends FacetSupport
    implements PyPiIndexFacet
{
  @Override
  public void deleteRootIndex() {
    content().delete(indexPath());
  }

  @Override
  public void deleteIndex(String packageName) {
    String indexPath = indexPath(normalizeName(packageName));
    PypiContentFacet contentFacet = content();
    FluentAsset cachedIndex = contentFacet.assets().path(indexPath).find().orElse(null);

    /*
      There is a chance that the index wasn't found because of package name normalization. For example '.'
      characters are normalized to '-' so jts.python would have an index at /simple/jts-python/. It is possible that
      we could just check for the normalized name but we check for both just in case. Searching for an index with a
      normalized name first means that most, if not all, index deletions will only perform a single search.

      See https://issues.sonatype.org/browse/NEXUS-19303 for additional context.
     */
    if (cachedIndex == null) {
      indexPath = indexPath(packageName);
      cachedIndex = contentFacet.assets().path(indexPath).find().orElse(null);
    }

    if (cachedIndex != null) {
      cachedIndex.delete();
    }
  }

  private PypiContentFacet content() {
    return getRepository().facet(PypiContentFacet.class);
  }
}
