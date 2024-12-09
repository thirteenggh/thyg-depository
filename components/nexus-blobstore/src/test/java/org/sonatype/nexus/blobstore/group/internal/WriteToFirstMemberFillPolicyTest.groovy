package org.sonatype.nexus.blobstore.group.internal

import org.sonatype.nexus.blobstore.api.BlobStore
import org.sonatype.nexus.blobstore.api.BlobStoreConfiguration
import org.sonatype.nexus.blobstore.group.BlobStoreGroup

import spock.lang.Specification
import spock.lang.Unroll

/**
 * Tests {@link WriteToFirstMemberFillPolicy}.
 */
class WriteToFirstMemberFillPolicyTest
    extends Specification
{
  WriteToFirstMemberFillPolicy underTest = new WriteToFirstMemberFillPolicy()

  @Unroll
  def 'It will skip non available and non writable members'() {
    given: 'A group with 3 members'
      BlobStoreGroup blobStoreGroup = Mock() {
        getMembers() >> [
            mockMember('one', available, writable),
            mockMember('two', available, writable),
            mockMember('three', true, true),
        ]
      }
    when: 'the policy tries to select the blob store member'
      def blobStore = underTest.chooseBlobStore(blobStoreGroup, [:])
    then:
      blobStore.blobStoreConfiguration.name == name

    where:
      available | writable | name
      false     | false    | 'three'
      false     | true     | 'three'
      true      | false    | 'three'
      true      | true     | 'one'
  }

  private BlobStore mockMember(final String name, final boolean available, final boolean writable) {
    Mock(BlobStore) {
      isStorageAvailable() >> available
      isWritable() >> writable
      getBlobStoreConfiguration() >> Mock(BlobStoreConfiguration) { getName() >> name }
    }
  }
}
