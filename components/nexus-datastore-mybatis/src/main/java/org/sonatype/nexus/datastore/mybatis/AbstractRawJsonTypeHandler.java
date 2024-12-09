package org.sonatype.nexus.datastore.mybatis;

import java.util.function.Predicate;
import java.util.function.Supplier;

import org.sonatype.nexus.security.PasswordHelper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.TypeHandler;

/**
 * Abstract {@link TypeHandler} that maps types directly to JSON without any additional behaviour/processing.
 *
 * @since 3.21
 */
public abstract class AbstractRawJsonTypeHandler<T>
    extends AbstractJsonTypeHandler<T>
{
  private static final ObjectMapper RAW_OBJECT_MAPPER = new ObjectMapper();

  @Override
  protected final ObjectMapper buildObjectMapper(final Supplier<ObjectMapper> unused) {
    return RAW_OBJECT_MAPPER;
  }

  @Override
  void encryptSensitiveFields(final PasswordHelper passwordHelper, final Predicate<String> attributeFilter) {
    // don't attempt to process sensitive fields in raw mappers
  }
}
