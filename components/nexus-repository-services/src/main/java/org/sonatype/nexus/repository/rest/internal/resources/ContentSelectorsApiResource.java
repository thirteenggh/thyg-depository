package org.sonatype.nexus.repository.rest.internal.resources;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.sonatype.nexus.repository.rest.api.ContentSelectorApiCreateRequest;
import org.sonatype.nexus.repository.rest.api.ContentSelectorApiResponse;
import org.sonatype.nexus.repository.rest.api.ContentSelectorApiUpdateRequest;
import org.sonatype.nexus.repository.rest.internal.resources.doc.ContentSelectorsResourceDoc;
import org.sonatype.nexus.rest.Resource;
import org.sonatype.nexus.rest.WebApplicationMessageException;
import org.sonatype.nexus.selector.CselSelector;
import org.sonatype.nexus.selector.SelectorConfiguration;
import org.sonatype.nexus.selector.SelectorFactory;
import org.sonatype.nexus.selector.SelectorManager;
import org.sonatype.nexus.validation.Validate;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Collections.singletonMap;
import static java.util.stream.Collectors.toList;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static org.sonatype.nexus.selector.SelectorConfiguration.EXPRESSION;

/**
 * @since 3.19
 */
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class ContentSelectorsApiResource
    implements Resource, ContentSelectorsResourceDoc
{
  private final SelectorFactory selectorFactory;

  private final SelectorManager selectorManager;

  @Inject
  public ContentSelectorsApiResource(final SelectorFactory selectorFactory, final SelectorManager selectorManager) {
    this.selectorFactory = checkNotNull(selectorFactory);
    this.selectorManager = checkNotNull(selectorManager);
  }

  @GET
  @RequiresAuthentication
  @RequiresPermissions("nexus:selectors:read")
  public List<ContentSelectorApiResponse> getContentSelectors() {
    // The selector manager has a built-in cache of all content selectors which should bound the performance
    return selectorManager.browse().stream().map(ContentSelectorsApiResource::fromSelectorConfiguration)
        .collect(toList());
  }

  @POST
  @Validate
  @RequiresAuthentication
  @RequiresPermissions("nexus:selectors:create")
  public void createContentSelector(@Valid final ContentSelectorApiCreateRequest request) {
    selectorFactory.validateSelector(CselSelector.TYPE, request.getExpression());
    selectorManager.create(request.getName(), CselSelector.TYPE, request.getDescription(),
        singletonMap(EXPRESSION, request.getExpression()));
  }

  @GET
  @Path("{name}")
  @RequiresAuthentication
  @RequiresPermissions("nexus:selectors:read")
  public ContentSelectorApiResponse getContentSelector(@PathParam("name") final String name) {
    SelectorConfiguration configuration = findConfigurationByNameOrThrowNotFound(name);

    return ContentSelectorsApiResource.fromSelectorConfiguration(configuration);
  }

  @PUT
  @Path("{name}")
  @Validate
  @RequiresAuthentication
  @RequiresPermissions("nexus:selectors:update")
  public void updateContentSelector(@PathParam("name") final String name,
                                    @Valid final ContentSelectorApiUpdateRequest request)
  {
    SelectorConfiguration configuration = findConfigurationByNameOrThrowNotFound(name);

    selectorFactory.validateSelector(configuration.getType(), request.getExpression());

    configuration.setDescription(request.getDescription());
    configuration.setAttributes(singletonMap(EXPRESSION, request.getExpression()));
    selectorManager.update(configuration);
  }

  @DELETE
  @Path("{name}")
  @RequiresAuthentication
  @RequiresPermissions("nexus:selectors:delete")
  public void deleteContentSelector(@PathParam("name") final String name) {
    SelectorConfiguration configuration = findConfigurationByNameOrThrowNotFound(name);

    selectorManager.delete(configuration);
  }

  private SelectorConfiguration findConfigurationByNameOrThrowNotFound(final String name) {
    return selectorManager.findByName(name)
        .orElseThrow(() -> new WebApplicationMessageException(NOT_FOUND, "\"No selector found for " + name + '"',
            APPLICATION_JSON));
  }

  private static ContentSelectorApiResponse fromSelectorConfiguration(final SelectorConfiguration selectorConfiguration) {
    ContentSelectorApiResponse response = new ContentSelectorApiResponse();
    response.setName(selectorConfiguration.getName());
    response.setType(selectorConfiguration.getType());
    response.setDescription(selectorConfiguration.getDescription());
    response.setExpression(selectorConfiguration.getAttributes().get(EXPRESSION));
    return response;
  }
}
