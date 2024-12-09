package org.sonatype.nexus.repository.view.handlers;

import javax.inject.Named;

import org.sonatype.nexus.repository.view.Handler;

/**
 * A {@link Handler} provided by an extension to Nexus. {@link Named @Named} implementations will be automatically
 * inserted into repository routes.
 *
 * @since 3.1
 */
public interface ContributedHandler
    extends Handler
{
}
