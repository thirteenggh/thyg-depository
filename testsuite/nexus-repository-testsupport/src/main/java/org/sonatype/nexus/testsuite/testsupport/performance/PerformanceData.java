package org.sonatype.nexus.testsuite.testsupport.performance;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Results from an entire suite of performance tests.
 */
public class PerformanceData
{
  @JsonProperty
  private Map<String, PerformanceTestSeries> tests = new HashMap<>();

  public PerformanceTestSeries findTestResult(String testName) {
    PerformanceTestSeries result = tests.get(testName);
    if (result == null) {
      result = new PerformanceTestSeries(testName);
      tests.put(testName, result);
    }
    return result;
  }

  public Map<String, PerformanceTestSeries> getTests() {
    return tests;
  }

  /**
   * Get the superset of all thread counts for which tests were done.
   */
  @JsonIgnore
  public SortedSet<Integer> getThreadCounts() {
    final TreeSet<Integer> counts = new TreeSet<>();
    for (PerformanceTestSeries test : tests.values()) {
      counts.addAll(test.getResultsByThreadCount().keySet());
    }
    return counts;
  }

  /**
   * Results for a single type of load generation (such as maven2-hosted).
   */
  public static class PerformanceTestSeries
  {
    @JsonProperty
    private final String testName;

    // Results for varying numbers of threads
    @JsonProperty
    private final Map<Integer, PerformanceRunResult> resultsByThreadCount = new HashMap<>();

    @JsonCreator
    public PerformanceTestSeries(@JsonProperty("testName") final String testName) {
      this.testName = checkNotNull(testName);
    }

    public void addResults(final int threads, final PerformanceRunResult results) {
      resultsByThreadCount.put(threads, results);
    }

    public PerformanceRunResult getResult(final int threadCount) {
      return resultsByThreadCount.get(threadCount);
    }

    public Map<Integer, PerformanceRunResult> getResultsByThreadCount() {
      return resultsByThreadCount;
    }
  }

  /**
   * Results for a single load type for a particular number of client threads.
   */
  public static class PerformanceRunResult
  {
    private final int requestsCompleted;

    private final int requestsIncomplete;

    private final int testDurationSeconds;

    private final boolean exceptionThrown;

    @JsonCreator
    public PerformanceRunResult(@JsonProperty("requestsCompleted") final int requestsCompleted,
                                @JsonProperty("requestsIncomplete") final int requestsIncomplete,
                                @JsonProperty("durationSeconds") final int testDurationSeconds,
                                @JsonProperty("exceptionThrown") final boolean exceptionThrown)
    {
      this.requestsCompleted = requestsCompleted;
      this.requestsIncomplete = requestsIncomplete;
      this.testDurationSeconds = testDurationSeconds;
      this.exceptionThrown = exceptionThrown;
    }

    public int getRequestsCompleted() {
      return requestsCompleted;
    }

    public int getRequestsIncomplete() {
      return requestsIncomplete;
    }

    public boolean isExceptionThrown() {
      return exceptionThrown;
    }

    public int getTestDurationSeconds() {
      return testDurationSeconds;
    }
  }
}
