package org.sonatype.nexus.repository.rest.api.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.sonatype.nexus.repository.types.GroupType;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * @since 3.20
 */
@JsonIgnoreProperties({"type"})
public class GroupRepositoryApiRequest
    extends AbstractRepositoryApiRequest
{
  @ApiModelProperty
  @NotNull
  @Valid
  protected final StorageAttributes storage;

  @ApiModelProperty
  @NotNull
  @Valid
  protected final GroupAttributes group;

  @JsonCreator
  public GroupRepositoryApiRequest(
      @JsonProperty("name") final String name,
      @JsonProperty("format") final String format,
      @JsonProperty("online") final Boolean online,
      @JsonProperty("storage") final StorageAttributes storage,
      @JsonProperty("group") final GroupAttributes group)
  {
    super(name, format, GroupType.NAME, online);
    this.storage = storage;
    this.group = group;
  }

  public StorageAttributes getStorage() {
    return storage;
  }

  public GroupAttributes getGroup() {
    return group;
  }
}
