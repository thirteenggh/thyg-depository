package org.sonatype.nexus.repository.json;

import java.io.IOException;

import org.sonatype.goodies.testsupport.TestSupport;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.std.MapDeserializer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static com.fasterxml.jackson.core.JsonToken.START_OBJECT;
import static com.google.common.collect.Maps.newHashMap;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MapDeserializerSerializerTest
    extends TestSupport
{
  @Mock
  private MapDeserializer rootDeserializer;

  @Mock
  private ValueInstantiator valueInstantiator;

  @Mock
  private UntypedObjectDeserializerSerializer untypedObjectDeserializerSerializer;

  @Mock
  private JsonParser parser;

  @Mock
  private DeserializationContext context;

  private MapDeserializerSerializer underTest;

  @Before
  public void setUp() {
    when(rootDeserializer.getValueInstantiator()).thenReturn(valueInstantiator);
    when(valueInstantiator.canCreateUsingDefault()).thenReturn(true);

    underTest = new MapDeserializerSerializer(rootDeserializer, untypedObjectDeserializerSerializer);
  }

  @Test
  public void should_Use_ValueType_From_RootDeserializer() {
    verify(rootDeserializer).getValueType();
  }

  @Test
  public void should_Use_ValueInstantiator_From_RootDeserializer() {
    verify(rootDeserializer).getValueInstantiator();
  }

  @Test
  public void should_Use_UntypedObjectDeserializerSerializer() throws IOException {
    when(parser.getCurrentToken()).thenReturn(START_OBJECT);
    when(parser.getCurrentName()).thenReturn("currentFieldName");
    when(parser.nextFieldName()).thenReturn(null);
    when(valueInstantiator.createUsingDefault(context)).thenReturn(newHashMap());

    underTest.deserialize(parser, context);

    verify(untypedObjectDeserializerSerializer).deserialize(parser, context);
  }
}
