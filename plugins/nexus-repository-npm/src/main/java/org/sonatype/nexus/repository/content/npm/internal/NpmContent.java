package org.sonatype.nexus.repository.content.npm.internal;

import java.util.List;

import org.sonatype.nexus.repository.npm.internal.NpmFieldMatcher;
import org.sonatype.nexus.repository.view.Content;
import org.sonatype.nexus.repository.view.payloads.StreamPayload.InputStreamFunction;

import static java.util.Collections.singletonList;

/**
 * NPM focused {@link Content} allowing for setting {@link NpmStreamPayload} fields after creation.
 *
 * @since 3.16
 */
public class NpmContent
    extends Content
{
  private NpmStreamPayload payload;

  public NpmContent(final NpmStreamPayload payload, final Content content) {
    super(payload, content.getAttributes());
    this.payload = payload;
  }

  public NpmContent packageId(final String packageId) {
    payload.packageId(packageId);
    return this;
  }

  public NpmContent revId(final String revId) {
    payload.revId(revId);
    return this;
  }

  public NpmContent fieldMatchers(final NpmFieldMatcher fieldMatcher) {
    return fieldMatchers(singletonList(fieldMatcher));
  }

  public NpmContent fieldMatchers(final List<NpmFieldMatcher> fieldMatchers) {
    payload.fieldMatchers(fieldMatchers);
    return this;
  }

  public NpmContent missingBlobInputStreamSupplier(
      final InputStreamFunction<MissingAssetBlobException> missingBlobInputStreamSupplier)
  {
    payload.missingBlobInputStreamSupplier(missingBlobInputStreamSupplier);
    return this;
  }
}
