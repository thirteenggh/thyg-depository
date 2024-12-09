package org.sonatype.nexus.repository.npm.internal;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Named;

import org.sonatype.nexus.repository.rest.api.AssetXODescriptor;

import static org.sonatype.nexus.repository.npm.internal.NpmAttributes.P_NAME;
import static org.sonatype.nexus.repository.npm.internal.NpmAttributes.P_VERSION;

/**
 * @since 3.29
 */
@Named(NpmFormat.NAME)
public class NpmAssetXODescriptor
    implements AssetXODescriptor
{
  private static final Set<String> attributeKeys =
      Stream.of(P_NAME, P_VERSION).collect(Collectors.toSet());

  @Override
  public Set<String> listExposedAttributeKeys() {
    return attributeKeys;
  }
}
