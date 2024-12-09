package org.sonatype.nexus.repository.pypi.internal;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Named;

import org.sonatype.nexus.repository.rest.api.AssetXODescriptor;

import static org.sonatype.nexus.repository.pypi.internal.PyPiAttributes.P_NAME;
import static org.sonatype.nexus.repository.pypi.internal.PyPiAttributes.P_VERSION;
import static org.sonatype.nexus.repository.pypi.internal.PyPiAttributes.P_PLATFORM;

/**
 * @since 3.29
 */
@Named(PyPiFormat.NAME)
public class PyPiAssetXODescriptor
    implements AssetXODescriptor
{
  private static final Set<String> attributeKeys =
      Stream.of(P_NAME, P_VERSION, P_PLATFORM).collect(Collectors.toSet());

  @Override
  public Set<String> listExposedAttributeKeys() {
    return attributeKeys;
  }
}
