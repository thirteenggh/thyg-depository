package org.sonatype.nexus.repository.maven.tasks;

import java.io.IOException;

import javax.inject.Named;

import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.RepositoryTaskSupport;
import org.sonatype.nexus.repository.maven.MavenIndexFacet;
import org.sonatype.nexus.repository.maven.internal.Maven2Format;

/**
 * Maven 2 publish MI indexes task.
 *
 * @since 3.0
 */
@Named
public class PublishMavenIndexTask
    extends RepositoryTaskSupport
{
  @Override
  protected void execute(final Repository repository) {
    MavenIndexFacet mavenIndexFacet = repository.facet(MavenIndexFacet.class);
    try {
      mavenIndexFacet.publishIndex();
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected boolean appliesTo(final Repository repository) {
    return repository.getFormat().getValue().equals(Maven2Format.NAME);
  }

  @Override
  public String getMessage() {
    return "Publish Maven indexes of " + getRepositoryField();
  }
}
