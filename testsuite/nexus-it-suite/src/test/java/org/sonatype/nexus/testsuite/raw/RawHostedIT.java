package org.sonatype.nexus.testsuite.raw;

import java.io.File;
import java.time.Duration;
import java.util.function.BiConsumer;

import javax.inject.Inject;

import org.sonatype.nexus.content.testsuite.groups.OrientAndSQLTestGroup;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.capability.GlobalRepositorySettings;
import org.sonatype.nexus.testsuite.testsupport.fixtures.LastDownloadedIntervalRule;
import org.sonatype.nexus.testsuite.testsupport.raw.RawClient;
import org.sonatype.nexus.testsuite.testsupport.raw.RawITSupport;

import org.apache.http.HttpResponse;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static com.google.common.io.Files.toByteArray;
import static java.lang.Thread.sleep;
import static org.apache.http.entity.ContentType.TEXT_PLAIN;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.sonatype.nexus.repository.http.HttpStatus.BAD_REQUEST;
import static org.sonatype.nexus.repository.http.HttpStatus.CREATED;
import static org.sonatype.nexus.repository.http.HttpStatus.OK;
import static org.sonatype.nexus.testsuite.testsupport.FormatClientSupport.bytes;
import static org.sonatype.nexus.testsuite.testsupport.FormatClientSupport.status;

/**
 * IT for hosted raw repositories
 */
@Category(OrientAndSQLTestGroup.class)
public class RawHostedIT
    extends RawITSupport
{
  public static final String HOSTED_REPO = "raw-test-hosted";

  public static final String TEST_CONTENT = "alphabet.txt";

  @Inject
  private GlobalRepositorySettings repositorySettings;

  @Rule
  public LastDownloadedIntervalRule lastDownloadedRule = new LastDownloadedIntervalRule(() -> repositorySettings);

  private RawClient rawClient;

  @Before
  public void createHostedRepository() throws Exception {
    rawClient = rawClient(repos.createRawHosted(HOSTED_REPO));
  }

  @SuppressWarnings("java:S2699") // sonar isn't detecting nested assertions
  @Test
  public void uploadAndDownload() throws Exception {
    uploadAndDownload(rawClient, TEST_CONTENT);
  }

  @SuppressWarnings("java:S2699") // sonar isn't detecting nested assertions
  @Test
  public void redeploy() throws Exception {
    uploadAndDownload(rawClient, TEST_CONTENT);
    uploadAndDownload(rawClient, TEST_CONTENT);
  }

  @Test
  public void failWhenRedeployNotAllowed() throws Exception {
    rawClient = rawClient(repos.createRawHosted(testName.getMethodName(), "ALLOW_ONCE"));

    File testFile = resolveTestFile(TEST_CONTENT);

    assertThat(rawClient.put(TEST_CONTENT, TEXT_PLAIN, testFile), is(CREATED));

    assertThat(rawClient.put(TEST_CONTENT, TEXT_PLAIN, testFile), is(BAD_REQUEST));
  }

  @Test
  public void setLastDownloadOnGetNotPut() throws Exception {
    Repository repository = repos.createRawHosted(testName.getMethodName(), "ALLOW_ONCE");

    rawClient = rawClient(repository);

    File testFile = resolveTestFile(TEST_CONTENT);

    assertThat(rawClient.put(TEST_CONTENT, TEXT_PLAIN, testFile), is(CREATED));
    assertThat(getLastDownloadedTime(repository, testFile.getName()), is(equalTo(null)));

    HttpResponse response = rawClient.get(TEST_CONTENT);

    assertThat(status(response), is(OK));
    assertThat(bytes(response), is(toByteArray(testFile)));
    assertThat(getLastDownloadedTime(repository, testFile.getName()).isBeforeNow(), is(equalTo(true)));
  }

  @Test
  public void lastDownloadedIsUpdatedWhenFrequencyConfigured() throws Exception {
    lastDownloadedRule.setLastDownloadedInterval(Duration.ofSeconds(1));

    verifyLastDownloadedTime((newDate, initialDate) -> assertThat(newDate, is(greaterThan(initialDate))));
  }

  @Test
  public void lastDownloadedIsNotUpdatedWhenFrequencyNotExceeded() throws Exception {
    lastDownloadedRule.setLastDownloadedInterval(Duration.ofSeconds(10));

    verifyLastDownloadedTime((newDate, initialDate) -> assertThat(newDate, is(equalTo(initialDate))));
  }

  private void verifyLastDownloadedTime(final BiConsumer<DateTime, DateTime> matcher) throws Exception {
    Repository repository = repos.createRawHosted(testName.getMethodName(), "ALLOW_ONCE");

    RawClient rawClient = rawClient(repository);

    File testFile = resolveTestFile(TEST_CONTENT);
    assertThat(rawClient.put(TEST_CONTENT, TEXT_PLAIN, testFile), is(CREATED));

    rawClient.get(TEST_CONTENT);
    DateTime firstLastDownloadedTime = getLastDownloadedTime(repository, testFile.getName());

    sleep(2000);

    rawClient.get(TEST_CONTENT);
    DateTime newLastDownloadedTime = getLastDownloadedTime(repository, testFile.getName());

    matcher.accept(newLastDownloadedTime, firstLastDownloadedTime);
  }
}
