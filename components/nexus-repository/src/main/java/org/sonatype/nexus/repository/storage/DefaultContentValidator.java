package org.sonatype.nexus.repository.storage;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.mime.MimeSupport;

/**
 * Default {@link ContentValidator}; provides compatibility with clients expecting the old package
 *
 * @deprecated use org.sonatype.nexus.repository.mime.DefaultContentValidator instead
 */
@Deprecated
@Named(org.sonatype.nexus.repository.mime.DefaultContentValidator.NAME)
@Singleton
public class DefaultContentValidator
    extends org.sonatype.nexus.repository.mime.DefaultContentValidator
    implements ContentValidator
{
  @Inject
  public DefaultContentValidator(final MimeSupport mimeSupport) {
    super(mimeSupport);
  }
}
