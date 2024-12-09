package org.sonatype.repository.conan.internal.security;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Supplier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.mime.MimeRulesSource;
import org.sonatype.nexus.repository.storage.ContentValidator;
import org.sonatype.nexus.repository.storage.DefaultContentValidator;
import org.sonatype.nexus.repository.view.ContentTypes;
import org.sonatype.repository.conan.internal.ConanFormat;
import org.sonatype.repository.conan.internal.orient.utils.ConanFacetUtils;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @since 3.28
 */
@Named(ConanFormat.NAME)
@Singleton
public class ConanContentValidator
    implements ContentValidator
{

  private static final String X_PYTHON = "text/x-python";

  private final DefaultContentValidator defaultContentValidator;

  @Inject
  public ConanContentValidator(final DefaultContentValidator defaultContentValidator) {
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
    if (contentName != null) {
      if (contentName.endsWith(".tgz")) {
        return ContentTypes.APPLICATION_GZIP;
      }
      else if (contentName.endsWith("/conanfile.py")) {
        return X_PYTHON;
      }
      else if (ConanFacetUtils.isPackageSnapshot(contentName)) {
        return ContentTypes.APPLICATION_JSON;
      }
      else if (ConanFacetUtils.isDigest(contentName)) {
        return ContentTypes.APPLICATION_JSON;
      }
    }
    return defaultContentValidator.determineContentType(
        strictContentTypeValidation, contentSupplier, mimeRulesSource, contentName, declaredContentType
    );
  }
}
