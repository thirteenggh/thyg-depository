package com.google.inject.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.google.common.collect.Sets;
import com.google.inject.Key;
import org.eclipse.sisu.inject.BeanLocator;
import org.eclipse.sisu.wire.EntryListAdapter;

import static com.google.inject.servlet.DynamicServletPipeline.DUMMY_INJECTOR;

/**
 * Dynamic {@link FilterPipeline} that can update its sequence of filter definitions on-demand.
 * Includes patched methods from {@link ManagedFilterPipeline} where delegating isn't possible.
 */
@Singleton
// don't use @Named, keep as implicit JIT-binding
final class DynamicFilterPipeline
    extends ManagedFilterPipeline
{
  private final DynamicServletPipeline servletPipeline;

  private final BeanLocator locator;

  // dynamic list of definitions
  private final List<FilterDefinition> filterDefinitions;

  // stable cache of definitions
  private volatile FilterDefinition[] filterDefinitionCache = {};

  private volatile ServletContext servletContext;

  @Inject
  DynamicFilterPipeline(final DynamicServletPipeline servletPipeline, final BeanLocator locator) {
    super(DUMMY_INJECTOR, servletPipeline, null);

    this.servletPipeline = servletPipeline;
    this.locator = locator;

    try {
      // disable lazy init as we don't use it
      super.initPipeline(null /* unused */);
    }
    catch (final Exception e) {
      throw new IllegalStateException(e);
    }

    filterDefinitions = new EntryListAdapter<>(locator.locate(Key.get(FilterDefinition.class)));
  }

  public ServletContext getServletContext() {
    return servletContext;
  }

  public synchronized void refreshCache() {
    final Object[] snapshot = filterDefinitions.toArray();
    filterDefinitionCache = Arrays.copyOf(snapshot, snapshot.length, FilterDefinition[].class);
    PipelineLogger.dump(filterDefinitionCache);

    servletPipeline.refreshCache();
  }

  @Override
  public synchronized void initPipeline(final ServletContext context) throws ServletException {
    if (servletContext == null && context != null) {
      servletContext = context;

      // register trigger to update definitions as FilterPipeline bindings come and go
      locator.watch(Key.get(FilterPipeline.class), new FilterPipelineMediator(), this);
    }
  }

  @Override
  public void dispatch(ServletRequest request, ServletResponse response, FilterChain proceedingFilterChain)
      throws IOException, ServletException
  {
    new FilterChainInvocation(filterDefinitions(), servletPipeline, proceedingFilterChain).doFilter(
        withDispatcher(request, servletPipeline), response);
  }

  @Override
  public void destroyPipeline() {
    servletPipeline.destroy();

    Set<Filter> destroyedSoFar = Sets.newIdentityHashSet();
    for (FilterDefinition filterDefinition : filterDefinitions()) {
      filterDefinition.destroy(destroyedSoFar);
    }
  }

  private static ServletRequest withDispatcher(ServletRequest servletRequest,
      final DynamicServletPipeline servletPipeline)
  {
    if (!servletPipeline.hasServletsMapped()) {
      return servletRequest;
    }

    return new HttpServletRequestWrapper((HttpServletRequest) servletRequest)
    {
      @Override
      public RequestDispatcher getRequestDispatcher(String path) {
        final RequestDispatcher dispatcher = servletPipeline.getRequestDispatcher(path);
        return (null != dispatcher) ? dispatcher : super.getRequestDispatcher(path);
      }
    };
  }

  private FilterDefinition[] filterDefinitions() {
    return filterDefinitionCache;
  }
}
