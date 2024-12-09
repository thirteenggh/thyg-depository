package org.sonatype.nexus.repository.internal.blobstore.orient

import org.sonatype.nexus.blobstore.api.BlobStoreConfiguration

import spock.lang.Specification

class OrientBlobStoreConfigurationSpec
    extends Specification
{

  def 'copy works'() {
    when: 'a configuration is created'
      BlobStoreConfiguration source = new OrientBlobStoreConfiguration()
      source.setName('source')
      source.setType('test type')
      source.attributes('config key').set('foo', 'bar')

    and: 'a copy is made'
      BlobStoreConfiguration copy = source.copy('a copy')

    then: 'the high-level items in the copy should be intact'
      assert copy.name == 'a copy'
      assert copy.type == source.type

    and: 'the embedded attributes should be intact'
      assert copy.attributes['config key'] != null
      assert copy.attributes['config key']['foo'] == 'bar'
  }
}
