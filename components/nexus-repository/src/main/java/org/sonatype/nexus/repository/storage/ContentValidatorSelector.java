package org.sonatype.nexus.repository.storage;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.mime.ContentValidator;
import org.sonatype.nexus.repository.mime.DefaultContentValidator;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Content validator selector component.
 *
 * @since 3.0
 */
@Singleton
@Named
public class ContentValidatorSelector
    extends ComponentSupport
{
  private final Map<String, ContentValidator> contentValidators;

  private final DefaultContentValidator defaultContentValidator;

  @Inject
  public ContentValidatorSelector(final Map<String, ContentValidator> contentValidators,
                                  final DefaultContentValidator defaultContentValidator)
  {
    this.contentValidators = checkNotNull(contentValidators);
    this.defaultContentValidator = checkNotNull(defaultContentValidator);
  }

  /**
   * Find content validator for given repository. If no format-specific validator is configured, the default is used.
   *
   * @param repository The repository for content validator is looked up.
   * @return the repository specific content validator to be used, or the default content validator, never {@code null}.
   */
  @Nonnull
  public ContentValidator validator(final Repository repository) {
    checkNotNull(repository);
    String format = repository.getFormat().getValue();
    log.trace("Looking for content validator for format: {}", format);
    ContentValidator contentValidator = contentValidators.get(format);
    if (contentValidator == null) {
      return defaultContentValidator;
    }
    return contentValidator;
  }
}
