package org.sonatype.nexus.script

import javax.validation.constraints.Pattern

import org.sonatype.nexus.validation.constraint.NamePatternConstants

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.TupleConstructor
import org.hibernate.validator.constraints.NotEmpty

/**
 * Script exchange object.
 * 
 * @since 3.0
 */
@ToString
@TupleConstructor
@EqualsAndHashCode
class ScriptXO
{
  @Pattern(regexp = NamePatternConstants.REGEX, message = NamePatternConstants.MESSAGE)
  @NotEmpty
  String name

  @NotEmpty
  String content
  
  @NotEmpty
  String type
}
