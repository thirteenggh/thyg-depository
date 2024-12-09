package org.sonatype.nexus.quartz.internal.orient;

import com.orientechnologies.orient.core.metadata.schema.OType;

/**
 * Provides the minimal marshalling functionality needed by {@link MarshalledEntityAdapter}.
 *
 * @see MarshalledEntityAdapter
 * @since 3.0
 */
public interface Marshaller
{
  /**
   * The orient type this marshaller requires to persist object data.
   */
  OType getType();

  /**
   * Marshal given value.
   *
   * Return value must be compatible with {@link #getType()}.
   */
  Object marshall(Object value) throws Exception;

  /**
   * Unmarshall data into object of given type.
   *
   * Marshalled data must be compatible with {@link #getType()}.
   */
  <T> T unmarshall(Object marshalled, Class<T> type) throws Exception;
}
