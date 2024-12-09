package org.sonatype.nexus.repository.storage;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Handler;
import org.sonatype.nexus.repository.view.Response;
import org.sonatype.nexus.transaction.UnitOfWork;

/**
 * Invokes the remaining handler chain within the repository's unit-of-work.
 *
 * @since 3.0
 */
@Named
@Singleton
public class UnitOfWorkHandler
    extends ComponentSupport
    implements Handler
{
  @Override
  public Response handle(Context context) throws Exception {
    UnitOfWork.begin(context.getRepository().facet(StorageFacet.class).txSupplier());
    try {
      return context.proceed();
    }
    finally {
      UnitOfWork.end();
    }
  }
}
