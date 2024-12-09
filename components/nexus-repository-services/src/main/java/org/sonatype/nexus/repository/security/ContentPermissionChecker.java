package org.sonatype.nexus.repository.security;

import java.util.Set;

import org.sonatype.nexus.selector.VariableSource;

/**
 * Repository content permission checker
 *
 * @since 3.1
 */
public interface ContentPermissionChecker
{
  /**
   * Ensure that either the view permission or the content selector permission is permitted
   */
  boolean isPermitted(String repositoryName,
                      String repositoryFormat,
                      String action,
                      VariableSource variableSource);

  /**
   * Ensure that either the view permission or that a JEXL content selector permission is permitted
   *
   * @since 3.6
   */
  boolean isPermittedJexlOnly(String repositoryName,
                              String repositoryFormat,
                              String action,
                              VariableSource variableSource);

  /**
   * Ensure that either the view permission or the content selector permission is permitted for the desired
   * repositories
   *
   * @since 3.14
   */
  boolean isPermitted(Set<String> repositoryNames,
                      String repositoryFormat,
                      String action,
                      VariableSource variableSource);
}
