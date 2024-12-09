package org.sonatype.nexus.script.plugin.internal

import org.sonatype.nexus.common.entity.HasName
import org.sonatype.nexus.script.Script
import org.sonatype.nexus.script.ScriptManager

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.TupleConstructor

/**
 * {@link Script} data.
 *
 * @since 3.21
 */
@ToString
@TupleConstructor
@EqualsAndHashCode
public class ScriptData
    implements HasName, Script
{
  String name

  String content

  String type = ScriptManager.DEFAULT_TYPE
}
