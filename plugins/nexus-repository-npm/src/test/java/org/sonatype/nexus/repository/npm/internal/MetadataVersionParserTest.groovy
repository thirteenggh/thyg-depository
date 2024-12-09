package org.sonatype.nexus.repository.npm.internal


import spock.lang.Specification
import spock.lang.Unroll

class MetadataVersionParserTest
    extends Specification
{
  @Unroll
  def "It will extract all version numbers from the blob"() {
    given:
      def inputStream = new ByteArrayInputStream(json.bytes)
    when:
      def versions = MetadataVersionParser.readVersions(inputStream)
    then:
      versions == expectedVersions
    where:
      json                                                                 | expectedVersions
      '{"versions": {"1.1.1": {}, "1.2.1": {}, "1.3.1": {}}}'              | ['1.1.1', '1.2.1', '1.3.1']
      '{"id": "foo", "versions": {"1.1.1": {}}}'                           | ['1.1.1']
      '{"id": "foo", "versions": {"1.1.1": {}}, "other": "bar"}'           | ['1.1.1']
      '{"id": "foo", "versions": {"1.1.1": {}}, "other": {"bar": "baz"}}}' | ['1.1.1']
      '{"id": "foo", "versions": {}}'                                      | []
      '{"id": "foo", "versions": null}'                                    | []
      '{"id": "foo"}'                                                      | []
  }
}
