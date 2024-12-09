package org.sonatype.nexus.blobstore.s3.internal.encryption

import com.amazonaws.services.s3.model.AbstractPutObjectRequest
import com.amazonaws.services.s3.model.CopyObjectRequest
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest
import com.amazonaws.services.s3.model.SSEAwsKeyManagementParams
import spock.lang.Specification
import spock.lang.Unroll

class KMSEncrypterTest
    extends Specification
{

  @Unroll
  def 'Constructor properly handles kmsId:\'#kmsId\''() {
    when: 'you create a kms encrypter'
      new KMSEncrypter(Optional.ofNullable(kmsId))

    then: 'aws sdk is setup properly'
      noExceptionThrown()

    where:
        kmsId << [
          null,
          "",
          " ",
          "   ",
          "aProperKeyId"
      ]
  }

  @Unroll
  def 'Supplying no kms id adds the correct kms parameters to \'#request\''() {
    given: 'an encrypter without a selected key'
      def request = Mock(requestType)
      KMSEncrypter kmsEncrypter = new KMSEncrypter()

    when: 'you encrypt a request'
      kmsEncrypter.addEncryption(request)

    then: 'Correct params are set'
      1 * request.setSSEAwsKeyManagementParams(*_) >> { args ->
        final SSEAwsKeyManagementParams params = args[0]
        assert params.getAwsKmsKeyId() == null
      }

    where:
      requestType << [
          InitiateMultipartUploadRequest,
          AbstractPutObjectRequest,
          CopyObjectRequest
      ]
  }

  @Unroll
  def 'Adds the correct kms parameters with key ID to \'#request\''() {
    given: 'an encrypter with a kms key ID'
      def request = Mock(requestType)
      KMSEncrypter kmsEncrypter = new KMSEncrypter(Optional.of('FakeKeyId'))

    when: 'you encrypt a request'
      kmsEncrypter.addEncryption(request)

    then: 'Correct params are set'
      1* request.setSSEAwsKeyManagementParams(*_) >> { args ->
        final SSEAwsKeyManagementParams params = args[0]
        assert params.getAwsKmsKeyId().equals('FakeKeyId')
      }

    where:
      requestType << [
          InitiateMultipartUploadRequest,
          AbstractPutObjectRequest,
          CopyObjectRequest
      ]
  }
}
