package org.sonatype.nexus.blobstore

import spock.lang.Specification

class PerformanceLoggingInputStreamTest
    extends Specification
{
  def 'Reads and close are passed through to underlying InputStream'() {
    given:
      def source = Mock(InputStream)
      def logger = Mock(PerformanceLogger)
      def buffer1 = new byte[100]
      def buffer2 = new byte[100]
      def underTest = new PerformanceLoggingInputStream(source, logger)

    when:
      def i = underTest.read()
      def len1 = underTest.read(buffer1)
      def len2 = underTest.read(buffer1, 7, 29)
      underTest.close()

    then:
     i == 123
     len1 == 99
     len2 == 29

     1 * source.read() >> 123
     1 * source.read(buffer1, 0, 100) >> 99
     1 * source.read(buffer2, 7, 29) >> 29
     1 * source.close()
  }

  def 'Performance data is logged on close'() {
    given:
      def source = Mock(InputStream)
      def logger = Mock(PerformanceLogger)
      def underTest = new PerformanceLoggingInputStream(source, logger)

    when:
      underTest.close()

    then:
      1 * logger.logRead(0, _);
  }
}
