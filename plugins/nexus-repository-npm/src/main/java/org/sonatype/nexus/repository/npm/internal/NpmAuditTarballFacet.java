package org.sonatype.nexus.repository.npm.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;

import javax.annotation.Nonnull;

import org.sonatype.nexus.common.collect.AttributesMap;
import org.sonatype.nexus.repository.Facet.Exposed;
import org.sonatype.nexus.repository.FacetSupport;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.group.GroupHandler;
import org.sonatype.nexus.repository.npm.internal.NpmProxyFacet.ProxyTarget;
import org.sonatype.nexus.repository.proxy.ProxyFacet;
import org.sonatype.nexus.repository.types.GroupType;
import org.sonatype.nexus.repository.types.ProxyType;
import org.sonatype.nexus.repository.view.Content;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Handler;
import org.sonatype.nexus.repository.view.Matcher;
import org.sonatype.nexus.repository.view.Request;
import org.sonatype.nexus.repository.view.Response;
import org.sonatype.nexus.repository.vulnerability.AuditComponent;
import org.sonatype.nexus.repository.vulnerability.AuditRepositoryComponent;
import org.sonatype.nexus.repository.vulnerability.exceptions.TarballLoadingException;
import org.sonatype.nexus.thread.NexusExecutorService;
import org.sonatype.nexus.thread.NexusThreadFactory;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.concurrent.TimeUnit.SECONDS;
import static java.util.stream.Collectors.toList;
import static org.sonatype.nexus.repository.group.GroupHandler.IGNORE_FIREWALL;
import static org.sonatype.nexus.repository.http.HttpMethods.GET;
import static org.sonatype.nexus.repository.npm.internal.NpmPaths.tarballMatcher;
import static org.sonatype.nexus.repository.npm.internal.NpmProxyFacet.ProxyTarget.TARBALL;

/**
 * Facet for saving/fetching npm package tarballs.
 *
 * @since 3.24
 */
@Exposed
public abstract class NpmAuditTarballFacet
    extends FacetSupport
{
  private final int maxConcurrentRequests;

  private final TarballGroupHandler tarballGroupHandler;

  private ExecutorService executor;

  public NpmAuditTarballFacet(final int maxConcurrentRequests)
  {
    checkArgument(maxConcurrentRequests > 0, "nexus.npm.audit.maxConcurrentRequests must be greater than 0");
    this.maxConcurrentRequests = maxConcurrentRequests;
    this.tarballGroupHandler = new TarballGroupHandler(this::getHashsum);
  }

  @Override
  protected void doStart() {
    executor = NexusExecutorService.forCurrentSubject(Executors.newFixedThreadPool(
        maxConcurrentRequests, new NexusThreadFactory("npm-audit-tasks", "npm-audit")));
  }

  @Override
  protected void doStop() throws InterruptedException {
    executor.shutdown();
    if (!executor.awaitTermination(10, SECONDS)) {
      log.warn("Failed to terminate thread pool in allotted time");
    }
    executor = null;
  }

  public Set<AuditRepositoryComponent> download(
      final Context originalContext,
      final Set<AuditComponent> auditComponents) throws TarballLoadingException
  {
    List<Callable<AuditRepositoryComponent>> tasks = new ArrayList<>();
    auditComponents.forEach(auditComponent -> tasks.add(() -> download(originalContext, auditComponent)));
    // submit task to execute
    List<Future<AuditRepositoryComponent>> repositoryComponentFutures = tasks.stream()
        .map(executor::submit)
        .collect(toList());

    Set<AuditRepositoryComponent> repositoryComponents = new HashSet<>(auditComponents.size());
    try {
      for (Future<AuditRepositoryComponent> repositoryComponentFuture : repositoryComponentFutures) {
        repositoryComponents.add(repositoryComponentFuture.get());
      }
    }
    catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException(e);
    }
    return repositoryComponents;
  }

  private AuditRepositoryComponent download(
      final Context originalContext,
      final AuditComponent auditComponent) throws TarballLoadingException
  {
    checkNotNull(auditComponent);
    String packageName = auditComponent.getName();
    String packageVersion = auditComponent.getVersion();
    String repositoryPath = NpmMetadataUtils.createRepositoryPath(packageName, packageVersion);
    final Request request = new Request.Builder()
        .action(GET)
        .path("/" + repositoryPath)
        .build();
    Repository repository = getRepository();
    Context context = new Context(repository, request);
    context.getAttributes().backing().putAll(originalContext.getAttributes().backing());
    Matcher tarballMatcher = tarballMatcher(GET)
        .handler(new EmptyHandler()).create().getMatcher();
    tarballMatcher.matches(context);
    context.getAttributes().set(ProxyTarget.class, TARBALL);
    context.getAttributes().set(IGNORE_FIREWALL, true);

    Optional<String> hashsumOpt;
    String repositoryType = repository.getType().getValue();
    if (repositoryType.equals(GroupType.NAME)) {
      hashsumOpt = tarballGroupHandler.getTarballHashsum(context);
    }
    else if (repositoryType.equals(ProxyType.NAME)) {
      hashsumOpt = getComponentHashsumForProxyRepo(repository, context);
    }
    else {
      // for an npm-hosted repository is no way to get npm package hashsum info.
      String errorMsg = String.format("The %s repository is not supported", repositoryType);
      throw new UnsupportedOperationException(errorMsg);
    }

    if (!hashsumOpt.isPresent()) {
      log.warn(String.format("Can't get hashsum for the %s package", auditComponent));
      return new AuditRepositoryComponent(auditComponent.getPackageType(), repositoryPath, null);
    }
    return new AuditRepositoryComponent(auditComponent.getPackageType(), repositoryPath, hashsumOpt.get());
  }

  protected abstract Optional<String> getComponentHashsumForProxyRepo(
      final Repository repository,
      final Context context) throws TarballLoadingException;

  protected Optional<String> getComponentHashsum(final Repository repository, final Context context) throws IOException {
    Content content = repository.facet(ProxyFacet.class).get(context);
    if (content != null) {
      return getHashsum(content.getAttributes());
    }
    return Optional.empty();
  }

  protected abstract Optional<String> getHashsum(final AttributesMap attributes);

  /**
   * This handler should do nothing cause it would never be used.
   */
  private static final class EmptyHandler
      implements Handler
  {
    @Nonnull
    @Override
    public Response handle(@Nonnull final Context context) throws Exception
    {
      return context.proceed();
    }
  }

  /**
   * This handler is used to download and get npm package tarballs for a group repository.
   */
  private static final class TarballGroupHandler
      extends GroupHandler
  {
    private final Function<AttributesMap, Optional<String>> hashFetcher;

    public TarballGroupHandler(final Function<AttributesMap, Optional<String>> hashFetcher) {
      this.hashFetcher = hashFetcher;
    }

    public Optional<String> getTarballHashsum(final Context context) throws TarballLoadingException {
      DispatchedRepositories dispatched = context.getRequest().getAttributes()
          .getOrCreate(DispatchedRepositories.class);
      try {
        Response response = super.doGet(context, dispatched);
        if (response.getPayload() instanceof Content) {
          Content content = (Content) response.getPayload();
          return hashFetcher.apply(content.getAttributes());
        }
      }
      catch (Exception e) {
        throw new TarballLoadingException(e.getMessage(), e);
      }

      return Optional.empty();
    }
  }
}
