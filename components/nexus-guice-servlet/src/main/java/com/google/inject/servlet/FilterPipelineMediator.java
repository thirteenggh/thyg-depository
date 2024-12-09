package com.google.inject.servlet;

import java.lang.annotation.Annotation;

import org.eclipse.sisu.BeanEntry;
import org.eclipse.sisu.Mediator;

/**
 * Updates the associated {@link DynamicFilterPipeline} as {@link FilterPipeline} bindings come and go.
 */
final class FilterPipelineMediator
    implements Mediator<Annotation, FilterPipeline, DynamicFilterPipeline>
{
  public void add(final BeanEntry<Annotation, FilterPipeline> entry, final DynamicFilterPipeline watcher)
      throws Exception
  {
    // initialize pipeline before exposing via cache
    final FilterPipeline pipeline = entry.getValue();
    pipeline.initPipeline(watcher.getServletContext());
    watcher.refreshCache();
  }

  public void remove(final BeanEntry<Annotation, FilterPipeline> entry, final DynamicFilterPipeline watcher)
      throws Exception
  {
    // remove pipeline from cache before disposing
    final FilterPipeline pipeline = entry.getValue();
    watcher.refreshCache();
    pipeline.destroyPipeline();
  }
}
