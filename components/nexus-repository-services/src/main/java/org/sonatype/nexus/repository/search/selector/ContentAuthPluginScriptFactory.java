package org.sonatype.nexus.repository.search.selector;

import java.util.Map;

import org.apache.shiro.subject.Subject;
import org.elasticsearch.script.ExecutableScript;
import org.elasticsearch.script.NativeScriptFactory;
import org.elasticsearch.script.Script;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Collections.singletonMap;
import static org.elasticsearch.script.ScriptService.ScriptType.INLINE;

/**
 * Factory for {@link ContentAuthPluginScript}.
 *
 * @since 3.1
 */
public class ContentAuthPluginScriptFactory
    implements NativeScriptFactory
{
  private static final String SUBJECT_PARAM = "subject";

  @Override
  public ExecutableScript newScript(final Map<String, Object> params) {
    checkNotNull(params);
    String subjectId = (String) checkNotNull(params.get(SUBJECT_PARAM));
    Subject subject = ContentAuthPlugin.getSearchSubjectHelper().getSubject(subjectId);
    return new ContentAuthPluginScript(
        subject,
        ContentAuthPlugin.getContentPermissionChecker(),
        ContentAuthPlugin.getVariableResolverAdapterManager(),
        ContentAuthPlugin.getRepositoryManager(),
        ContentAuthPlugin.getContentAuthSleep());
  }

  @Override
  public boolean needsScores() {
    return false;
  }

  /**
   * Returns a new {@link Script} instance for use in ES queries, configured for the {@link ContentAuthPluginScript}.
   */
  public static Script newScript(final String subjectId) {
    checkNotNull(subjectId);
    return new Script(ContentAuthPluginScript.NAME, INLINE, "native", singletonMap(SUBJECT_PARAM, subjectId));
  }
}
