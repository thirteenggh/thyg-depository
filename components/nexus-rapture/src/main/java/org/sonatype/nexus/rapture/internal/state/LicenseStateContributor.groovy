package org.sonatype.nexus.rapture.internal.state

import javax.annotation.Nullable
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

import org.sonatype.goodies.common.ComponentSupport
import org.sonatype.nexus.common.app.ApplicationLicense
import org.sonatype.nexus.common.app.ApplicationLicense.Attributes
import org.sonatype.nexus.rapture.StateContributor

import com.google.common.collect.ImmutableMap
import groovy.time.TimeCategory

import static com.google.common.base.Preconditions.checkNotNull

/**
 * Contributes {@code license} state.
 *
 * @since 3.0
 */
@Named
@Singleton
class LicenseStateContributor
    extends ComponentSupport
    implements StateContributor
{
  private static final String STATE_ID = "license"

  private final ApplicationLicense applicationLicense

  @Inject
  LicenseStateContributor(final ApplicationLicense applicationLicense) {
    this.applicationLicense = checkNotNull(applicationLicense)
  }

  @Nullable
  @Override
  Map<String, Object> getState() {
    return ImmutableMap.of(STATE_ID, calculateLicense())
  }

  private Object calculateLicense() {
    LicenseXO result = new LicenseXO()
    result.setRequired(applicationLicense.isRequired())
    result.setInstalled(applicationLicense.isInstalled())
    result.setValid(applicationLicense.isValid())
    Map<String, Object> attributes = applicationLicense.getAttributes()
    if (attributes && attributes.get(Attributes.EXPIRATION_DATE.getKey())) {
      use(TimeCategory) {
        def duration = attributes.get(Attributes.EXPIRATION_DATE.getKey()) - new Date()
        result.setDaysToExpiry(duration.days)
      }
    }
    if (attributes && attributes.get(Attributes.FEATURES.getKey())) {
      result.setFeatures(attributes.get(Attributes.FEATURES.getKey()) as List<String>)
    }
    else {
      result.setFeatures(Collections.emptyList())
    }
    return result
  }
}
