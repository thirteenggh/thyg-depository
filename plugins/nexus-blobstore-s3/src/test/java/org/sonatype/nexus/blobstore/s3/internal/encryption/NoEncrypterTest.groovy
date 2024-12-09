package org.sonatype.nexus.blobstore.s3.internal.encryption

import com.amazonaws.services.s3.model.AbstractPutObjectRequest
import com.amazonaws.services.s3.model.CopyObjectRequest
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest
import spock.lang.Specification
import spock.lang.Unroll

class NoEncrypterTest
    extends Specification
{

  NoEncrypter noEncrypter = new NoEncrypter()

  @Unroll
  def 'NoEncrypter does nothing to \'#request\''() {
    given: 'a request to s3'
      def request = Mock(requestType)

    when: 'you try to encrypt a request'
      noEncrypter.addEncryption(request)

    then: 'nothing happens'
      0 * request./.*/

    where:
      requestType << [
          InitiateMultipartUploadRequest,
          AbstractPutObjectRequest,
          CopyObjectRequest
      ]
  }
}
