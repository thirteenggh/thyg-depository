package org.sonatype.nexus.repository.config

import org.sonatype.nexus.repository.manager.RepositoryManager

import spock.lang.Specification

/**
 * Tests validity of Repository names validated by {@link UniqueRepositoryNameValidator}
 * @since 3.0
 */
class UniqueRepositoryNameValidatorTest
    extends Specification
{
  RepositoryManager repositoryManager = Mock()

  UniqueRepositoryNameValidator validator = new UniqueRepositoryNameValidator(repositoryManager)

  def "Name is valid when the RepositoryManager says it does not exist"(String name, boolean exists) {
    when:
      def valid = validator.isValid(name, null)

    then:
      1 * repositoryManager.exists(name) >> exists
      valid == !exists

    where:
      name  | exists
      'foo' | true
      'foo' | false
  }
}
