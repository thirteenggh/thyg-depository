package org.sonatype.nexus.cleanup.internal.rest;

import javax.validation.constraints.NotEmpty;

import org.sonatype.nexus.cleanup.storage.CleanupPolicyReleaseType;
import org.sonatype.nexus.cleanup.storage.config.CleanupPolicyAssetNamePattern;

/**
 * @since 3.29
 */
public class PreviewRequestXO
{
  @NotEmpty
  private String repository;

  private Integer criteriaLastBlobUpdated;

  private Integer criteriaLastDownloaded;

  private CleanupPolicyReleaseType criteriaReleaseType;

  @CleanupPolicyAssetNamePattern
  private String criteriaAssetRegex;

  public String getRepository() {
    return repository;
  }

  public void setRepository(final String repository) {
    this.repository = repository;
  }

  public CleanupPolicyReleaseType getCriteriaReleaseType() {
    return criteriaReleaseType;
  }

  public Integer getCriteriaLastBlobUpdated() {
    return criteriaLastBlobUpdated;
  }

  public Integer getCriteriaLastDownloaded() {
    return criteriaLastDownloaded;
  }

  public String getCriteriaAssetRegex() {
    return criteriaAssetRegex;
  }

  public void setCriteriaAssetRegex(final String criteriaAssetRegex) {
    this.criteriaAssetRegex = criteriaAssetRegex;
  }

  public void setCriteriaLastBlobUpdated(final Integer criteriaLastBlobUpdated) {
    this.criteriaLastBlobUpdated = criteriaLastBlobUpdated;
  }

  public void setCriteriaLastDownloaded(final Integer criteriaLastDownloaded) {
    this.criteriaLastDownloaded = criteriaLastDownloaded;
  }

  public void setCriteriaReleaseType(final CleanupPolicyReleaseType criteriaReleaseType) {
    this.criteriaReleaseType = criteriaReleaseType;
  }
}
