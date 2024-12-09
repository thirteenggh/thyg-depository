package org.sonatype.nexus.siesta;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sonatype.nexus.rest.Component;
import org.sonatype.nexus.rest.Resource;

import org.eclipse.sisu.BeanEntry;
import org.jboss.resteasy.core.Dispatcher;

/**
 * Siesta {@link Component} (and {@link Resource} container abstraction.
 *
 * @since 3.0
 */
public interface ComponentContainer
{
  void init(final ServletConfig config) throws ServletException;

  void service(final HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

  void destroy();

  void addComponent(BeanEntry<?,?> entry) throws Exception;

  void removeComponent(BeanEntry<?,?> entry) throws Exception;

  Dispatcher getDispatcher();
}
