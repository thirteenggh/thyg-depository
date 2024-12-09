package org.sonatype.nexus.datastore.mybatis.handlers;

import java.util.Map;

import org.sonatype.nexus.datastore.mybatis.AbstractRawJsonTypeHandler;

import org.apache.ibatis.type.TypeHandler;

/**
 * MyBatis {@link TypeHandler} that maps a map to/from JSON without any additional behaviour/processing.
 *
 * @since 3.21
 */
// not @Named because we register this manually
public class MapTypeHandler
    extends AbstractRawJsonTypeHandler<Map<String, ?>>
{
  // nothing to add
}
