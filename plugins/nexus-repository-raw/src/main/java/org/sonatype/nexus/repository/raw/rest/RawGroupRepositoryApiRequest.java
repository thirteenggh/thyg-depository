package org.sonatype.nexus.repository.raw.rest;

import org.sonatype.nexus.repository.raw.internal.RawFormat;
import org.sonatype.nexus.repository.rest.api.model.GroupAttributes;
import org.sonatype.nexus.repository.rest.api.model.GroupRepositoryApiRequest;
import org.sonatype.nexus.repository.rest.api.model.StorageAttributes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import static org.sonatype.nexus.content.raw.internal.recipe.ContentDisposition.ATTACHMENT;

/**
 * @since 3.24
 */
@JsonIgnoreProperties({"format", "type"})
public class RawGroupRepositoryApiRequest
    extends GroupRepositoryApiRequest
{
  private final RawAttributes raw;

  @JsonCreator
  public RawGroupRepositoryApiRequest(
      @JsonProperty("name") final String name,
      @JsonProperty("online") final Boolean online,
      @JsonProperty("storage") final StorageAttributes storage,
      @JsonProperty("group") final GroupAttributes group,
      @JsonProperty("raw") final RawAttributes raw)
  {
    super(name, RawFormat.NAME, online, storage, group);
    this.raw = raw != null ? raw : new RawAttributes(ATTACHMENT);
  }

  public RawAttributes getRaw() {
    return raw;
  }
}
