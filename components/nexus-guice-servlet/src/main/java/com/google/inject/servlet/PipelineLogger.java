package com.google.inject.servlet;

import com.google.common.base.Strings;
import com.google.inject.spi.DefaultBindingTargetVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logs pipeline definitions, using padding to align them into columns.
 */
final class PipelineLogger
    extends DefaultBindingTargetVisitor<Object, String>
    implements ServletModuleTargetVisitor<Object, String>
{
  private static final String NL = System.getProperty("line.separator");

  private static final Logger log = LoggerFactory.getLogger(DynamicGuiceFilter.class);

  private static final int PADDING = 30;

  private static final PipelineLogger THIS = new PipelineLogger();

  public static void dump(FilterDefinition[] filterDefinitions) {
    if (log.isDebugEnabled()) {
      final StringBuilder buf = new StringBuilder("Updated filter definitions:");
      for (final FilterDefinition fd : filterDefinitions) {
        buf.append(NL).append(fd.acceptExtensionVisitor(PipelineLogger.THIS, null));
      }
      log.debug(buf.toString());
    }
  }

  public static void dump(ServletDefinition[] servletDefinitions) {
    if (log.isDebugEnabled()) {
      final StringBuilder buf = new StringBuilder("Updated servlet definitions:");
      for (final ServletDefinition sd : servletDefinitions) {
        buf.append(NL).append(sd.acceptExtensionVisitor(PipelineLogger.THIS, null));
      }
      log.debug(buf.toString());
    }
  }

  public String visit(LinkedFilterBinding binding) {
    return format(binding.getPattern(), binding.getLinkedKey().getTypeLiteral());
  }

  public String visit(InstanceFilterBinding binding) {
    return format(binding.getPattern(), binding.getFilterInstance().getClass());
  }

  public String visit(LinkedServletBinding binding) {
    return format(binding.getPattern(), binding.getLinkedKey().getTypeLiteral());
  }

  public String visit(InstanceServletBinding binding) {
    return format(binding.getPattern(), binding.getServletInstance().getClass());
  }

  private static String format(String pattern, Object element) {
    return Strings.padEnd(pattern, PADDING, ' ') + ' ' + element;
  }
}