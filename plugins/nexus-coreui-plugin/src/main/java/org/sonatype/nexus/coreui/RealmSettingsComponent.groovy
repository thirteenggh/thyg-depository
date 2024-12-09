package org.sonatype.nexus.coreui

import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import javax.validation.Valid
import javax.validation.constraints.NotNull

import org.sonatype.nexus.extdirect.DirectComponent
import org.sonatype.nexus.extdirect.DirectComponentSupport
import org.sonatype.nexus.security.realm.RealmConfiguration
import org.sonatype.nexus.security.realm.RealmManager
import org.sonatype.nexus.validation.Validate

import com.codahale.metrics.annotation.ExceptionMetered
import com.codahale.metrics.annotation.Timed
import com.google.inject.Key
import com.softwarementors.extjs.djn.config.annotations.DirectAction
import com.softwarementors.extjs.djn.config.annotations.DirectMethod
import org.apache.shiro.authz.annotation.RequiresAuthentication
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.apache.shiro.realm.Realm
import org.eclipse.sisu.inject.BeanLocator

/**
 * Realm Security Settings {@link DirectComponent}.
 *
 * @since 3.0
 */
@Named
@Singleton
@DirectAction(action = 'coreui_RealmSettings')
class RealmSettingsComponent
    extends DirectComponentSupport
{
  @Inject
  RealmManager realmManager

  @Inject
  BeanLocator beanLocator

  /**
   * Retrieves security realm settings.
   * @return security realm settings
   */
  @DirectMethod
  @Timed
  @ExceptionMetered
  @RequiresPermissions('nexus:settings:read')
  RealmSettingsXO read() {
    return new RealmSettingsXO(
        realms: realmManager.getConfiguration().realmNames
    )
  }

  /**
   * Retrieves realm types.
   * @return a list of realm types
   */
  @DirectMethod
  @Timed
  @ExceptionMetered
  @RequiresPermissions('nexus:settings:read')
  List<ReferenceXO> readRealmTypes() {
    beanLocator.locate(Key.get(Realm.class, Named.class)).collect { entry ->
      new ReferenceXO(
          id: entry.key.value,
          name: entry.description
      )
    }.sort { a, b -> a.name.compareToIgnoreCase(b.name) }
  }

  /**
   * Updates security realm settings.
   * @return updated security realm settings
   */
  @DirectMethod
  @Timed
  @ExceptionMetered
  @RequiresAuthentication
  @RequiresPermissions('nexus:settings:update')
  @Validate
  RealmSettingsXO update(final @NotNull @Valid RealmSettingsXO realmSettingsXO) {
    RealmConfiguration entity = realmManager.newEntity()
    entity.realmNames = realmSettingsXO.realms
    realmManager.configuration = entity
    return read()
  }
}
