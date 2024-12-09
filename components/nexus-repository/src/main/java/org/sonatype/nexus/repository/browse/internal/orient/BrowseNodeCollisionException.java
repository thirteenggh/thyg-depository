package org.sonatype.nexus.repository.browse.internal.orient;

import org.sonatype.nexus.repository.browse.node.BrowseNode;

import com.orientechnologies.common.concur.ONeedRetryException;

/**
 * {@link ONeedRetryException} thrown when we want to retry upserting a {@link BrowseNode} due to a collision.
 *
 * @since 3.14
 */
public class BrowseNodeCollisionException
    extends ONeedRetryException
{
  BrowseNodeCollisionException(final String message) {
    super(message);
  }
}
