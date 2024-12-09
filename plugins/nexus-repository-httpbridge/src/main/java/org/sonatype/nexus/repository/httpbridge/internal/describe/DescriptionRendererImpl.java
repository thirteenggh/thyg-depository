package org.sonatype.nexus.repository.httpbridge.internal.describe;

import java.net.URL;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.template.EscapeHelper;
import org.sonatype.nexus.common.template.TemplateHelper;
import org.sonatype.nexus.common.template.TemplateParameters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Default {@link DescriptionRenderer}.
 *
 * @since 3.0
 */
@Named
@Singleton
public class DescriptionRendererImpl
    implements DescriptionRenderer
{
  private static final String TEMPLATE_RESOURCE = "describeHtml.vm";

  private final TemplateHelper templateHelper;

  private final ObjectMapper objectMapper;

  private final URL template;

  @Inject
  public DescriptionRendererImpl(final TemplateHelper templateHelper) {
    this.templateHelper = checkNotNull(templateHelper);
    objectMapper = new ObjectMapper();
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    template = getClass().getResource(TEMPLATE_RESOURCE);
    checkNotNull(template);
  }

  @Override
  public String renderHtml(final Description description) {
    TemplateParameters params = templateHelper.parameters();
    params.setAll(description.getParameters());
    params.set("items", description.getItems());
    params.set("esc", new EscapeHelper());
    return templateHelper.render(template, params);
  }

  @Override
  public String renderJson(final Description description) {
    try {
      return objectMapper.writeValueAsString(description);
    }
    catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
