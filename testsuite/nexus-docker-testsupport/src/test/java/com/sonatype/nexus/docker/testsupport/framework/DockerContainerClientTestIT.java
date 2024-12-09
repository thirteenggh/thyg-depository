package com.sonatype.nexus.docker.testsupport.framework;

import org.junit.After;
import org.junit.Test;

import static java.util.UUID.randomUUID;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DockerContainerClientTestIT
{
  private static final String IMAGE_HELLO_WORLD = "docker-all.repo.sonatype.com/hello-world";

  private static final String IMAGE_DOCKER = "docker-all.repo.sonatype.com/docker";

  private static final String IMAGE_CENTOS = "docker-all.repo.sonatype.com/centos";

  private DockerContainerClient underTest;

  @After
  public void cleanUp() {
    underTest.close();
  }

  @Test
  public void when_Pull_HelloWorld_Expect_Container_Exists() {
    underTest = new DockerContainerClient(IMAGE_HELLO_WORLD);
    assertTrue(underTest.pull().isPresent());
  }

  @Test
  public void when_Run_HelloWorld_Expect_Container_Exists() {
    underTest = new DockerContainerClient(IMAGE_HELLO_WORLD);
    assertTrue(underTest.run(null).isPresent());
  }

  @Test
  public void when_Exec_YumVersion_On_CentosLatest_Expect_Execution_To_Succeed() {
    underTest = new DockerContainerClient(IMAGE_CENTOS);
    assertTrue(underTest.exec("yum --version").isPresent());
  }

  @Test
  public void when_Exec_YumVersion_On_Centos_6_9_Expect_Execution_To_Succeed() {
    underTest = new DockerContainerClient(IMAGE_CENTOS + ":6.9");
    assertTrue(underTest.exec("yum --version").isPresent());
  }

  @Test
  public void when_Exec_DockerVersion_On_DockerLatest_Expect_Execution_To_Succeed() {
    underTest = new DockerContainerClient(IMAGE_DOCKER);
    assertTrue(underTest.exec("docker --version").isPresent());
  }

  @Test
  public void when_Exec_Pwd_On_UnknownImage_Expect_Execution_To_Fail() {
    underTest = new DockerContainerClient("unknown-image-" + randomUUID().toString());
    assertFalse(underTest.exec("pwd").isPresent());
  }
}
