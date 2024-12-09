package org.sonatype.nexus.datastore.mybatis;

import java.io.IOException;
import java.io.OutputStream;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import org.sonatype.nexus.security.PasswordHelper;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Predicates.alwaysFalse;

/**
 * Supplies JSON parsers and generators that can encrypt/decrypt string values of sensitive attributes.
 *
 * @since 3.21
 */
final class SensitiveJsonFactory
    extends JsonFactory
{
  private static final long serialVersionUID = 1L;

  @Nullable
  private transient PasswordHelper passwordHelper = null;

  private transient Predicate<String> attributeFilter = alwaysFalse();

  /**
   * Enables automatic encryption of sensitive JSON fields, identified using the given filter.
   */
  @SuppressWarnings("hiding")
  void encryptSensitiveFields(final PasswordHelper passwordHelper, final Predicate<String> attributeFilter) {
    this.passwordHelper = checkNotNull(passwordHelper);
    this.attributeFilter = checkNotNull(attributeFilter);
  }

  @Override
  public JsonGenerator createGenerator(final OutputStream out, final JsonEncoding enc) throws IOException {
    JsonGenerator generator = super.createGenerator(out, enc);
    if (passwordHelper != null) {
      return new SensitiveJsonGenerator(generator, passwordHelper, attributeFilter);
    }
    return generator;
  }

  @Override
  public JsonParser createParser(byte[] data) throws IOException {
    JsonParser parser = super.createParser(data);
    if (passwordHelper != null) {
      return new SensitiveJsonParser(parser, passwordHelper, attributeFilter);
    }
    return parser;
  }

  @Override
  public JsonFactory copy() {
    return new SensitiveJsonFactory(); // there's no state to copy at the time this is called
  }
}
