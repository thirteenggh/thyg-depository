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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests {@link PerformanceDataIO}
 */
public class PerformanceDataIOTest
    extends TestSupport
{
  @Rule
  public TestName name = new TestName();

  private File jsonData;

  @Before
  public void setJsonFileLocation() throws IOException {
    jsonData = File.createTempFile(name.getMethodName(), "json");
    jsonData.delete();// We just need a location, not an empty file to confuse the loader.
  }

  @Test
  public void roundTrip() throws Exception {
    final PerformanceData perfData = PerformanceDataIO.loadTestData(jsonData);

    final PerformanceTestSeries roundTrip = perfData.findTestResult("roundTrip");
    roundTrip.addResults(1, new PerformanceRunResult(1, 2, 60, true));

    PerformanceDataIO.saveTestData(perfData, jsonData);

    final PerformanceData loaded = PerformanceDataIO.loadTestData(jsonData);

    final PerformanceTestSeries testResult = loaded.findTestResult("roundTrip");
    final PerformanceRunResult result = testResult.getResult(1);

    assertThat(result.getRequestsCompleted(), is((int) 1));
    assertThat(result.getRequestsIncomplete(), is((int) 2));
    assertThat(result.getTestDurationSeconds(), is((int) 60));
    assertThat(result.isExceptionThrown(), is(true));
  }
}
