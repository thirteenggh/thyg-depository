package org.sonatype.nexus.coreui;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintValidatorContext;

import org.sonatype.nexus.blobstore.api.BlobStoreManager;
import org.sonatype.nexus.validation.ConstraintValidatorSupport;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Ensure that BlobStore names are unique case-insensitively.
 *
 * @since 3.1
 */
@Named
public class UniqueBlobStoreNameValidator
    extends ConstraintValidatorSupport<UniqueBlobStoreName, String>
{

  private final BlobStoreManager blobStoreManager;

  @Inject
  public UniqueBlobStoreNameValidator(final BlobStoreManager blobStoreManager) {
    this.blobStoreManager = checkNotNull(blobStoreManager);
  }

  @Override
  public boolean isValid(final String s, final ConstraintValidatorContext constraintValidatorContext) {
    return !blobStoreManager.exists(s);
  }
}
