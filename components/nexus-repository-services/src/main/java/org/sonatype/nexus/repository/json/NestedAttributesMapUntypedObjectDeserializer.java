package org.sonatype.nexus.repository.json;

import java.io.IOException;

import org.sonatype.nexus.common.collect.NestedAttributesMap;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.UntypedObjectDeserializer;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Implementation of {@link UntypedObjectDeserializer} with using a {@link NestedAttributesMap} as a root map to
 * get existing deserialized references from.
 *
 * @since 3.16
 */
public class NestedAttributesMapUntypedObjectDeserializer
    extends UntypedObjectDeserializer
{
  private NestedAttributesMapJsonParser jsonParser;

  public NestedAttributesMapUntypedObjectDeserializer(final NestedAttributesMapJsonParser jsonParser) {
    super(null, null); // using this null, null constructor like the default deprecated one
    this.jsonParser = checkNotNull(jsonParser);
  }

  /**
   * Overridden to mark when we are mapping inside an array. The marker is available for internal checking.
   */
  @Override
  protected Object mapArray(final JsonParser parser, final DeserializationContext context) throws IOException {
    try {
      markMappingInsideArray();
      return super.mapArray(parser, context);
    }
    finally {
      unMarkMappingInsideArray();
    }
  }

  protected NestedAttributesMap getChildFromRoot() {
    return jsonParser.getChildFromRoot();
  }

  protected boolean isMappingInsideArray() {
    return jsonParser.isMappingInsideArray();
  }

  protected String currentPath() {
    return jsonParser.currentPath();
  }

  protected boolean isMappingField(final String name) {
    return jsonParser.currentPath().endsWith(name);
  }

  protected void markMappingInsideArray() {
    jsonParser.markMappingInsideArray();
  }

  protected void unMarkMappingInsideArray() {
    jsonParser.unMarkMappingInsideArray();
  }

  protected boolean isDefaultMapping() {
    return jsonParser.isDefaultMapping();
  }

  protected void enableDefaultMapping() {
    jsonParser.enableDefaultMapping();
  }

  protected void disableDefaultMapping() {
    jsonParser.disableDefaultMapping();
  }
}
