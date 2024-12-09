package org.sonatype.nexus.coreui.internal.content;

import org.sonatype.nexus.repository.security.RepositorySelector;
import org.sonatype.nexus.selector.CselSelector;

/**
 * @since 3.29
 */
public class SelectorPreviewRequest
{
  private String repository = RepositorySelector.ALL;
  private String type = CselSelector.TYPE;
  private String expression;

  public String getRepository() {
    return repository;
  }

  public void setRepository(final String repository) {
    this.repository = repository;
  }

  public String getType() {
    return type;
  }

  public void setType(final String type) {
    this.type = type;
  }

  public String getExpression() {
    return expression;
  }

  public void setExpression(final String expression) {
    this.expression = expression;
  }
}
