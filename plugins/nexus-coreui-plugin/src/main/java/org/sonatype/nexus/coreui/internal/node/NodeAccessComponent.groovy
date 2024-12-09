package org.sonatype.nexus.coreui.internal.node

import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

import org.sonatype.nexus.common.node.NodeAccess
import org.sonatype.nexus.extdirect.DirectComponentSupport

import com.codahale.metrics.annotation.ExceptionMetered
import com.codahale.metrics.annotation.Timed
import com.softwarementors.extjs.djn.config.annotations.DirectAction
import com.softwarementors.extjs.djn.config.annotations.DirectMethod

/**
 * NodeAccessComponent {@link DirectComponentSupport}.
 *
 * @since 3.2
 */
@Named
@Singleton
@DirectAction(action = 'node_NodeAccess')
class NodeAccessComponent
    extends DirectComponentSupport
{

  @Inject
  NodeAccess nodeAccess

  @DirectMethod
  @Timed
  @ExceptionMetered
  List<NodeInfoXO> nodes() {
    nodeAccess.memberAliases.collect {
      new NodeInfoXO(name: it.key,
          local: it.key == nodeAccess.id,
          displayName: it.value
      )
    }
  }
}
