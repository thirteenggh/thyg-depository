package org.sonatype.nexus.security.user;

import java.util.HashSet;
import java.util.Set;

/**
 * A defines searchable fields.
 *
 * Null or empty fields will be ignored.
 */
public class UserSearchCriteria
{
  private String userId;

  private Set<String> oneOfRoleIds = new HashSet<>();

  private String source;

  private String email;

  private Integer limit;

  public UserSearchCriteria() {
  }

  public UserSearchCriteria(final String userId) {
    this.userId = userId;
  }

  public UserSearchCriteria(final String userId, final Set<String> oneOfRoleIds, final String source) {
    this.userId = userId;
    this.oneOfRoleIds = oneOfRoleIds;
    this.source = source;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(final String userId) {
    this.userId = userId;
  }

  public Set<String> getOneOfRoleIds() {
    return oneOfRoleIds;
  }

  public void setOneOfRoleIds(final Set<String> oneOfRoleIds) {
    this.oneOfRoleIds = oneOfRoleIds;
  }

  public String getSource() {
    return source;
  }

  public void setSource(final String source) {
    this.source = source;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  public Integer getLimit() {
    return limit;
  }

  public void setLimit(final Integer limit) {
    this.limit = limit;
  }
}
