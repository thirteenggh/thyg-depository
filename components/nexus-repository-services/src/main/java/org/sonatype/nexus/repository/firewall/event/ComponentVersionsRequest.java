package org.sonatype.nexus.repository.firewall.event;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.sonatype.goodies.packageurl.PackageUrl;

/**
 * @since 3.29
 */
public class ComponentVersionsRequest
{
  private final CompletableFuture<List<String>> result = new CompletableFuture<>();

  private final PackageUrl packageUrl;

  public ComponentVersionsRequest(final PackageUrl packageUrl) {
    this.packageUrl = packageUrl;
  }

  public CompletableFuture<List<String>> getResult() {
    return result;
  }

  public PackageUrl getPackageUrl() {
    return packageUrl;
  }
}
