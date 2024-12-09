package org.sonatype.nexus.repository.golang.internal;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.mime.MimeRulesSource;
import org.sonatype.nexus.repository.golang.GolangFormat;
import org.sonatype.nexus.repository.mime.ContentValidator;
import org.sonatype.nexus.repository.mime.DefaultContentValidator;

import java.util.function.Supplier;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Go specific {@link ContentValidator} that validates for some go specific file
 * extensions and format specific files.
 *
 * @since 3.17
 */
@Named(GolangFormat.NAME)
@Singleton
public class GolangContentValidator
    extends ComponentSupport
    implements ContentValidator
{
  private final DefaultContentValidator defaultContentValidator;

  @Inject
  public GolangContentValidator(final DefaultContentValidator defaultContentValidator) {
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

    String modifiedContentName = modifyContentName(contentName);

    return defaultContentValidator.determineContentType(
        strictContentTypeValidation, contentSupplier, mimeRulesSource, modifiedContentName, declaredContentType
    );
  }

  private String modifyContentName(@Nullable final String contentName) {
    if (contentName != null) {
      if (contentName.endsWith(".mod")) {
        // Note: this is due to fact that Tika has glob "*.mod" extension enlisted at audio/x-mod
        return contentName + ".txt";
      }
      else if (contentName.endsWith("/list")) {
        // It is valid to return an empty list file. The latest version of Tika (1.20) recognises this as an 
        // application/octet-stream which is wrong and causes NXRM to error and return a 404 instead of a 200 response.
        return contentName + ".json";
      }
    }
    
    return contentName;
  }
}
