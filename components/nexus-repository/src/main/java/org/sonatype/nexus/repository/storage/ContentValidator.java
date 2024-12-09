package org.sonatype.nexus.repository.storage;

/**
 * Content validator interface; provides compatibility with clients expecting the old package.
 *
 * @deprecated use org.sonatype.nexus.repository.mime.ContentValidator instead
 */
@Deprecated
public interface ContentValidator
    extends org.sonatype.nexus.repository.mime.ContentValidator
{
  // nothing to add...
}
