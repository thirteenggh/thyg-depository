package org.sonatype.nexus.repository.content.store;

/**
 * Extend this module if your format uses the standard content store APIs.
 * Declare your DAOs and annotate the module with the name of your format:
 *
 * <code><pre>
 * &#64;Named("example")
 * public class ExampleStoreModule
 *     extends FormatStoreModule&lt;ExampleContentRepositoryDAO,
 *                               ExampleComponentDAO,
 *                               ExampleAssetDAO,
 *                               ExampleAssetBlobDAO&gt;
 * {
 *   // nothing to add...
 * }
 * </pre></code>
 *
 * @since 3.24
 */
public abstract class FormatStoreModule<CONTENT_REPOSITORY_DAO extends ContentRepositoryDAO,
                                        COMPONENT_DAO extends ComponentDAO,
                                        ASSET_DAO extends AssetDAO,
                                        ASSET_BLOB_DAO extends AssetBlobDAO>

    extends BespokeFormatStoreModule<ContentRepositoryStore<CONTENT_REPOSITORY_DAO>,
                                     ComponentStore<COMPONENT_DAO>,
                                     AssetStore<ASSET_DAO>,
                                     AssetBlobStore<ASSET_BLOB_DAO>>
{
  // nothing to add...
}
