package org.sonatype.nexus.script.plugin.internal;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.datastore.ConfigStoreSupport;
import org.sonatype.nexus.datastore.api.DataSessionSupplier;
import org.sonatype.nexus.script.Script;
import org.sonatype.nexus.transaction.Transactional;

import com.google.common.collect.ImmutableList;

/**
 * MyBatis {@link ScriptStore} implementation.
 *
 * @since 3.21
 */
@Named("mybatis")
@Singleton
public class ScriptStoreImpl
    extends ConfigStoreSupport<ScriptDAO>
    implements ScriptStore
{
  @Inject
  public ScriptStoreImpl(final DataSessionSupplier sessionSupplier) {
    super(sessionSupplier);
  }

  @Override
  public Script newScript() {
    return new ScriptData();
  }

  @Transactional
  @Override
  public List<Script> list() {
    return ImmutableList.copyOf(dao().browse());
  }

  @Transactional
  @Override
  public Script get(final String name) {
    return dao().read(name).orElse(null);
  }

  @Transactional
  @Override
  public void create(final Script script) {
    dao().create((ScriptData) script);
  }

  @Transactional
  @Override
  public void update(final Script script) {
    dao().update((ScriptData) script);
  }

  @Transactional
  @Override
  public void delete(final Script script) {
    dao().delete(script.getName());
  }
}
