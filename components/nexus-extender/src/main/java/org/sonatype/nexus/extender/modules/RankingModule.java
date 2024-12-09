package org.sonatype.nexus.extender.modules;

import java.util.concurrent.atomic.AtomicInteger;

import org.sonatype.nexus.common.log.LogManager;

import com.google.inject.AbstractModule;
import org.eclipse.sisu.inject.DefaultRankingFunction;
import org.eclipse.sisu.inject.RankingFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides ranking policy that gives more recent plugins priority over older plugins.
 * 
 * @since 3.0
 */
public class RankingModule
    extends AbstractModule
{
  private final AtomicInteger rank = new AtomicInteger(1);

  @Override
  protected void configure() {
    bind(RankingFunction.class).toInstance(new DefaultRankingFunction(rank.incrementAndGet()));

    // TEMP: placeholder to satisfy a few types that expect to inject loggers
    bind(Logger.class).toInstance(LoggerFactory.getLogger(LogManager.class));
  }
}
