package org.sonatype.nexus.internal.httpclient;

import java.io.IOException;

import org.sonatype.nexus.httpclient.config.AuthenticationConfiguration;
import org.sonatype.nexus.httpclient.config.BearerTokenAuthenticationConfiguration;
import org.sonatype.nexus.httpclient.config.NtlmAuthenticationConfiguration;
import org.sonatype.nexus.httpclient.config.UsernameAuthenticationConfiguration;
import org.sonatype.nexus.security.PasswordHelper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static org.sonatype.nexus.httpclient.config.AuthenticationConfiguration.TYPES;

/**
 * {@link AuthenticationConfiguration} deserializer.
 *
 * Determines the instance class by the {@code type} field and decrypts sensitive data.
 *
 * @since 3.0
 */
public class AuthenticationConfigurationDeserializer
    extends StdDeserializer<AuthenticationConfiguration>
{
  private final PasswordHelper passwordHelper;

  public AuthenticationConfigurationDeserializer(final PasswordHelper passwordHelper) {
    super(AuthenticationConfiguration.class);
    this.passwordHelper = checkNotNull(passwordHelper);
  }

  @Override
  public AuthenticationConfiguration deserialize(final JsonParser parser, final DeserializationContext context)
      throws IOException
  {
    return deserialize(parser);
  }

  @Override
  public AuthenticationConfiguration deserializeWithType(final JsonParser parser,
                                                         final DeserializationContext context,
                                                         final TypeDeserializer typeDeserializer)
      throws IOException
  {
    return deserialize(parser);
  }

  private AuthenticationConfiguration deserialize(final JsonParser parser) throws IOException {
    JsonNode node = parser.readValueAsTree();
    String typeName = node.get("type").textValue();
    Class<? extends AuthenticationConfiguration> type = TYPES.get(typeName);
    checkState(type != null, "Unknown %s type: %s", AuthenticationConfiguration.class.getSimpleName(), typeName);
    AuthenticationConfiguration configuration = parser.getCodec().treeToValue(node, type);
    if (UsernameAuthenticationConfiguration.class.equals(type)) {
      UsernameAuthenticationConfiguration upc = (UsernameAuthenticationConfiguration) configuration;
      upc.setUsername(upc.getUsername());
      upc.setPassword(passwordHelper.tryDecrypt(upc.getPassword()));
    }
    else if (NtlmAuthenticationConfiguration.class.equals(type)) {
      NtlmAuthenticationConfiguration ntc = (NtlmAuthenticationConfiguration) configuration;
      ntc.setUsername(ntc.getUsername());
      ntc.setPassword(passwordHelper.tryDecrypt(ntc.getPassword()));
      ntc.setDomain(ntc.getDomain());
      ntc.setHost(ntc.getHost());
    }
    else if (BearerTokenAuthenticationConfiguration.class.equals(type)) {
      BearerTokenAuthenticationConfiguration btac = (BearerTokenAuthenticationConfiguration) configuration;
      btac.setBearerToken(passwordHelper.tryDecrypt(btac.getBearerToken()));
    }
    return configuration;
  }
}
