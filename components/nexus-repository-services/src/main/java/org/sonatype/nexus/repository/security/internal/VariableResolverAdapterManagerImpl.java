package org.sonatype.nexus.repository.security.internal;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.repository.security.VariableResolverAdapter;
import org.sonatype.nexus.repository.security.VariableResolverAdapterManager;

import com.google.common.annotations.VisibleForTesting;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Default implementation of {@link VariableResolverAdapterManager}.
 * 
 * @since 3.1
 */
@Named
@Singleton
public class VariableResolverAdapterManagerImpl
    extends ComponentSupport
    implements VariableResolverAdapterManager
{
  @VisibleForTesting
  static final String DEFAULT_ADAPTER_NAME = "simple";

  private final VariableResolverAdapter defaultAdapter;

  private final Map<String, VariableResolverAdapter> adaptersByFormat;

  @Inject
  public VariableResolverAdapterManagerImpl(final Map<String, VariableResolverAdapter> adaptersByFormat) {
    this.adaptersByFormat = checkNotNull(adaptersByFormat);
    this.defaultAdapter = checkNotNull(adaptersByFormat.get(DEFAULT_ADAPTER_NAME));
  }

  @Override
  public VariableResolverAdapter get(String format) {
    return adaptersByFormat.getOrDefault(format, defaultAdapter);
  }
}
