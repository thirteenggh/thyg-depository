package org.sonatype.nexus.repository.apt.internal.debian;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @since 3.17
 */
public class Release
{
  private final ControlFile index;

  public Release(final ControlFile index) {
    super();
    this.index = index;
  }

  public Optional<String> getOrigin() {
    return getValue("Origin");
  }

  public Optional<String> getLabel() {
    return getValue("Label");
  }

  public Optional<String> getSuite() {
    return getValue("Suite");
  }

  public Optional<String> getVersion() {
    return getValue("Version");
  }

  public Optional<String> getCodename() {
    return getValue("Codename");
  }

  public List<String> getComponents() {
    return index.getField("Components").map(s -> s.listValue()).orElse(Collections.emptyList());
  }

  public List<String> getArchitectures() {
    return index.getField("Architectures").map(s -> s.listValue()).orElse(Collections.emptyList());
  }

  public Optional<String> getDescription() {
    return getValue("Description");
  }

  private Optional<String> getValue(final String name) {
    return index.getField(name).map(e -> e.value);
  }
}
