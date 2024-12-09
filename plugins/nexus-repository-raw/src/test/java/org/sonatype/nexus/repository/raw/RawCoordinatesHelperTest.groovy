package org.sonatype.nexus.repository.raw

import spock.lang.Specification
import spock.lang.Unroll

/**
 * {@link RawCoordinatesHelper} tests.
 */
class RawCoordinatesHelperTest
    extends Specification
{
  @Unroll
  def 'group of #path is #expectedGroup'() {
    when: 'we get the group of a path'
      def group = RawCoordinatesHelper.getGroup(path)

    then: 'the group is as expected'
      group == expectedGroup

    where:
      path                           || expectedGroup
      '/foo/bar'                     || '/foo'
      'foo/bar'                      || '/foo'
      'foobar.txt'                   || '/'
      '/foobar.txt'                  || '/'
      '/some/long/involved/path.txt' || '/some/long/involved'
      'some/long/involved/path.txt'  || '/some/long/involved'
  }
}
