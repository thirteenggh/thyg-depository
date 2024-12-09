package org.sonatype.nexus.common.guice;

import javax.inject.Named;

import org.sonatype.goodies.common.Time;

import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeConverter;

/**
 * Guice {@link TypeConverter} for {@link Time} instances.
 *
 * @since 3.0
 */
@Named
public class TimeTypeConverter
    extends TypeConverterSupport<Time>
{
  @Override
  protected Object doConvert(final String value, final TypeLiteral<?> toType) throws Exception {
    return Time.parse(value);
  }
}
