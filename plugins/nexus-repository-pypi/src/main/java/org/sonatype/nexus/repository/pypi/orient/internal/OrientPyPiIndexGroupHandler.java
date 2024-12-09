package org.sonatype.nexus.repository.pypi.orient.internal;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.template.TemplateHelper;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.group.GroupHandler;
import org.sonatype.nexus.repository.http.HttpResponses;
import org.sonatype.nexus.repository.pypi.internal.AssetKind;
import org.sonatype.nexus.repository.view.Content;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Response;
import org.sonatype.nexus.repository.view.matchers.token.TokenMatcher;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.repository.pypi.internal.AssetKind.ROOT_INDEX;
import static org.sonatype.nexus.repository.pypi.internal.PyPiGroupUtils.lazyMergeResult;
import static org.sonatype.nexus.repository.pypi.internal.PyPiPathUtils.INDEX_PATH_PREFIX;
import static org.sonatype.nexus.repository.pypi.internal.PyPiPathUtils.name;

/**
 * Support for merging PyPI simple indexes together.
 *
 * @since 3.1
 */
@Named
@Singleton
class OrientPyPiIndexGroupHandler
    extends GroupHandler
{
  private final TemplateHelper templateHelper;

  @Inject
  public OrientPyPiIndexGroupHandler(final TemplateHelper templateHelper) {
    this.templateHelper = checkNotNull(templateHelper);
  }

  @Override
  protected Response doGet(@Nonnull final Context context,
                           @Nonnull final GroupHandler.DispatchedRepositories dispatched)
      throws Exception
  {
    checkNotNull(context);
    checkNotNull(dispatched);

    String name;
    AssetKind assetKind = context.getAttributes().get(AssetKind.class);
    if (ROOT_INDEX.equals(assetKind)) {
      name = INDEX_PATH_PREFIX;
    }
    else {
      name = name(context.getAttributes().require(TokenMatcher.State.class));
    }

    OrientPyPiGroupFacet groupFacet = context.getRepository().facet(OrientPyPiGroupFacet.class);
    Content content = groupFacet.getFromCache(name, assetKind);

    Map<Repository, Response> memberResponses = getAll(context, groupFacet.members(), dispatched);

    if (groupFacet.isStale(name, content, memberResponses)) {
      return HttpResponses.ok(
          groupFacet.buildIndexRoot(
              name, assetKind, lazyMergeResult(name, assetKind, memberResponses, templateHelper)));
    }

    return HttpResponses.ok(content);
  }
}
