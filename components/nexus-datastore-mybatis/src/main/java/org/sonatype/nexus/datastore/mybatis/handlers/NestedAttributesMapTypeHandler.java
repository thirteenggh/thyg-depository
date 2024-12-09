package org.sonatype.nexus.datastore.mybatis.handlers;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.Map;

import org.sonatype.nexus.common.collect.NestedAttributesMap;
import org.sonatype.nexus.datastore.mybatis.AbstractJsonTypeHandler;

import org.apache.ibatis.type.TypeHandler;

/**
 * MyBatis {@link TypeHandler} that maps a {@link NestedAttributesMap} to/from JSON.
 *
 * Sensitive fields will be automatically encrypted at rest when persisting to the config store.
 *
 * @see org.sonatype.nexus.datastore.mybatis.SensitiveAttributes
 *
 * @since 3.20
 */
// not @Named because we register this manually
public class NestedAttributesMapTypeHandler
    extends AbstractJsonTypeHandler<NestedAttributesMap>
{
  @Override
  protected Type getJsonType() {
    return Map.class;
  }

  @Override
  protected byte[] writeToJson(final Object value) throws SQLException {
    return super.writeToJson(((NestedAttributesMap) value).backing());
  }

  @Override
  @SuppressWarnings("unchecked")
  protected Object readFromJson(final byte[] json) throws SQLException {
    return new NestedAttributesMap("attributes", (Map<String, Object>) super.readFromJson(json));
  }
}
