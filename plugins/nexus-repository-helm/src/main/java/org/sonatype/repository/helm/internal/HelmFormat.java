package org.sonatype.repository.helm.internal;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.hash.HashAlgorithm;
import org.sonatype.nexus.repository.Format;

import com.google.common.collect.ImmutableList;

import static org.sonatype.nexus.common.hash.HashAlgorithm.SHA1;
import static org.sonatype.nexus.common.hash.HashAlgorithm.SHA256;

/**
 * Helm repository format.
 */
@Named(HelmFormat.NAME)
@Singleton
public class HelmFormat
    extends Format
{
  public static final String NAME = "helm";

  public static final List<HashAlgorithm> HASH_ALGORITHMS = ImmutableList.of(SHA1, SHA256);

  public HelmFormat() {
    super(NAME);
  }
}
