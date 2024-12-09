package org.sonatype.nexus.coreui

import javax.validation.constraints.Pattern

import org.sonatype.nexus.selector.UniqueSelectorName
import org.sonatype.nexus.validation.constraint.NamePatternConstants
import org.sonatype.nexus.validation.group.Create
import org.sonatype.nexus.validation.group.Update

import groovy.transform.ToString
import org.hibernate.validator.constraints.NotBlank

/**
 * Selector exchange object.
 *
 * @since 3.0
 */
@ToString(includePackage = false, includeNames = true)
class SelectorXO
{
  @NotBlank(groups = Update)
  String id

  @Pattern(regexp = NamePatternConstants.REGEX, message = NamePatternConstants.MESSAGE)
  @NotBlank(groups = Create)
  @UniqueSelectorName(groups = Create)
  String name

  @NotBlank(groups = Create)
  String type

  String description

  @NotBlank
  String expression

  List<String> usedBy

  int usedByCount
}
