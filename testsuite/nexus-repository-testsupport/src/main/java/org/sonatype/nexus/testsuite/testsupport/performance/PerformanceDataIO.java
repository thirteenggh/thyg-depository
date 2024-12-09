package org.sonatype.nexus.testsuite.testsupport.performance;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Loads and saves performance data as JSON.
 */
public class PerformanceDataIO
{
  private PerformanceDataIO() {
    // empty
  }

  /**
   * Loads performance test data from the specified file if it exists, otherwise returns an empty data set.
   */
  public static PerformanceData loadTestData(final File datafile) throws IOException {
    final ObjectMapper mapper = new ObjectMapper();
    if (datafile.exists()) {
      return mapper.readValue(datafile, PerformanceData.class);
    }
    return new PerformanceData();
  }

  /**
   * Overwrites the provided datafile with json output representing the suite results.
   */
  public static void saveTestData(final PerformanceData results, final File datafile) throws IOException {
    final ObjectMapper mapper = new ObjectMapper();
    mapper.writeValue(datafile, results);
  }
}
