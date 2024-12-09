package org.sonatype.nexus.repository.internal;

import javax.inject.Named;

import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.manager.internal.RepositoryFactory;
import org.sonatype.nexus.repository.manager.internal.RepositoryImpl;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Repository module.
 *
 * @since 3.0
 */
@Named
public class RepositoryModule
    extends AbstractModule
{
  @Override
  protected void configure() {
    install(new FactoryModuleBuilder()
            .implement(Repository.class, RepositoryImpl.class)
            .build(RepositoryFactory.class)
    );
  }
}
