package org.sonatype.nexus.testsuite.testsupport.performance;

import java.io.File;
import java.io.IOException;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.testsuite.testsupport.performance.PerformanceData.PerformanceRunResult;
import org.sonatype.nexus.testsuite.testsupport.performance.PerformanceData.PerformanceTestSeries;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

/**
 * Tests {@link PerformanceChart}
 */
public class PerformanceChartTest
    extends TestSupport
{
  @Rule
  public TestName name = new TestName();

  private File chartFile;

  @Before
  public void setJsonFileLocation() throws IOException {
    final File chartDir = util.resolveFile("target/test-tmp/" + getClass().getSimpleName());
    chartDir.mkdirs();
    chartFile = new File(chartDir, name.getMethodName() + ".html");
  }

  @Test
  public void writeChartSmokeTest() throws Exception {
    final PerformanceData perfData =  new PerformanceData();
    final PerformanceTestSeries data = perfData.findTestResult("sample");
    data.addResults(1, new PerformanceRunResult(1, 0, 60, true));
    data.addResults(2, new PerformanceRunResult(10, 0, 60, true));
    data.addResults(3, new PerformanceRunResult(100, 0, 60, true));

    PerformanceChart.writePerformanceReport(perfData, chartFile);
  }
}
