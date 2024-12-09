package org.sonatype.nexus.repository.pypi

import spock.lang.Specification
import spock.lang.Unroll

/**
 * {@link PyPiRestoreUtils} unit tests.
 */
class PyPiRestoreUtilTest
    extends Specification
{
  @Unroll
  def 'Extract #version from the provided #path'() {
    expect:
      PyPiRestoreUtil.extractVersionFromPath(path) == version
    where:
      path                                                       | version
      'foo/bar/aldryn-addons-0.1.tar.gz'                         | '0.1'
      'some/bogus/path/SQLObject-3.0.0a2dev_20151224-py2.6.egg'  | '3.0.0a2dev_20151224'
      'bad/apple/pyglet-1.2.4-py2-none-any.whl'                  | '1.2.4'
      'path/location/anyit.djattributes-0.2.5-py2.6.egg'         | '0.2.5'
      'barbwire/Twisted-16.1.1.tar.bz2'                          | '16.1.1'
      'barbwire/Twisted-16.1.1-cp27-none-win_amd64.whl'          | '16.1.1'
      'barbwire/Twisted-15.4.0.win-amd64-py2.7.exe'              | '15.4.0.win'
      'barbwire/Twisted-11.1.0.win32-py2.7.msi'                  | '11.1.0.win32'
      'bad/news/bears/name-version.foo'                          | 'version'
      'bad/news/bears/name-version'                              | 'version'
  }

  @Unroll
  def 'Determine from #path if it is an index'() {
    expect:
      PyPiRestoreUtil.isIndex(path) == isIndex
    where:
      path              | isIndex
      'simple/foo'      | true
      'simple/foo/'     | true
      'simple/'         | true
      'foo'             | false
      'foo/simple/bar'  | false
      'foo/simple/bar/' | false
      'foo/'            | false
  }
}
