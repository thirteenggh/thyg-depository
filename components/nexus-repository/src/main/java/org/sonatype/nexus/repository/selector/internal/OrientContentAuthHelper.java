package org.sonatype.nexus.repository.selector.internal;

import java.util.Arrays;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.security.ContentPermissionChecker;
import org.sonatype.nexus.repository.security.VariableResolverAdapter;
import org.sonatype.nexus.repository.security.VariableResolverAdapterManager;
import org.sonatype.nexus.selector.VariableSource;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.repository.storage.DatabaseThreadUtils.withOtherDatabase;
import static org.sonatype.nexus.security.BreadActions.BROWSE;

/**
 * Simple helper class that encapsulates the auth checks for reuse by the orientdb user defined functions
 * @since 3.2.1
 */
@Named
@Singleton
public class OrientContentAuthHelper
{
  private final VariableResolverAdapterManager variableResolverAdapterManager;

  private final ContentPermissionChecker contentPermissionChecker;

  @Inject
  public OrientContentAuthHelper(final VariableResolverAdapterManager variableResolverAdapterManager,
                                 final ContentPermissionChecker contentPermissionChecker)
  {
    this.variableResolverAdapterManager = checkNotNull(variableResolverAdapterManager);
    this.contentPermissionChecker = checkNotNull(contentPermissionChecker);
  }

  public boolean checkPathPermissions(final String path, final String format, final String... repositoryNames) {
    VariableResolverAdapter variableResolverAdapter = variableResolverAdapterManager.get(format);
    VariableSource variableSource = variableResolverAdapter.fromPath(path, format);
    return withOtherDatabase(() -> Arrays.stream(repositoryNames).anyMatch(repositoryName -> contentPermissionChecker
        .isPermitted(repositoryName, format, BROWSE, variableSource)));
  }

  public boolean checkPathPermissionsJexlOnly(final String path, final String format, final String... repositoryNames) {
    VariableResolverAdapter variableResolverAdapter = variableResolverAdapterManager.get(format);
    VariableSource variableSource = variableResolverAdapter.fromPath(path, format);
    return withOtherDatabase(() -> Arrays.stream(repositoryNames).anyMatch(repositoryName -> contentPermissionChecker
        .isPermittedJexlOnly(repositoryName, format, BROWSE, variableSource)));
  }
}
