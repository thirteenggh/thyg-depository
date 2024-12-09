package org.sonatype.nexus.repository.apt.internal;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

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

/**
 * Apt-specific {@link ContentValidator} that "hints" default content validator for apt package metadata and format
 * specific files
 *
 * @since 3.17
 */
@Named(AptFormat.NAME)
@Singleton
public class AptContentValidator
    extends ComponentSupport
    implements ContentValidator
{
  private static final String TEXT_PLAIN = "text/plain";
  private static final String TXT = ".txt";

  private final DefaultContentValidator defaultContentValidator;

  @Inject
  public AptContentValidator(final DefaultContentValidator defaultContentValidator) {
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
    String name = contentName;
    // if name is Packages without extension - it's plain/text
    if (name != null && Objects.equals(declaredContentType, TEXT_PLAIN)) {
      name += TXT;
    }
    return defaultContentValidator.determineContentType(
        strictContentTypeValidation, contentSupplier, mimeRulesSource, name, declaredContentType
    );
  }
}
