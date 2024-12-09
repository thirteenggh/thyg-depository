package org.sonatype.nexus.internal.selector;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.entity.EntityId;
import org.sonatype.nexus.common.event.EventManager;
import org.sonatype.nexus.datastore.ConfigStoreSupport;
import org.sonatype.nexus.datastore.api.DataSessionSupplier;
import org.sonatype.nexus.selector.SelectorConfiguration;
import org.sonatype.nexus.transaction.Transactional;

import com.google.common.collect.ImmutableList;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * MyBatis {@link SelectorConfigurationStore} implementation.
 *
 * @since 3.21
 */
@Named("mybatis")
@Singleton
public class SelectorConfigurationStoreImpl
    extends ConfigStoreSupport<SelectorConfigurationDAO>
    implements SelectorConfigurationStore
{
  private final EventManager eventManager;

  @Inject
  public SelectorConfigurationStoreImpl(final DataSessionSupplier sessionSupplier, final EventManager eventManager) {
    super(sessionSupplier);
    this.eventManager = checkNotNull(eventManager);
  }

  @Override
  public SelectorConfiguration newSelectorConfiguration() {
    return new SelectorConfigurationData();
  }

  @Transactional
  @Override
  public List<SelectorConfiguration> browse() {
    return ImmutableList.copyOf(dao().browse());
  }

  @Override
  public void create(final SelectorConfiguration configuration) {
    doCreate(configuration);
    postEvent(configuration);
  }

  @Transactional
  protected void doCreate(final SelectorConfiguration configuration) {
    dao().create((SelectorConfigurationData) configuration);
  }

  @Override
  public SelectorConfiguration read(final EntityId entityId) {
    throw new UnsupportedOperationException("Use getByName instead");
  }

  @Override
  public void update(final SelectorConfiguration configuration) {
    if (doUpdate(configuration)) {
      postEvent(configuration);
    }
  }

  @Transactional
  protected boolean doUpdate(final SelectorConfiguration configuration) {
    return dao().update((SelectorConfigurationData) configuration);
  }

  @Override
  public void delete(final SelectorConfiguration configuration) {
    if (doDelete(configuration)) {
      postEvent(configuration);
    }
  }

  @Transactional
  protected boolean doDelete(final SelectorConfiguration configuration) {
    return dao().delete(configuration.getName());
  }

  @Transactional
  @Override
  public SelectorConfiguration getByName(final String name) {
    return dao().read(name).orElse(null);
  }

  private void postEvent(final SelectorConfiguration configuration) {
    // trigger invalidation of SelectorManagerImpl caches
    eventManager.post(new SelectorConfigurationEvent()
    {
      @Override
      public boolean isLocal() {
        return true;
      }

      @Override
      public SelectorConfiguration getSelectorConfiguration() {
        return configuration;
      }
    });
  }
}
