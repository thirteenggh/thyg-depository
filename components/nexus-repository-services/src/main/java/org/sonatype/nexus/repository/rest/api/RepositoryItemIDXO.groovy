package org.sonatype.nexus.repository.rest.api

import javax.ws.rs.NotFoundException
import javax.ws.rs.WebApplicationException

import org.sonatype.goodies.common.Loggers

import org.slf4j.Logger

import static com.google.common.base.Preconditions.checkNotNull
import static org.sonatype.nexus.repository.http.HttpStatus.UNPROCESSABLE_ENTITY

/**
 * An object to hold the id and repository id of an asset in the format repository-id:asset-id, encoded.
 * @since 3.3
 */
class RepositoryItemIDXO
{
  public static final Logger log = Loggers.getLogger(RepositoryItemIDXO.class)

  String repositoryId

  String id

  RepositoryItemIDXO(final String repositoryId, final String id) {
    this.repositoryId = checkNotNull(repositoryId)
    this.id = checkNotNull(id)
  }

  public static RepositoryItemIDXO fromString(String encoded) {
    try {
      String decoded = new String(Base64.getUrlDecoder().decode(encoded))
      String[] parts = decoded.split(":")
      if (parts.length != 2) {
        throw new WebApplicationException("Unable to parse RepositoryItemIDXO " + encoded, UNPROCESSABLE_ENTITY)
      }
      return new RepositoryItemIDXO(parts[0], parts[1])
    }
    catch (IllegalArgumentException e) {
      log.debug("Unable to parse id: {}, returning 404.", encoded, e)
      throw new NotFoundException("Unable to locate asset with id " + encoded)
    }
  }

  public String getValue() {
    return new String(Base64.getUrlEncoder().withoutPadding().encode("$repositoryId:$id".bytes))
  }
}
