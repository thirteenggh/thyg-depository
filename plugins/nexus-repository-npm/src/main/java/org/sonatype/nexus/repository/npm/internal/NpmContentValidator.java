package org.sonatype.nexus.repository.npm.internal;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Supplier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.mime.MimeRulesSource;
import org.sonatype.nexus.repository.mime.ContentValidator;
import org.sonatype.nexus.repository.mime.DefaultContentValidator;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * npm specific {@link ContentValidator} that "hints" default content validator for npm metadata and format
 * specific files.
 *
 * @since 3.0
 */
@Named(NpmFormat.NAME)
@Singleton
public class NpmContentValidator
    extends ComponentSupport
    implements ContentValidator
{
  private final DefaultContentValidator defaultContentValidator;

  @Inject
  public NpmContentValidator(final DefaultContentValidator defaultContentValidator) {
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
    // if not tgz it is json package root
    if (name != null && !name.endsWith(".tgz")) {
      name += ".json";
    }
    return defaultContentValidator.determineContentType(
        strictContentTypeValidation, contentSupplier, mimeRulesSource, name, declaredContentType
    );
  }
}
