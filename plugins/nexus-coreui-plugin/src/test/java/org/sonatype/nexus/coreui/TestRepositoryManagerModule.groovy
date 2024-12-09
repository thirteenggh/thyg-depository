package org.sonatype.nexus.coreui

import org.sonatype.nexus.repository.manager.RepositoryManager

import com.google.inject.AbstractModule

/**
 * Provide a mock RepositoryManager
 *
 * @since 3.0
 */
class TestRepositoryManagerModule
    extends AbstractModule
{
  public static final NAMES = ['foo', 'bar', 'baz'].asImmutable()

  @Override
  protected void configure() {
    bind(RepositoryManager).toInstance({ testValue -> NAMES.any({ it -> it.equalsIgnoreCase(testValue) }) } as RepositoryManager)
  }
}
