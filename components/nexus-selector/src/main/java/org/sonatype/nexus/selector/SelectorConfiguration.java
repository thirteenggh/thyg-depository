package org.sonatype.nexus.selector;

import java.util.Map;

/**
 * {@link Selector} configuration.
 *
 * @since 3.0
 */
public interface SelectorConfiguration
{
  String EXPRESSION = "expression";

  String getName();

  void setName(String name);

  String getType();

  void setType(String type);

  String getDescription();

  void setDescription(String description);

  Map<String,String> getAttributes();

  void setAttributes(Map<String, ?> attributes);
}
