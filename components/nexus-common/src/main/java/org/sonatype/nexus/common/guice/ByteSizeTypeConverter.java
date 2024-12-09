package org.sonatype.nexus.common.guice;

import javax.inject.Named;

import org.sonatype.goodies.common.ByteSize;

import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeConverter;

/**
 * Guice {@link TypeConverter} for {@link ByteSize} instances.
 *
 * @since 3.0
 */
@Named
public class ByteSizeTypeConverter
    extends TypeConverterSupport<ByteSize>
{
  @Override
  protected Object doConvert(final String value, final TypeLiteral<?> toType) throws Exception {
    return ByteSize.parse(value);
  }
}
