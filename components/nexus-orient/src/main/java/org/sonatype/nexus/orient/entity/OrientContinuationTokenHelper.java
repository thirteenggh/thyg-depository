package org.sonatype.nexus.orient.entity;

import javax.annotation.Nullable;

import org.sonatype.nexus.common.entity.ContinuationTokenHelper;
import org.sonatype.nexus.common.entity.DetachedEntityId;
import org.sonatype.nexus.common.entity.Entity;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;
import static org.sonatype.nexus.common.entity.EntityHelper.id;

/**
 * Orient based implementation of {@link ContinuationTokenHelper}
 *
 * @since 3.7
 */
public abstract class OrientContinuationTokenHelper
    implements ContinuationTokenHelper
{
  private final EntityAdapter<?> entityAdapter;

  public OrientContinuationTokenHelper(final EntityAdapter<?> entityAdapter) {
    this.entityAdapter = checkNotNull(entityAdapter);
  }

  @Nullable
  @Override
  public String getIdFromToken(final String continuationToken) {
    try {
      return continuationToken != null ?
          entityAdapter.recordIdentity(new DetachedEntityId(continuationToken)).toString() : null;
    }
    catch (IllegalArgumentException e) {
      throw new ContinuationTokenException(
          format("Caught exception parsing id from continuation token '%s'", continuationToken), e);
    }
  }

  @Override
  public String getTokenFromId(final Entity entity) {
    return id(entity).getValue();
  }
}
