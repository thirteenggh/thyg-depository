package org.sonatype.nexus.blobstore.quota

import org.sonatype.nexus.blobstore.MockBlobStoreConfiguration
import org.sonatype.nexus.blobstore.api.BlobStore
import org.sonatype.nexus.blobstore.api.BlobStoreConfiguration

import org.slf4j.Logger
import spock.lang.Specification
import spock.lang.Unroll

import static org.sonatype.nexus.blobstore.quota.BlobStoreQuotaSupport.LIMIT_KEY
import static org.sonatype.nexus.blobstore.quota.BlobStoreQuotaSupport.ROOT_KEY
import static org.sonatype.nexus.blobstore.quota.BlobStoreQuotaSupport.getLimit
import static org.sonatype.nexus.blobstore.quota.BlobStoreQuotaSupport.quotaCheckJob

class BlobStoreQuotaSupportTest
    extends Specification
{
  @Unroll
  def 'Get Limit handles numbers properly'() {
    when:
      BlobStoreConfiguration config = new MockBlobStoreConfiguration()
      config.attributes(ROOT_KEY).set(LIMIT_KEY, value)
      def result = getLimit(config)

    then:
      result == number

    where:
      value || number
      1     || 1
      0     || 0
      -1    || -1
  }

  @Unroll
  def 'Get Limit handles error cases'() {
    when:
      BlobStoreConfiguration config = new MockBlobStoreConfiguration()
      config.attributes(ROOT_KEY).set(LIMIT_KEY, value)
      getLimit(config)
    then: 'blobstore fails to start'
      thrown(IllegalArgumentException)

    where:
      value | _
      null  | _
  }

  def 'passed quota logs nothing'() {
    given:
      def msg = "msg"
      def blobStore = Mock(BlobStore)
      def quotaService = Mock(BlobStoreQuotaService)
      def logger = Mock(Logger)
      def result = new BlobStoreQuotaResult(false, "name", msg)
      quotaService.checkQuota(blobStore) >> result
    when:
      quotaCheckJob(blobStore, quotaService, logger)
    then:
      0 * logger.error(*_)
      0 * logger.warn(msg)
      notThrown(Exception)
  }

  def 'Failed quota logs result'(){
    given:
      def msg = "msg"
      def blobStore = Mock(BlobStore)
      def quotaService = Mock(BlobStoreQuotaService)
      def logger = Mock(Logger)
      def result = new BlobStoreQuotaResult(true, "name", msg)
      quotaService.checkQuota(blobStore) >> result
    when:
      quotaCheckJob(blobStore, quotaService, logger)
    then:
      0 * logger.error(*_)
      1 * logger.warn(msg)
      notThrown(Exception)
  }

  def 'quota check job exceptions are caught'() {
    given:
      def msg = "msg"
      def blobStore = Mock(BlobStore)
      def quotaService = Mock(BlobStoreQuotaService)
      def config = Mock(BlobStoreConfiguration)
      def logger = Mock(Logger)
      blobStore.getBlobStoreConfiguration() >> config
      config.getName() >> 'testConfig'
      quotaService.checkQuota(blobStore) >> { throw new Exception() }
    when:
      quotaCheckJob(blobStore, quotaService, logger)
    then:
      1 * logger.error(*_)
      0 * logger.warn(msg)
      notThrown(Exception)
  }
}
