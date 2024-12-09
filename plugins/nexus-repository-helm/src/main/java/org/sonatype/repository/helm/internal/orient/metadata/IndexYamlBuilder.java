package org.sonatype.repository.helm.internal.orient.metadata;

import java.io.InputStream;
import java.io.OutputStream;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.repository.storage.StorageFacet;
import org.sonatype.nexus.repository.view.payloads.TempBlob;
import org.sonatype.nexus.thread.io.StreamCopier;
import org.sonatype.repository.helm.internal.HelmFormat;
import org.sonatype.repository.helm.internal.metadata.ChartIndex;
import org.sonatype.repository.helm.internal.util.YamlParser;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @since 3.28
 */
@Named
public class IndexYamlBuilder
{
  private final YamlParser yamlParser;

  @Inject
  public IndexYamlBuilder(final YamlParser yamlParser) {
    this.yamlParser = checkNotNull(yamlParser);
  }

  public TempBlob build(final ChartIndex index, final StorageFacet storageFacet) {
    return new StreamCopier<>(os -> readIntoYaml(os, index), is -> createTempBlob(is, storageFacet)).read();
  }

  private void readIntoYaml(final OutputStream os, final ChartIndex index) {
    yamlParser.write(os, index);
  }

  private TempBlob createTempBlob(final InputStream inputStream, final StorageFacet storageFacet) {
    return storageFacet.createTempBlob(inputStream, HelmFormat.HASH_ALGORITHMS);
  }
}
