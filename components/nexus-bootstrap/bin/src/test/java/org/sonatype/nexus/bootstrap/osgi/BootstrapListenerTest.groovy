package org.sonatype.nexus.bootstrap.osgi

import java.nio.file.Path

import spock.lang.Specification
import spock.lang.Unroll

class BootstrapListenerTest
    extends Specification
{
  BootstrapListener underTest = Spy(BootstrapListener)

  Path workDirPath = Mock(Path)

  File proEditionMarker = Mock(File)

  @Unroll
  def 'hasLoadAsOss: #hasLoadAsOss, loadAsOss: #loadAsOss, edition_pro: #edition_pro, clustered: #clustered, nullFileLic: #nullFileLic, nullPrefLic: #nullPrefLic'()
  {
    when:
      underTest.hasNexusLoadAsOSS() >> hasLoadAsOss
      underTest.getProEditionMarker(workDirPath) >> proEditionMarker
      underTest.isNexusLoadAsOSS() >> loadAsOss
      underTest.isNexusClustered() >> clustered
      underTest.isNullNexusLicenseFile() >> nullFileLic
      underTest.isNullJavaPrefLicense() >> nullPrefLic
      proEditionMarker.exists() >> edition_pro

    then:
      underTest.shouldSwitchToOss(workDirPath) == is_oss

    where:
      hasLoadAsOss | loadAsOss | edition_pro | clustered | nullFileLic | nullPrefLic | is_oss
      // if nexus.loadAsOSS has a value and it's true then is_oss == true
      true         | true      | false       | false     | true        | true        | true
      true         | true      | true        | false     | true        | true        | true
      true         | true      | false       | true      | true        | true        | true
      true         | true      | true        | true      | true        | true        | true
      true         | true      | false       | false     | false       | true        | true
      true         | true      | true        | false     | false       | true        | true
      true         | true      | false       | true      | false       | true        | true
      true         | true      | true        | true      | false       | true        | true
      true         | true      | false       | false     | true        | false       | true
      true         | true      | true        | false     | true        | false       | true
      true         | true      | false       | true      | true        | false       | true
      true         | true      | true        | true      | true        | false       | true
      true         | true      | false       | false     | false       | false       | true
      true         | true      | true        | false     | false       | false       | true
      true         | true      | false       | true      | false       | false       | true
      true         | true      | true        | true      | false       | false       | true
      // if nexus.loadAsOSS has a value and it's false then is_oss == false
      true         | false     | true        | false     | true        | true        | false
      true         | false     | false       | false     | true        | true        | false
      true         | false     | true        | true      | true        | true        | false
      true         | false     | false       | true      | true        | true        | false
      true         | false     | true        | false     | false       | true        | false
      true         | false     | false       | false     | false       | true        | false
      true         | false     | true        | true      | false       | true        | false
      true         | false     | false       | true      | false       | true        | false
      true         | false     | true        | false     | true        | false       | false
      true         | false     | false       | false     | true        | false       | false
      true         | false     | true        | true      | true        | false       | false
      true         | false     | false       | true      | true        | false       | false
      true         | false     | true        | false     | false       | false       | false
      true         | false     | false       | false     | false       | false       | false
      true         | false     | true        | true      | false       | false       | false
      true         | false     | false       | true      | false       | false       | false
      // if nexus.loadAsOss doesn't have a value
      false        | null      | false       | false     | true        | true        | true
      // edition_pro is present then is_oss = false
      false        | null      | true        | false     | true        | true        | false
      false        | null      | true        | true      | true        | true        | false
      false        | null      | true        | false     | false       | true        | false
      false        | null      | true        | true      | false       | true        | false
      false        | null      | true        | false     | true        | false       | false
      false        | null      | true        | true      | true        | false       | false
      false        | null      | true        | false     | false       | false       | false
      false        | null      | true        | true      | false       | false       | false
      // if clustered then is_oss = false
      false        | null      | false       | true      | true        | true        | false
      false        | null      | false       | true      | false       | true        | false
      false        | null      | false       | true      | true        | false       | false
      false        | null      | false       | true      | false       | false       | false
      // if nexus.licenseFile is not null then is_oss = false
      false        | null      | false       | false     | false       | true        | false
      false        | null      | false       | false     | false       | false       | false
      // if there is a license stored in javaprefs then is_oss = false
      false        | null      | false       | false     | true        | false       | false
  }
}
