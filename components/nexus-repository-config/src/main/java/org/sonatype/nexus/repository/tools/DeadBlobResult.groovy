package org.sonatype.nexus.repository.tools

import javax.annotation.Nullable

import groovy.transform.ToString

import static com.google.common.base.Preconditions.checkNotNull

/**
 * @since 3.3
 */
@ToString(includePackage = false)
class DeadBlobResult<T>
{
  final String repositoryName

  final T asset

  final String errorMessage

  final ResultState resultState

  DeadBlobResult(final String repositoryName, @Nullable final T asset, final ResultState resultState,
                 final String errorMessage)
  {
    this.repositoryName = checkNotNull(repositoryName)
    this.asset = asset
    this.resultState = checkNotNull(resultState)
    this.errorMessage = checkNotNull(errorMessage)
  }
}
