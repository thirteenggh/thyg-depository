package org.sonatype.nexus.internal.httpclient;

import java.io.IOException;

import org.sonatype.nexus.httpclient.config.AuthenticationConfiguration;
import org.sonatype.nexus.httpclient.config.BearerTokenAuthenticationConfiguration;
import org.sonatype.nexus.httpclient.config.NtlmAuthenticationConfiguration;
import org.sonatype.nexus.httpclient.config.UsernameAuthenticationConfiguration;
import org.sonatype.nexus.security.PasswordHelper;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link AuthenticationConfiguration} serializer.
 * <p>
 * Encrypts sensitive data.
 *
 * @since 3.0
 */
public class AuthenticationConfigurationSerializer
    extends StdSerializer<AuthenticationConfiguration>
{
  private final PasswordHelper passwordHelper;

  public AuthenticationConfigurationSerializer(final PasswordHelper passwordHelper) {
    super(AuthenticationConfiguration.class);
    this.passwordHelper = checkNotNull(passwordHelper);
  }

  @Override
  public void serialize(final AuthenticationConfiguration value,
                        final JsonGenerator jgen,
                        final SerializerProvider provider)
      throws IOException
  {
    serialize(value, jgen);
    jgen.writeEndObject();
  }

  @Override
  public void serializeWithType(final AuthenticationConfiguration value,
                                final JsonGenerator jgen,
                                final SerializerProvider provider,
                                final TypeSerializer typeSer)
      throws IOException
  {
    serialize(value, jgen);
    if (value instanceof UsernameAuthenticationConfiguration) {
      jgen.writeStringField(typeSer.getPropertyName(), UsernameAuthenticationConfiguration.TYPE);
    }
    else if (value instanceof NtlmAuthenticationConfiguration) {
      jgen.writeStringField(typeSer.getPropertyName(), NtlmAuthenticationConfiguration.TYPE);
    }
    else if (value instanceof BearerTokenAuthenticationConfiguration) {
      jgen.writeStringField(typeSer.getPropertyName(), BearerTokenAuthenticationConfiguration.TYPE);
    }
    else {
      // be foolproof, if new type added but this class is not updated
      throw new JsonGenerationException("Unsupported type:" + value.getClass().getName(), jgen);
    }
    jgen.writeEndObject();
  }

  private void serialize(final AuthenticationConfiguration value, final JsonGenerator jgen) throws IOException {
    jgen.writeStartObject();
    jgen.writeStringField("type", value.getType());
    if (value instanceof UsernameAuthenticationConfiguration) {
      UsernameAuthenticationConfiguration upc = (UsernameAuthenticationConfiguration) value;
      jgen.writeStringField("username", upc.getUsername());
      jgen.writeStringField("password", passwordHelper.encrypt(upc.getPassword()));
    }
    else if (value instanceof NtlmAuthenticationConfiguration) {
      NtlmAuthenticationConfiguration ntc = (NtlmAuthenticationConfiguration) value;
      jgen.writeStringField("username", ntc.getUsername());
      jgen.writeStringField("password", passwordHelper.encrypt(ntc.getPassword()));
      jgen.writeStringField("domain", ntc.getDomain());
      jgen.writeStringField("host", ntc.getHost());
    }
    else if (value instanceof BearerTokenAuthenticationConfiguration) {
      BearerTokenAuthenticationConfiguration btac = (BearerTokenAuthenticationConfiguration) value;
      jgen.writeStringField(BearerTokenAuthenticationConfiguration.TYPE, passwordHelper.encrypt(btac.getBearerToken()));
    }
    else {
      // be foolproof, if new type added but this class is not updated
      throw new JsonGenerationException("Unsupported type:" + value.getClass().getName());
    }
  }
}
