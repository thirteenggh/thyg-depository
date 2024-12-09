package org.sonatype.nexus.repository.routing.internal;

import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.entity.EntityId;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.manager.RepositoryManager;
import org.sonatype.nexus.repository.routing.RoutingMode;
import org.sonatype.nexus.repository.routing.RoutingRule;
import org.sonatype.nexus.repository.routing.RoutingRuleHelper;
import org.sonatype.nexus.repository.security.RepositoryAdminPermission;
import org.sonatype.nexus.repository.security.RepositoryPermissionChecker;
import org.sonatype.nexus.repository.types.ProxyType;

import org.apache.shiro.authz.Permission;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static org.sonatype.nexus.security.BreadActions.ADD;
import static org.sonatype.nexus.security.BreadActions.READ;

/**
 * @since 3.16
 */
@Named
@Singleton
public class RoutingRuleHelperImpl
    implements RoutingRuleHelper
{
  private final RoutingRuleCache routingRuleCache;

  private final RepositoryManager repositoryManager;

  private final RepositoryPermissionChecker repositoryPermissionChecker;

  private volatile List<Permission> repositoryAddPermissions;

  @Inject
  public RoutingRuleHelperImpl(
      final RoutingRuleCache routingRuleCache,
      final RepositoryManager repositoryManager,
      final RepositoryPermissionChecker repositoryPermissionChecker)
  {
    this.routingRuleCache = checkNotNull(routingRuleCache);
    this.repositoryManager = checkNotNull(repositoryManager);
    this.repositoryPermissionChecker = checkNotNull(repositoryPermissionChecker);
  }

  @Override
  public boolean isAllowed(final Repository repository, final String path) {
    RoutingRule routingRule = routingRuleCache.getRoutingRule(repository);

    if (routingRule == null) {
      return true;
    }

    return isAllowed(routingRule, path);
  }

  public boolean isAllowed(final RoutingRule routingRule, final String path) {
    return isAllowed(routingRule.mode(), routingRule.matchers(), path);
  }

  @Override
  public boolean isAllowed(final RoutingMode mode, final List<String> matchers, final String path) {
    boolean matches = matchers.stream().anyMatch(path::matches);
    return (!matches && mode == RoutingMode.BLOCK) || (matches && mode == RoutingMode.ALLOW);
  }

  @Override
  public Map<EntityId, List<Repository>> calculateAssignedRepositories() {
    return StreamSupport.stream(repositoryManager.browse().spliterator(), false)
        .filter(repository -> routingRuleCache.getRoutingRuleId(repository) != null)
        .collect(groupingBy(routingRuleCache::getRoutingRuleId, toList()));
  }

  @Override
  public void ensureUserHasPermissionToRead() {
    List<Permission> permissions = getRepositoryAddPermissions();
    if (!permissions.isEmpty()) { // avoid log-spam when we start NXRM with no recipes
      repositoryPermissionChecker.ensureUserHasAnyPermissionOrAdminAccess(
          permissions,
          READ,
          repositoryManager.browse()
      );
    }
  }

  private List<Permission> getRepositoryAddPermissions() {
    if (null == repositoryAddPermissions) {
      repositoryAddPermissions = repositoryManager.getAllSupportedRecipes().stream()
          .filter(r -> r.getType().getValue().equals(ProxyType.NAME))
          .map(r -> new RepositoryAdminPermission(r.getFormat().getValue(), "*", singletonList(ADD)))
          .collect(toList());
    }
    return repositoryAddPermissions;
  }
}
