package org.sonatype.nexus.datastore.mybatis.handlers;

import java.util.Map;

import org.sonatype.nexus.datastore.mybatis.AbstractJsonTypeHandler;

import org.apache.ibatis.type.TypeHandler;

/**
 * MyBatis {@link TypeHandler} that maps a (possibly nested) attribute map to/from JSON.
 *
 * Sensitive fields will be automatically encrypted at rest when persisting to the config store.
 *
 * @see org.sonatype.nexus.datastore.mybatis.SensitiveAttributes
 *
 * @since 3.19
 */
// not @Named because we register this manually
public class AttributesTypeHandler
    extends AbstractJsonTypeHandler<Map<String, ?>>
{
  // nothing to do
}
