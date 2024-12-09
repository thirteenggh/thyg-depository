package org.sonatype.nexus.repository.view;

/**
 * View matcher.
 *
 * @since 3.0
 */
public interface Matcher
{
  boolean matches(Context context);
}
