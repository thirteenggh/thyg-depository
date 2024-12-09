package org.sonatype.nexus.script.plugin.internal.security

import org.sonatype.nexus.security.authz.WildcardPermission2

import com.google.common.collect.ImmutableList
import groovy.transform.CompileStatic

import static com.google.common.base.Preconditions.checkNotNull

/**
 * Script permission.
 * Allows for fine-grained permissions on Scripts based on their name.
 * 
 * @since 3.0
 */
@CompileStatic
class ScriptPermission
    extends WildcardPermission2
{
  public static final String SYSTEM = 'nexus'
  
  public static final String DOMAIN = 'script'
  
  final String name
  
  final List<String> actions 
  
  ScriptPermission(String name, List<String> actions) {
    this.name = checkNotNull(name)
    this.actions = checkNotNull(actions)

    setParts(ImmutableList.of(SYSTEM, DOMAIN, name), actions)
  }
}
