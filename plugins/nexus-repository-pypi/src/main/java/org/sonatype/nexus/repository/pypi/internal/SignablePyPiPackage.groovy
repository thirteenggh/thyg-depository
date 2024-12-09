package org.sonatype.nexus.repository.pypi.internal

import javax.annotation.Nullable

import org.sonatype.nexus.repository.view.payloads.TempBlobPartPayload

import static com.google.common.base.Preconditions.checkNotNull

/**
 * Represents a Python .whl file, attributes and Gpg Signature which is optional.
 * @since 3.19
 */
class SignablePyPiPackage
{
  final TempBlobPartPayload wheelPayload

  final Map<String, String> attributes

  final TempBlobPartPayload gpgSignature

  SignablePyPiPackage(final TempBlobPartPayload wheelPayload,
                      final Map<String, String> attributes,
                      @Nullable final TempBlobPartPayload gpgSignature)
  {
    this.wheelPayload = checkNotNull(wheelPayload)
    this.attributes = checkNotNull(attributes)
    this.gpgSignature = gpgSignature
  }
}

