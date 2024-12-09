package org.sonatype.nexus.repository.rest.api

import org.sonatype.nexus.common.collect.NestedAttributesMap
import org.sonatype.nexus.repository.Format
import org.sonatype.nexus.repository.Repository
import org.sonatype.nexus.repository.config.Configuration
import org.sonatype.nexus.repository.types.GroupType
import org.sonatype.nexus.repository.types.HostedType
import org.sonatype.nexus.repository.types.ProxyType

import spock.lang.Specification
import spock.lang.Unroll

class RepositoryXOTest
    extends Specification
{
  @Unroll
  def 'It will convert a Repository to a RepositoryXO'() {
    given:
      def repository = Mock(Repository) {
        getName() >> name
        getFormat() >> format
        getType() >> type
        getUrl() >> url
        getConfiguration() >> configuration(type.value, attributes)
      }
    when:
      def repositoryXO = RepositoryXO.fromRepository(repository)
    then:
      repositoryXO.name == name
      repositoryXO.format == expectedFormat
      repositoryXO.type == expectedType
      repositoryXO.url == url
      repositoryXO.attributes == expectedAttributes

    where:
      name | format          | expectedFormat | type             | expectedType | url | attributes         | expectedAttributes
      'x'  | format('npm')   | 'npm'          | new ProxyType()  | 'proxy'      | 'u' | [remoteUrl: 'url'] | [proxy: [remoteUrl: 'url']]
      'y'  | format('maven') | 'maven'        | new HostedType() | 'hosted'     | 'u' | [remoteUrl: 'foo'] | [:]
      'z'  | format('nuget') | 'nuget'        | new GroupType()  | 'group'      | 'u' | [remoteUrl: 'foo'] | [:]
  }

  private Format format(value) {
    Mock(Format) {
      getValue() >> value
    }
  }

  private Configuration configuration(type, value) {
    Mock(Configuration) {
      attributes() >> [(type): value]
      attributes(type) >> new NestedAttributesMap(type, value)
    }
  }
}
