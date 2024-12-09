package org.sonatype.nexus.blobstore.s3.internal

import org.sonatype.nexus.blobstore.MockBlobStoreConfiguration

import spock.lang.Specification
import spock.lang.Unroll

/**
 * {@link AmazonS3FactoryTest} tests.
 */
class AmazonS3FactoryTest
    extends Specification
{

  AmazonS3Factory amazonS3Factory = new AmazonS3Factory(-1, false, '')

  def config = new MockBlobStoreConfiguration()

  def setup() {
    config.attributes = [s3: [bucket: 'mybucket']]
  }

  def 'Endpoint is set when provided in config'() {
    when: 'An endpoint and region are provided'
      config.attributes.s3.endpoint = 'http://localhost/'
      config.attributes.s3.region = 'us-west-2'
      def s3 = amazonS3Factory.create(config)

    then: 'The client endpoint is set'
      s3.endpoint == new URI('http://localhost/')
  }

  def 'Signing algorithm is set when provided in config'() {
    when: 'A custom signing algorithm is provided'
      config.attributes.s3.signertype = 'AWSS3V4SignerType'
      config.attributes.s3.region = 'us-west-2'
      def s3 = amazonS3Factory.create(config)

    then: 'The client signer override is set'
      s3.clientConfiguration.signerOverride == 'AWSS3V4SignerType'
  }

  @Unroll
  def 'Signer value of \'#signer\' does not override config value'() {
    when: 'A custom signing algorithm is provided'
      config.attributes.s3.region = 'us-west-2'
      config.attributes.s3.signertype = signer
      def s3 = amazonS3Factory.create(config)

    then: 'The client signer override is not set'
      s3.clientConfiguration.signerOverride == null

    where:
      signer << [null, '', 'DEFAULT']
  }

  def 'pathStyleAccess is set when provided in config'() {
    when: 'pathStyleAccess is provided'
      config.attributes.s3.region = 'us-west-2'
      config.attributes.s3.forcepathstyle = 'true'
      def s3 = amazonS3Factory.create(config)

    then: 'The bucket is in the path'
      s3.getUrl('bucket', 'key').path == '/bucket/key'
  }
}
