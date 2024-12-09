package org.sonatype.nexus.common.text

import spock.lang.Specification
import spock.lang.Unroll

import static java.lang.Math.pow
import static java.lang.Math.round

class UnitFormatterTest
    extends Specification
{
  @Unroll
  def 'Correct units for #value is #stringValue'() {
    when:
      def result = UnitFormatter.formatStorage(value)

    then:
      result == stringValue

    where:
      value                   || stringValue
      round(pow(10, 18))      || "1.00 EB"
      round(pow(10, 15))      || "1.00 PB"
      round(pow(10, 12))      || "1.00 TB"
      round(pow(10, 9))       || "1.00 GB"
      round(pow(10, 6))       || "1.00 MB"
      round(pow(10, 3))       || "1.00 KB"
      round(pow(10, 0))       || "1.00 B"
      0                       || "0.00 B"
      -1 * round(pow(10, 18)) || "-1.00 EB"
      -1 * round(pow(10, 15)) || "-1.00 PB"
      -1 * round(pow(10, 12)) || "-1.00 TB"
      -1 * round(pow(10, 9))  || "-1.00 GB"
      -1 * round(pow(10, 6))  || "-1.00 MB"
      -1 * round(pow(10, 3))  || "-1.00 KB"
      -1 * round(pow(10, 0))  || "-1.00 B"
  }

  @Unroll
  def 'Correct rounding for #value is #stringValue'() {
    when:
      def result = UnitFormatter.formatStorage(value)

    then:
      result == stringValue

    where:
      value                      || stringValue
      round(3.7 * pow(10, 18))   || "3.70 EB"
      round(3.4 * pow(10, 18))   || "3.40 EB"
      round(2.999 * pow(10, 18)) || "3.00 EB"
      round(2.444 * pow(10, 18)) || "2.44 EB"
  }
}
