
package org.sonatype.nexus.repository.npm.internal

import org.sonatype.nexus.repository.view.Context

import spock.lang.Specification

class NpmPingHandlerTest
    extends Specification
{

  def 'it will respond with an empty object when invoked'() {
    given:
      def subject = new NpmPingHandler()
    when:
      def response = subject.handle(Mock(Context))
    then:
      response.getPayload().openInputStream().text == '{}'
  }
}
