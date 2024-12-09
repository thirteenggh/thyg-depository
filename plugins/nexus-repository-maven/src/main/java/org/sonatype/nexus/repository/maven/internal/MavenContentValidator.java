package org.sonatype.nexus.repository.maven.internal;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.mime.MimeRulesSource;
import org.sonatype.nexus.repository.InvalidContentException;
import org.sonatype.nexus.repository.mime.ContentValidator;
import org.sonatype.nexus.repository.mime.DefaultContentValidator;

import java.util.function.Supplier;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Maven 2 specific {@link ContentValidator} that "hints" default content validator for some Maven specific file
 * extensions and format specific files.
 *
 * @since 3.0
 */
@Named(Maven2Format.NAME)
@Singleton
public class MavenContentValidator
    extends ComponentSupport
    implements ContentValidator
{
  private final DefaultContentValidator defaultContentValidator;

  @Inject
  public MavenContentValidator(final DefaultContentValidator defaultContentValidator) {
    this.defaultContentValidator = checkNotNull(defaultContentValidator);
  }

  @Nonnull
  @Override
  public String determineContentType(boolean strictContentTypeValidation,
                                     Supplier<InputStream> contentSupplier,
                                     @Nullable MimeRulesSource mimeRulesSource,
                                     @Nullable String contentName,
                                     @Nullable String declaredContentType) throws IOException
  {
    if (contentName != null) {
      if (contentName.endsWith(".pom")) {
        // Note: this is due fact that Tika has glob "*.pom" extension enlisted at text/plain
        return defaultContentValidator.determineContentType(
            strictContentTypeValidation, contentSupplier, mimeRulesSource, contentName + ".xml", declaredContentType
        );
      }
      else if (contentName.endsWith(".sha1") || contentName.endsWith(".md5")) {
        if (strictContentTypeValidation) {
          // hashes are small/simple, do it directly
          try (InputStream is = contentSupplier.get()) {
            final String digestCandidate = DigestExtractor.extract(is);
            if (!DigestExtractor.isDigest(digestCandidate)) {
              throw new InvalidContentException("Not a Maven2 digest: " + contentName);
            }
          }
        }

        if (declaredContentType != null) {
          return declaredContentType;
        }
      }
    }
    // everything else goes to default for now
    return defaultContentValidator.determineContentType(
        strictContentTypeValidation, contentSupplier, mimeRulesSource, contentName, declaredContentType
    );
  }
}
