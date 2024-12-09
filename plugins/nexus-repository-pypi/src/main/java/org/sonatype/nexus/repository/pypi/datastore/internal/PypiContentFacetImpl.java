package org.sonatype.nexus.repository.pypi.datastore.internal;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.common.hash.HashAlgorithm;
import org.sonatype.nexus.repository.pypi.datastore.PypiContentFacet;
import org.sonatype.nexus.repository.content.facet.ContentFacetSupport;
import org.sonatype.nexus.repository.content.fluent.FluentAsset;
import org.sonatype.nexus.repository.content.fluent.FluentComponent;
import org.sonatype.nexus.repository.content.fluent.FluentQuery;
import org.sonatype.nexus.repository.content.store.FormatStoreManager;
import org.sonatype.nexus.repository.pypi.internal.PyPiFormat;
import org.sonatype.nexus.repository.view.Payload;
import org.sonatype.nexus.repository.view.payloads.TempBlob;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.common.hash.HashAlgorithm.MD5;
import static org.sonatype.nexus.common.hash.HashAlgorithm.SHA1;
import static org.sonatype.nexus.repository.pypi.datastore.internal.PyPiDataUtils.copyFormatAttributes;
import static org.sonatype.nexus.repository.pypi.internal.PyPiStorageUtils.getNameAttributes;

/**
 * @since 3.29
 */
@Named(PyPiFormat.NAME)
public class PypiContentFacetImpl
    extends ContentFacetSupport
    implements PypiContentFacet
{
  private static final Iterable<HashAlgorithm> HASHING = ImmutableList.of(MD5, SHA1);

  @Inject
  public PypiContentFacetImpl(
      @Named(PyPiFormat.NAME) final FormatStoreManager formatStoreManager)
  {
    super(formatStoreManager);
  }

  @Override
  public Iterable<FluentAsset> browseAssets() {
    return assets().browse(Integer.MAX_VALUE, null);
  }

  @Override
  public Optional<FluentAsset> getAsset(final String path) {
    checkNotNull(path);
    return assets().path(path).find();
  }

  @Override
  public boolean delete(final String path) {
    checkNotNull(path);
    return assets().path(path).find().map(FluentAsset::delete).orElse(false);
  }

  @Override
  public FluentAsset findOrCreateAsset(
      final String packagePath,
      final FluentComponent component,
      final String assetKind)
  {
    checkNotNull(packagePath);
    checkNotNull(component);
    checkNotNull(assetKind);

    return assets()
        .path(packagePath)
        .kind(assetKind)
        .component(component)
        .getOrCreate();
  }

  @Override
  public FluentAsset findOrCreateAsset(
      final String packagePath,
      final String assetKind)
  {
    checkNotNull(packagePath);
    checkNotNull(assetKind);

    return assets()
        .path(packagePath)
        .kind(assetKind)
        .getOrCreate();
  }

  @Override
  public boolean isComponentExists(final String name) {
    return componentsByName(name).count() > 0;
  }

  @Override
  public List<FluentAsset> assetsByComponentName(String name) {
    return componentsByName(name)
        .browse(Integer.MAX_VALUE, null)
        .stream()
        .flatMap(component -> component.assets().stream())
        .collect(Collectors.toList());
  }

  @Override
  public FluentComponent findOrCreateComponent(
      final String name,
      final String version,
      final String normalizedName)
  {
    checkNotNull(name);
    checkNotNull(version);
    checkNotNull(normalizedName);

    FluentComponent component = components().name(normalizedName).version(version).getOrCreate();
    copyFormatAttributes(component, getNameAttributes(name));
    return component;
  }

  @Override
  public TempBlob getTempBlob(final Payload payload) {
    checkNotNull(payload);
    return blobs().ingest(payload, HASHING);
  }

  @Override
  public TempBlob getTempBlob(final InputStream content, @Nullable final String contentType) {
    return blobs().ingest(content, contentType, HASHING);
  }

  private FluentQuery<FluentComponent> componentsByName(final String name) {
    String filter = "name = #{filterParams.nameParam}";
    Map<String, Object> params = ImmutableMap.of("nameParam", name);
    return facet(PypiContentFacet.class).components().byFilter(filter, params);
  }
}
