package org.sonatype.nexus.script.plugin.internal.orient

import org.sonatype.nexus.common.entity.AbstractEntity
import org.sonatype.nexus.script.Script
import org.sonatype.nexus.script.ScriptManager

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.TupleConstructor
/**
 * Script entity.
 * 
 * @since 3.0
 */
@ToString
@TupleConstructor
@EqualsAndHashCode
public class OrientScript
    extends AbstractEntity
    implements Script
{
  String name

  String content

  String type = ScriptManager.DEFAULT_TYPE
}
