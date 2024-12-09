package org.sonatype.nexus.repository.maven.internal;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Named;

import org.sonatype.nexus.repository.rest.api.AssetXODescriptor;

import static org.sonatype.nexus.repository.maven.internal.Attributes.P_GROUP_ID;
import static org.sonatype.nexus.repository.maven.internal.Attributes.P_ARTIFACT_ID;
import static org.sonatype.nexus.repository.maven.internal.Attributes.P_VERSION;
import static org.sonatype.nexus.repository.maven.internal.Attributes.P_CLASSIFIER;
import static org.sonatype.nexus.repository.maven.internal.Attributes.P_EXTENSION;

/**
 * @since 3.29
 */
@Named(Maven2Format.NAME)
public class MavenAssetXODescriptor
    implements AssetXODescriptor
{
  private static final Set<String> attributeKeys =
      Stream.of(P_GROUP_ID, P_ARTIFACT_ID, P_VERSION, P_CLASSIFIER, P_EXTENSION).collect(Collectors.toSet());

  @Override
  public Set<String> listExposedAttributeKeys() {
    return attributeKeys;
  }
}
