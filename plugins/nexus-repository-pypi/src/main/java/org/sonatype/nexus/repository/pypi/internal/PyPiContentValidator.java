package org.sonatype.nexus.repository.pypi.internal;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.mime.MimeRulesSource;
import org.sonatype.nexus.repository.mime.ContentValidator;
import org.sonatype.nexus.repository.mime.DefaultContentValidator;

import java.util.function.Supplier;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.lang.StringUtils.endsWith;

/**
 * PyPI-specific {@link ContentValidator} that treats accesses to index pages as being HTML content.
 *
 * @since 3.1
 */
@Named(PyPiFormat.NAME)
@Singleton
public class PyPiContentValidator
    extends ComponentSupport
    implements ContentValidator
{
  protected static final String ASC_FILE_EXTENSION = ".asc";

  protected static final String TEXT_FILE_EXTENSION = ".txt";

  private final DefaultContentValidator defaultContentValidator;

  @Inject
  public PyPiContentValidator(final DefaultContentValidator defaultContentValidator) {
    this.defaultContentValidator = checkNotNull(defaultContentValidator);
  }

  @Nonnull
  @Override
  public String determineContentType(final boolean strictContentTypeValidation,
                                     final Supplier<InputStream> contentSupplier,
                                     @Nullable final MimeRulesSource mimeRulesSource,
                                     @Nullable final String contentName,
                                     @Nullable final String declaredContentType) throws IOException
  {
    String name = modifyContentNameForSpecialCases(contentName);
    return defaultContentValidator
        .determineContentType(strictContentTypeValidation, contentSupplier, mimeRulesSource, name, declaredContentType);
  }

  private String modifyContentNameForSpecialCases(final String contentName) {
    if (isAscFileExtension(contentName)) {
      return contentName + TEXT_FILE_EXTENSION;
    }
    return contentName;
  }

  private boolean isAscFileExtension(@Nullable final String contentName) {
    return endsWith(contentName, ASC_FILE_EXTENSION);
  }
}
