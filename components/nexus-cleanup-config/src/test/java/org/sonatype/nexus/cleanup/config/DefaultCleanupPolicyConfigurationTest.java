package org.sonatype.nexus.cleanup.config;

import org.sonatype.goodies.testsupport.TestSupport;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.sonatype.nexus.cleanup.config.CleanupPolicyConstants.IS_PRERELEASE_KEY;
import static org.sonatype.nexus.cleanup.config.CleanupPolicyConstants.LAST_BLOB_UPDATED_KEY;
import static org.sonatype.nexus.cleanup.config.CleanupPolicyConstants.LAST_DOWNLOADED_KEY;

public class DefaultCleanupPolicyConfigurationTest
    extends TestSupport
{
  @Test
  public void allFieldsExceptReleasePrereleaseAreEnabled() throws Exception {
    DefaultCleanupPolicyConfiguration underTest = new DefaultCleanupPolicyConfiguration();

    assertThat(underTest.getConfiguration().get(LAST_BLOB_UPDATED_KEY), is(equalTo(true)));
    assertThat(underTest.getConfiguration().get(LAST_DOWNLOADED_KEY), is(equalTo(true)));
    assertThat(underTest.getConfiguration().get(IS_PRERELEASE_KEY), is(equalTo(false)));
  }
}
