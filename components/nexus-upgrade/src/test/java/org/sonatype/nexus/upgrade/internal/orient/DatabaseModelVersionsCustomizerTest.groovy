package org.sonatype.nexus.upgrade.internal.orient

import org.sonatype.nexus.supportzip.SupportBundle

import groovy.json.JsonOutput
import spock.lang.Specification
import spock.lang.Subject

class DatabaseModelVersionsCustomizerTest
    extends Specification
{
  UpgradeManager upgradeManager = Mock()

  @Subject
  DatabaseModelVersionsCustomizer upgradeVersionCustomizer = new DatabaseModelVersionsCustomizer(upgradeManager)

  def 'Model versions are generated to a file'() {
    given:
      SupportBundle bundle = new SupportBundle()
      final Map upgradeVersions = [foo: 'bar'].asImmutable()

    when:
      upgradeVersionCustomizer.customize(bundle)

    then:
      bundle.sources.size() == 1

    when: 'Preparing the bundle content'
      SupportBundle.ContentSource source = bundle.sources.get(0)
      source.prepare()

    then: 'It is generated from the map of known model versions'
      upgradeManager.latestKnownModelVersions() >> upgradeVersions
      source.content.text == JsonOutput.prettyPrint(JsonOutput.toJson(upgradeVersions))
  }
}
