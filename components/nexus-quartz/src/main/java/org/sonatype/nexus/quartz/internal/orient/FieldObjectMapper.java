package org.sonatype.nexus.quartz.internal.orient;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * {@link ObjectMapper} which only pays attention to non-null fields.
 *
 * @since 3.0
 */
public class FieldObjectMapper
  extends ObjectMapper
{
  public FieldObjectMapper() {
    // only pay attention to fields
    setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
    setVisibility(PropertyAccessor.GETTER, Visibility.NONE);
    setVisibility(PropertyAccessor.SETTER, Visibility.NONE);
    setVisibility(PropertyAccessor.CREATOR, Visibility.NONE);
    setVisibility(PropertyAccessor.IS_GETTER, Visibility.NONE);

    // ignore unknown fields when reading
    configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    // ignore null fields when writing
    setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}
