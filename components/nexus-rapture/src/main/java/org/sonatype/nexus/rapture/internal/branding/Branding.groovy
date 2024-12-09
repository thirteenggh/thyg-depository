package org.sonatype.nexus.rapture.internal.branding

import java.util.regex.Matcher

import javax.annotation.Nullable
import javax.inject.Named
import javax.inject.Singleton

import org.sonatype.nexus.common.app.BaseUrlHolder
import org.sonatype.nexus.rapture.StateContributor

import groovy.transform.PackageScope

/**
 * Branding state contributor.
 *
 * @since 3.0
 */
@Named
@Singleton
class Branding
    implements StateContributor
{
  private BrandingCapabilityConfiguration config

  // FIXME: This will perform interpolation (when configured) each poll,
  // FIXME: ... but is needed due to interpolation of baseUrl which can change

  @Override
  Map<String, Object> getState() {
    if (config) {
      return ['branding': new BrandingXO(
          headerEnabled: config.headerEnabled,
          headerHtml: interpolate(config.headerHtml),
          footerEnabled: config.footerEnabled,
          footerHtml: interpolate(config.footerHtml)
      )]
    }
    return null
  }

  @PackageScope
  @Nullable
  String interpolate(@Nullable final String html) {
    if (html != null) {
      return html.replaceAll(Matcher.quoteReplacement('$baseUrl'), BaseUrlHolder.get())
    }
    return null
  }

  void set(final BrandingCapabilityConfiguration config) {
    this.config = config
  }

  void reset() {
    this.config = null
  }
}
