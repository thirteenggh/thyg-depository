package org.sonatype.nexus.repository.r.internal;

import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Named;

import org.sonatype.nexus.repository.FacetSupport;
import org.sonatype.nexus.repository.r.RFacet;
import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.storage.Bucket;
import org.sonatype.nexus.repository.storage.Component;
import org.sonatype.nexus.repository.storage.StorageTx;

import static org.sonatype.nexus.repository.r.internal.RAttributes.P_PACKAGE;
import static org.sonatype.nexus.repository.r.internal.RAttributes.P_VERSION;
import static org.sonatype.nexus.repository.r.internal.util.RFacetUtils.findAsset;
import static org.sonatype.nexus.repository.r.internal.util.RFacetUtils.findComponent;
import static org.sonatype.nexus.repository.r.internal.util.RPathUtils.getAssetKind;
import static org.sonatype.nexus.repository.r.internal.util.RPathUtils.getBasePath;
import static org.sonatype.nexus.repository.storage.AssetEntityAdapter.P_ASSET_KIND;

/**
 * {@link RFacet} implementation.
 *
 * @since 3.28
 */
@Named
public class RFacetImpl
    extends FacetSupport
    implements RFacet
{
  @Override
  public Component findOrCreateComponent(final StorageTx tx,
                                         final String path,
                                         final Map<String, String> attributes)
  {
    String name = attributes.get(P_PACKAGE);
    String version = attributes.get(P_VERSION);
    String group = getBasePath(path);

    Component component = findComponent(tx, getRepository(), name, version, group);
    if (component == null) {
      Bucket bucket = tx.findBucket(getRepository());
      component = tx.createComponent(bucket, getRepository().getFormat())
          .name(name)
          .version(version)
          .group(group);
      tx.saveComponent(component);
    }

    return component;
  }

  @Override
  public Asset findOrCreateAsset(final StorageTx tx,
                                 final Component component,
                                 final String path,
                                 final Map<String, String> attributes)
  {
    Bucket bucket = tx.findBucket(getRepository());
    Asset asset = findAsset(tx, bucket, path);
    if (asset == null) {
      asset = tx.createAsset(bucket, component);
      asset.name(path);

      // TODO: Make this a bit more robust (could be problematic if keys are removed in later versions, or if keys clash)
      for (Entry<String, String> attribute : attributes.entrySet()) {
        asset.formatAttributes().set(attribute.getKey(), attribute.getValue());
      }
      asset.formatAttributes().set(P_ASSET_KIND, getAssetKind(path).name());
      tx.saveAsset(asset);
    }

    return asset;
  }

  @Override
  public Asset findOrCreateAsset(final StorageTx tx, final String path) {
    Bucket bucket = tx.findBucket(getRepository());
    Asset asset = findAsset(tx, bucket, path);
    if (asset == null) {
      asset = tx.createAsset(bucket, getRepository().getFormat());
      asset.name(path);
      asset.formatAttributes().set(P_ASSET_KIND, getAssetKind(path).name());
      tx.saveAsset(asset);
    }

    return asset;
  }
}
