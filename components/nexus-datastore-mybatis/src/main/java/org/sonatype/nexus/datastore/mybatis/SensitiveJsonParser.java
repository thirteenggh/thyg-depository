package org.sonatype.nexus.datastore.mybatis;

import java.io.IOException;
import java.util.function.Predicate;

import org.sonatype.nexus.security.PasswordHelper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.util.JsonParserDelegate;

import static com.fasterxml.jackson.core.JsonToken.VALUE_STRING;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link JsonParser} wrapper that decrypts the values of sensitive attributes.
 *
 * @since 3.21
 */
final class SensitiveJsonParser
    extends JsonParserDelegate
{
  private final PasswordHelper passwordHelper;

  private final Predicate<String> attributeFilter;

  SensitiveJsonParser(final JsonParser delegate,
                      final PasswordHelper passwordHelper,
                      final Predicate<String> atributeFilter)
  {
    super(delegate);
    this.passwordHelper = checkNotNull(passwordHelper);
    this.attributeFilter = checkNotNull(atributeFilter);
  }

  @Override
  public String getText() throws IOException {
    String text = super.getText();
    if (text != null && currentToken() == VALUE_STRING && isSensitiveAttribute()) {
      text = passwordHelper.decrypt(text);
    }
    return text;
  }

  private boolean isSensitiveAttribute() {
    String attributName = getParsingContext().getCurrentName();
    return attributName != null && attributeFilter.test(attributName);
  }
}
