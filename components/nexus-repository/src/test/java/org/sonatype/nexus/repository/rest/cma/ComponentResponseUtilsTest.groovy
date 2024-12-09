package org.sonatype.nexus.repository.rest.cma

import org.sonatype.nexus.repository.rest.cma.ComponentResponseUtils
import org.sonatype.nexus.repository.storage.DefaultComponent

import spock.lang.Specification

class ComponentResponseUtilsTest
    extends Specification
{
  def "a component with only a name has only that name in the return map"() {
    given: 'component only has name'
      def component = new DefaultComponent()
      component.name("name")

    when: 'method called'
      def result = ComponentResponseUtils.mapFor(component)

    then: 'map will only contain name'
      result.size() == 1
      result.containsKey("name")
  }

  def "a component with all attributes has all three entries in map"() {
    given: 'component is fully populated'
      def component = new DefaultComponent()
      component.name("name")
      component.group("group")
      component.version("1.0.0")

    when: 'method called'
      def result = ComponentResponseUtils.mapFor(component)

    then: 'map will contain all three entries'
      result.size() == 3
      result.containsKey("name")
      result.containsKey("group")
      result.containsKey("version")
  }
}
