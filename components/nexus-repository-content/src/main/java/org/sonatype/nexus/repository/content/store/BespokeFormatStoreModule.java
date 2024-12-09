package org.sonatype.nexus.repository.content.store;

/**
 * Extend this module if your format adds methods to its content store APIs.
 * Declare your stores and annotate the module with the name of your format.
 * You can mix custom stores with standard stores in the same bespoke module:
 *
 * <code><pre>
 * &#64;Named("example")
 * public class ExampleStoreModule
 *     extends BespokeFormatStoreModule&lt;ContentRepositoryStore&lt;ExampleContentRepositoryDAO&gt;,
 *                                      ExampleComponentStore,
 *                                      ExampleAssetStore,
 *                                      AssetBlobStore&lt;ExampleAssetBlobDAO&gt;&gt;
 * {
 *   // nothing to add...
 * }
 * </pre></code>
 *
 * In this scenario {@code ExampleContentStore} and {@code ExampleAssetStore}
 * have added methods that query supplementary data held in columns attached
 * to the format's component and asset tables.
 *
 * Most formats don't need this flexibility and should use {@link FormatStoreModule}.
 *
 * @since 3.24
 */
@SuppressWarnings({"rawtypes", "unchecked", "unused"})
public abstract class BespokeFormatStoreModule<CONTENT_REPOSITORY_STORE extends ContentRepositoryStore<?>,
                                               COMPONENT_STORE extends ComponentStore<?>,
                                               ASSET_STORE extends AssetStore<?>,
                                               ASSET_BLOB_STORE extends AssetBlobStore<?>>
    extends ContentStoreModule
{
  protected BespokeFormatStoreModule() {
    super(BespokeFormatStoreModule.class);
  }

  @Override
  protected void configure() {
    super.configure();

    bind(FormatStoreManager.class).annotatedWith(format).toInstance(new FormatStoreManager(formatClassPrefix));
  }
}
