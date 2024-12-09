package org.sonatype.nexus.content.testsupport.helm;

import java.net.URL;

import javax.annotation.Nonnull;

import org.sonatype.nexus.content.testsupport.NexusITSupport;
import org.sonatype.nexus.content.testsupport.fixtures.RepositoryRule;
import org.sonatype.nexus.pax.exam.NexusPaxExamSupport;
import org.sonatype.nexus.repository.Repository;

import org.junit.Rule;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;

public class HelmContentITSupport
    extends NexusITSupport
{
  public static final String MONGO_PKG_NAME = "mongodb";

  public static final String YAML_NAME = "index";

  public static final String MONGO_PKG_VERSION_600 = "6.0.0";

  public static final String MONGO_PKG_VERSION_728 = "7.2.8";

  public static final String TGZ_EXT = ".tgz";

  public static final String YAML_EXT = ".yaml";

  public static final String MONGO_PKG_FILE_NAME_600_TGZ = format("%s-%s%s",
      MONGO_PKG_NAME, MONGO_PKG_VERSION_600, TGZ_EXT);

  public static final String MONGO_PKG_FILE_NAME_728_TGZ = format("%s-%s%s",
      MONGO_PKG_NAME, MONGO_PKG_VERSION_728, TGZ_EXT);

  public static final String CONTENT_TYPE_TGZ = "application/x-tgz";

  public static final String CONTENT_TYPE_YAML = "text/x-yaml";

  public static final String YAML_FILE_NAME = String.format("%s%s", YAML_NAME, YAML_EXT);

  @Rule
  public RepositoryRule repos = createRepositoryRule();

  protected RepositoryRule createRepositoryRule() {
    return new RepositoryRule(() -> repositoryManager);
  }

  public HelmContentITSupport() {
    testData.addDirectory(NexusPaxExamSupport.resolveBaseFile("target/it-resources/helm"));
  }

  @Nonnull
  protected URL repositoryBaseUrl(Repository repository) {
    return resolveUrl(this.nexusUrl, "/repository/" + repository.getName() + "/");
  }

  @Nonnull
  protected HelmClient helmClient(final Repository repository) throws Exception {
    checkNotNull(repository);
    final URL repositoryUrl = repositoryBaseUrl(repository);

    return new HelmClient(
        clientBuilder(repositoryUrl).build(),
        clientContext(),
        repositoryUrl.toURI()
    );
  }
}
