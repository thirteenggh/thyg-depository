package org.sonatype.nexus.repository.view.handlers;

import javax.annotation.Nonnull;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.common.app.FrozenException;
import org.sonatype.nexus.repository.IllegalOperationException;
import org.sonatype.nexus.repository.InvalidContentException;
import org.sonatype.nexus.repository.http.HttpResponses;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Handler;
import org.sonatype.nexus.repository.view.Response;

import static org.sonatype.nexus.repository.http.HttpMethods.PUT;

/**
 * A format-neutral error handler for some exceptions. These exceptions are meant to signal some response directly
 * mappable onto a HTTP response, usually some 4xx error code.
 *
 * @since 3.0
 */
public class ExceptionHandler
    extends ComponentSupport
    implements Handler
{
  @Nonnull
  @Override
  public Response handle(@Nonnull final Context context) throws Exception {  //NOSONAR
    try {
      return context.proceed();
    }
    catch (IllegalOperationException e) {
      log.warn("Illegal operation: {} {}: {}",
          context.getRequest().getAction(),
          context.getRequest().getPath(),
          e.toString());
      return HttpResponses.badRequest(e.getMessage());
    }
    catch (InvalidContentException e) {
      log.warn("Invalid content: {} {}: {}",
          context.getRequest().getAction(),
          context.getRequest().getPath(),
          e.toString());
      if (PUT.equals(context.getRequest().getAction())) {
        return HttpResponses.badRequest(e.getMessage());
      }
      else {
        return HttpResponses.notFound(e.getMessage());
      }
    }
    catch (FrozenException e) {
      return readOnly(context, e);
    }
    catch (Exception e) {
      if (e.getCause() instanceof FrozenException) {
        return readOnly(context, e);
      }
      String exceptionName = e.getClass().getSimpleName();
      if (exceptionName.contains("OModificationOperationProhibitedException")
          || exceptionName.contains("OWriteOperationNotPermittedException")) {
        return readOnly(context, e);
      }
      else {
        throw e;
      }
    }
  }

  private Response readOnly(final Context context, Exception e) {
    log.warn("Trust Repository Manager is in read-only mode: {} {}: {}",
        context.getRequest().getAction(),
        context.getRequest().getPath(),
        e.toString());

    return HttpResponses.serviceUnavailable("Trust Repository Manager is in read-only mode");
  }
}
