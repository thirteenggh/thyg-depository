package org.sonatype.repository.conan.internal.metadata;

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Each project consists of these element. They are grouped here for easier access throughout the code base
 *
 * @since 3.28
 */
public class ConanCoords
{
  final private String path;

  final private String group;

  final private String project;

  final private String version;

  final private String channel;

  final private String sha;

  public ConanCoords(final String path,
                     final String group,
                     final String project,
                     final String version,
                     final String channel,
                     @Nullable final String sha) {
    this.path = checkNotNull(path);
    this.group = checkNotNull(group);
    this.project = checkNotNull(project);
    this.version = checkNotNull(version);
    this.channel = checkNotNull(channel);
    this.sha = sha;
  }

  public String getPath() { return path; }

  public String getGroup() {
    return group;
  }

  public String getProject() {
    return project;
  }

  public String getVersion() {
    return version;
  }

  public String getChannel() {
    return channel;
  }

  public String getSha() {
    return sha;
  }
}
