package org.sonatype.nexus.script

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.TupleConstructor
import org.hibernate.validator.constraints.NotEmpty

/**
 * Script result exchange object.
 * 
 * @since 3.0
 */
@ToString
@TupleConstructor
@EqualsAndHashCode
class ScriptResultXO
{
  @NotEmpty
  String name
  
  String result
}
