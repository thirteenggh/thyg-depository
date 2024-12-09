package org.sonatype.nexus.repository.rest.api;

import java.util.Set;

import javax.inject.Named;

/**
 * Descriptor for the {@link AssetXO} class
 *
 * @since 3.29
 */
@Named
public interface AssetXODescriptor
{
  Set<String> listExposedAttributeKeys();
}
