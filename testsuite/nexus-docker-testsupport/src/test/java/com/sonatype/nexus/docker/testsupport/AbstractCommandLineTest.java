package com.sonatype.nexus.docker.testsupport;

import java.util.List;

import com.sonatype.nexus.docker.testsupport.framework.DockerContainerClient;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.junit.After;
import org.junit.Before;
import org.slf4j.LoggerFactory;

import static ch.qos.logback.classic.Level.INFO;
import static ch.qos.logback.classic.Level.TRACE;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

public abstract class AbstractCommandLineTest
{
  @Before
  public void onInitializeCommandLineTest() {
    setDockerContainerClientLogLevel(TRACE);
  }

  @After
  public void onTearDownCommandLineTest() {
    setDockerContainerClientLogLevel(INFO);
  }

  protected void assertToHaveMoreLinesThan(List<String> outputLines, int size) {
    assertThat(outputLines.size(), greaterThan(size));
  }

  protected void assertLastValue(List<String> outputLines, String lineValue) {
    assertThat(outputLines.get(outputLines.size() - 1), equalTo(lineValue));
  }

  protected void setDockerContainerClientLogLevel(Level level) {
    ((Logger) LoggerFactory.getLogger(DockerContainerClient.class)).setLevel(level);
  }
}
