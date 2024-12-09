package org.sonatype.nexus.repository.cocoapods.internal.pod;

import java.net.URI;

import org.sonatype.nexus.repository.cocoapods.internal.PathUtils;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @since 3.27
 */
@RunWith(JUnitParamsRunner.class)
public class PathUtilsTest
{
  @Test
  @Parameters(method = "provideGeneratePodFileNameParams")
  public void testGeneratePodFileName(
      final String name,
      final String version,
      final String downloadUri,
      final String nxrmPath)
  {
    assertThat(PathUtils.buildNxrmPodPath(name, version, URI.create(downloadUri)), is(nxrmPath));
  }

  private static Object[] provideGeneratePodFileNameParams() {
    return new Object[]{
        new Object[]{
            "mycheck888", "1.2.3", "https://api.github.com/repos/mycheck888/MyCheckWalletUI/tarball/1.2.3",
            "pods/mycheck888/1.2.3/1.2.3.tar.gz"
        },
        new Object[]{
            "mycheck888", "1.2.3", "https://api.github.com/repos/mycheck888/MyCheckWalletUI/tarball/",
            "pods/mycheck888/1.2.3/1.2.3.tar.gz"
        },
        new Object[]{
            "packName", "0.9.2", "https://bitbucket.org/jefrydagucci/asbaseiosproject/get/v0.9.2.tar.gz",
            "pods/packName/0.9.2/v0.9.2.tar.gz"
        },
        new Object[]{
            "packName", "0.9.2", "https://bitbucket.org/jefrydagucci/asbaseiosproject/get/master.tar.gz",
            "pods/packName/0.9.2/master.tar.gz"
        },
        new Object[]{
            "packName", "0.9.2", "https://gitlab.com/streethawk/sdks/streethawk-sdk-ios/-/archive/1.10.2/1.10.2.tar.gz",
            "pods/packName/0.9.2/1.10.2.tar.gz"
        },
        new Object[]{
            "packName", "0.9.2", "https://gitlab.com/streethawk/sdks/streethawk-sdk-ios/-/archive/master/master.tar.gz",
            "pods/packName/0.9.2/master.tar.gz"
        },
    };
  }
}
