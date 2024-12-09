package org.sonatype.nexus.repository.view.handlers;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.common.collect.AttributeKey;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Handler;
import org.sonatype.nexus.repository.view.Response;

import com.google.common.base.Stopwatch;

/**
 * Simple timing handler.
 *
 * @since 3.0
 */
@Named
@Singleton
public class TimingHandler
    extends ComponentSupport
    implements Handler
{
  public static final String ELAPSED_KEY = AttributeKey.get(TimingHandler.class, "elapsed");

  private final Handler meteringHandler;

  @Inject
  public TimingHandler(@Named("nexus.analytics.meteringHandler") @Nullable final Handler meteringHandler) {
    this.meteringHandler = meteringHandler;
  }

  @Nonnull
  @Override
  public Response handle(@Nonnull final Context context) throws Exception {
    Stopwatch watch = Stopwatch.createStarted();

    try {
      if (meteringHandler != null) {
        context.insertHandler(meteringHandler);
      }
      return context.proceed();
    }
    finally {
      String elapsed = watch.toString();
      context.getAttributes().set(ELAPSED_KEY, elapsed);
      log.trace("Timing: {}", elapsed);
    }
  }
}
