package org.sonatype.nexus.common.event;

import java.util.function.Consumer;

/**
 * Consumer of events; like {@link Consumer} but throws a checked exception.
 *
 * @since 3.2.1
 */
public interface EventConsumer<E>
{
  void accept(E event) throws Exception;
}
