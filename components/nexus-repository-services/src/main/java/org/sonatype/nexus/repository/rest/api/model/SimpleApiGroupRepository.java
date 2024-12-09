package org.sonatype.nexus.repository.rest.api.model;

import javax.validation.constraints.NotNull;

import org.sonatype.nexus.repository.types.GroupType;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * API Group Repository for simple formats which do not have custom attributes for groups.
 *
 * @since 3.20
 */
@JsonIgnoreProperties(value = {"format", "type", "url"}, allowGetters = true)
public class SimpleApiGroupRepository
    extends AbstractApiRepository
{
  @ApiModelProperty
  @NotNull
  protected final StorageAttributes storage;

  @ApiModelProperty
  @NotNull
  protected final GroupAttributes group;

  @JsonCreator
  public SimpleApiGroupRepository(
      @JsonProperty("name") final String name,
      @JsonProperty("format") final String format,
      @JsonProperty("url") final String url,
      @JsonProperty("online") final Boolean online,
      @JsonProperty("storage") final StorageAttributes storage,
      @JsonProperty("group") final GroupAttributes group)
  {
    super(name, format, GroupType.NAME, url, online);
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
