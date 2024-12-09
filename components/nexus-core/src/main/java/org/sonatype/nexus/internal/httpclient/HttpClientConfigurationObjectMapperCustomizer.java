package org.sonatype.nexus.internal.httpclient;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.Time;
import org.sonatype.nexus.httpclient.config.AuthenticationConfiguration;
import org.sonatype.nexus.repository.config.ConfigurationObjectMapperCustomizer;
import org.sonatype.nexus.security.PasswordHelper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * HTTP-client specific {@link ConfigurationObjectMapperCustomizer} that registers custom deserializer
 * with {@link ObjectMapper}.
 *
 * @see AuthenticationConfigurationDeserializer
 * @since 3.0
 */
@Named
@Singleton
public class HttpClientConfigurationObjectMapperCustomizer
    implements ConfigurationObjectMapperCustomizer
{
  private final PasswordHelper passwordHelper;

  @Inject
  public HttpClientConfigurationObjectMapperCustomizer(final PasswordHelper passwordHelper) {
    this.passwordHelper = checkNotNull(passwordHelper);
  }

  @Override
  public void customize(final ObjectMapper objectMapper) {
    objectMapper.registerModule(
        new SimpleModule()
            .addSerializer(
                Time.class,
                new SecondsSerializer()
            )
            .addDeserializer(
                Time.class,
                new SecondsDeserializer()
            )
            .addSerializer(
                AuthenticationConfiguration.class,
                new AuthenticationConfigurationSerializer(passwordHelper)
            )
            .addDeserializer(
                AuthenticationConfiguration.class,
                new AuthenticationConfigurationDeserializer(passwordHelper)
            )
    );
  }
}
