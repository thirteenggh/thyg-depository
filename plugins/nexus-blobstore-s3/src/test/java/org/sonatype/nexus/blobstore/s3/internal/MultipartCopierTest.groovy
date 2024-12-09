package org.sonatype.nexus.blobstore.s3.internal

import spock.lang.Specification

import org.sonatype.nexus.blobstore.api.BlobStoreException

import com.amazonaws.SdkClientException
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.CopyPartRequest
import com.amazonaws.services.s3.model.CopyPartResult
import com.amazonaws.services.s3.model.InitiateMultipartUploadResult
import com.amazonaws.services.s3.model.ObjectMetadata

/**
 * {@link MultipartCopier} tests.
 */
class MultipartCopierTest
    extends Specification
{

  def 'copy copies with the multipart api'() {
    given: 'A multipart copier'
      MultipartCopier multipartCopier = new MultipartCopier(100)
      AmazonS3 s3 = Mock()

    when: 'a copy is started'
      multipartCopier.copy(s3, 'bucketName', 'source', 'destination')

    then: 'the multipart api is called'
      1 * s3.initiateMultipartUpload(_) >> new InitiateMultipartUploadResult(uploadId: 'testupload')
      1 * s3.getObjectMetadata('bucketName', 'source') >> new ObjectMetadata().with { setContentLength(101); it }
      2 * s3.copyPart(_) >> new CopyPartResult()
      1 * s3.completeMultipartUpload(_)
      0 * s3.abortMultipartUpload(_)
  }

  def 'copy aborts multipart copys on error'() {
    given: 'A multipart copier'
      MultipartCopier multipartCopier = new MultipartCopier(100)
      AmazonS3 s3 = Mock()

    when: 'a copy is started'
      multipartCopier.copy(s3, 'bucketName', 'source', 'destination')

    then: 'the copy is aborted'
      thrown(BlobStoreException)
      1 * s3.initiateMultipartUpload(_) >> new InitiateMultipartUploadResult(uploadId: 'testupload')
      1 * s3.getObjectMetadata('bucketName', 'source') >> new ObjectMetadata().with { setContentLength(101); it }
      1 * s3.copyPart(_) >> { CopyPartRequest request -> throw new SdkClientException('') }
      1 * s3.abortMultipartUpload(_)
  }

  def 'copy splits parts'() {
    given: 'A multipart copier'
      MultipartCopier multipartCopier = new MultipartCopier(100)
      AmazonS3 s3 = Mock()

    when: 'a copy is started'
      multipartCopier.copy(s3, 'bucketName', 'source', 'destination')

    then: 'the copy is split into parts'
      1 * s3.initiateMultipartUpload(_) >> new InitiateMultipartUploadResult(uploadId: 'testupload')
      1 * s3.getObjectMetadata('bucketName', 'source') >> new ObjectMetadata().with { setContentLength(345); it }
      4 * s3.copyPart(_) >> new CopyPartResult()
      1 * s3.completeMultipartUpload(_)
      0 * s3.abortMultipartUpload(_)
  }

  def 'copy uses copyObject for small copys'() {
    given: 'A multipart copier'
      MultipartCopier multipartCopier = new MultipartCopier(100)
      AmazonS3 s3 = Mock()

    when: 'a copy of a small object is started'
      multipartCopier.copy(s3, 'bucketName', 'source', 'destination')

    then: 'the copyObject method is called'
      1 * s3.getObjectMetadata('bucketName', 'source') >> new ObjectMetadata().with { setContentLength(99); it }
      1 * s3.copyObject(_, _, _, _)
      0 * s3.initiateMultipartUpload(_)
  }
}
