package org.sonatype.nexus.content.testsupport.raw;

import java.io.File;
import java.net.URL;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import org.sonatype.nexus.content.testsupport.FormatClientSupport;
import org.sonatype.nexus.content.testsupport.NexusITSupport;
import org.sonatype.nexus.content.testsupport.fixtures.RepositoryRule;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.http.HttpStatus;
import org.sonatype.nexus.repository.manager.RepositoryManager;

import com.google.common.io.Files;
import org.apache.http.entity.ContentType;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Rule;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RawITSupport
    extends NexusITSupport
{
  protected static final String SLASH_REPO_SLASH = "/repository/";

  @Inject
  protected RepositoryManager repositoryManager;

  @Rule
  public RepositoryRule repos = createRepositoryRule();

  public RawITSupport() {
    testData.addDirectory(resolveBaseFile("target/it-resources/raw"));
  }

  protected RepositoryRule createRepositoryRule() {
    return new RepositoryRule(() -> repositoryManager);
  }

  @Nonnull
  protected URL repositoryBaseUrl(final Repository repository) {
    return resolveUrl(nexusUrl, SLASH_REPO_SLASH + repository.getName() + "/");
  }

  @Nonnull
  protected RawClient rawClient(final Repository repository) throws Exception {
    checkNotNull(repository);
    return rawClient(repositoryBaseUrl(repository));
  }
  protected RawClient rawClient(final URL repositoryUrl) throws Exception {
    return new RawClient(
        clientBuilder(repositoryUrl).build(),
        clientContext(),
        repositoryUrl.toURI()
    );
  }

  protected void uploadAndDownload(RawClient rawClient, String file) throws Exception {
    final File testFile = resolveTestFile(file);
    final int response = rawClient.put(file, ContentType.TEXT_PLAIN, testFile);
    MatcherAssert.assertThat(response, Matchers.is(HttpStatus.CREATED));

    MatcherAssert.assertThat(FormatClientSupport.bytes(rawClient.get(file)), is(Files.toByteArray(testFile)));

    MatcherAssert.assertThat(FormatClientSupport.status(rawClient.delete(file)), Matchers.is(HttpStatus.NO_CONTENT));

    assertThat("content should be deleted", FormatClientSupport.status(rawClient.get(file)), Matchers.is(HttpStatus.NOT_FOUND));
  }
}
