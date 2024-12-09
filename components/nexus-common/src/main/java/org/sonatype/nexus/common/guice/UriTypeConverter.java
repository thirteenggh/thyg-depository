package org.sonatype.nexus.common.guice;

import java.net.URI;

import javax.inject.Named;

import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeConverter;

/**
 * Guice {@link TypeConverter} for {@link URI} instances.
 *
 * @since 3.0
 */
@Named
public class UriTypeConverter
    extends TypeConverterSupport<URI>
{
  @Override
  protected Object doConvert(final String value, final TypeLiteral<?> toType) throws Exception {
    return new URI(value);
  }
}
