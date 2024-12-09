package org.sonatype.nexus.repository.maven.internal;

import javax.annotation.Nullable;
import javax.inject.Inject;

import org.sonatype.nexus.common.template.TemplateHelper;
import org.sonatype.nexus.common.template.TemplateParameters;

/**
 * @since 3.8
 */
public class MavenPomGenerator
{
  private final TemplateHelper templateHelper;

  @Inject
  public MavenPomGenerator(final TemplateHelper templateHelper) {
    this.templateHelper = templateHelper;
  }

  public String generatePom(final String groupId,
                            final String artifactId,
                            final String version,
                            @Nullable final String packaging)
  {
    TemplateParameters params = templateHelper.parameters();
    params.set("groupId", groupId);
    params.set("artifactId", artifactId);
    params.set("version", version);
    params.set("packaging", packaging);
    return templateHelper.render(getClass().getResource("pom.vm"), params);
  }
}
