package org.sonatype.nexus.repository.pypi.datastore.internal.store;

import javax.inject.Named;

import org.sonatype.nexus.repository.content.store.FormatStoreModule;
import org.sonatype.nexus.repository.pypi.internal.PyPiFormat;

/**
 * @since 3.29
 */
@Named(PyPiFormat.NAME)
public class PypiStoreModule
    extends FormatStoreModule<PypiContentRepositoryDAO,
    PypiComponentDAO,
    PypiAssetDAO,
    PypiAssetBlobDAO>
{
  // nothing to add...
}
