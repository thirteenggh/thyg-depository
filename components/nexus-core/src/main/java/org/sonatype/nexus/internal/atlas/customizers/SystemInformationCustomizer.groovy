package org.sonatype.nexus.internal.atlas.customizers

import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

import org.sonatype.goodies.common.ComponentSupport
import org.sonatype.nexus.common.atlas.SystemInformationGenerator
import org.sonatype.nexus.supportzip.GeneratedContentSourceSupport
import org.sonatype.nexus.supportzip.SupportBundle
import org.sonatype.nexus.supportzip.SupportBundleCustomizer

import groovy.json.JsonOutput

import static com.google.common.base.Preconditions.checkNotNull
import static org.sonatype.nexus.supportzip.SupportBundle.ContentSource.Priority.REQUIRED
import static org.sonatype.nexus.supportzip.SupportBundle.ContentSource.Type.SYSINFO

/**
 * Adds system information report to support bundle.
 *
 * @since 2.7
 */
@Named
@Singleton
class SystemInformationCustomizer
    extends ComponentSupport
    implements SupportBundleCustomizer
{
  private final SystemInformationGenerator systemInformationGenerator

  @Inject
  SystemInformationCustomizer(final SystemInformationGenerator systemInformationGenerator) {
    this.systemInformationGenerator = checkNotNull(systemInformationGenerator)
  }

  @Override
  void customize(final SupportBundle supportBundle) {
    supportBundle << new GeneratedContentSourceSupport(SYSINFO, 'info/sysinfo.json', REQUIRED) {
      @Override
      protected void generate(final File file) {
        def report = systemInformationGenerator.report()
        JsonOutput.with {
          file.text = prettyPrint(toJson(report))
        }
      }
    }
  }
}
