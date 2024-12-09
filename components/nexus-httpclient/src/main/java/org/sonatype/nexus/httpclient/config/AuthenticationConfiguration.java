package org.sonatype.nexus.httpclient.config;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.common.collect.ImmutableMap;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Authentication configuration.
 *
 * @since 3.0
 */
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "type")
@JsonSubTypes({
    @Type(value = BearerTokenAuthenticationConfiguration.class, name = BearerTokenAuthenticationConfiguration.TYPE),
    @Type(value = NtlmAuthenticationConfiguration.class, name = NtlmAuthenticationConfiguration.TYPE),
    @Type(value = UsernameAuthenticationConfiguration.class, name = UsernameAuthenticationConfiguration.TYPE)
})
public abstract class AuthenticationConfiguration
    implements Cloneable
{
  /**
   * Mapping of type-name to type-class used by deserializers. If you add new type of auth config, this map needs
   * to be updated as well.
   */
  public static final Map<String, Class<? extends AuthenticationConfiguration>> TYPES = ImmutableMap.of(
      UsernameAuthenticationConfiguration.TYPE, UsernameAuthenticationConfiguration.class,
      NtlmAuthenticationConfiguration.TYPE, NtlmAuthenticationConfiguration.class,
      BearerTokenAuthenticationConfiguration.TYPE, BearerTokenAuthenticationConfiguration.class
  );

  private final String type;

  public AuthenticationConfiguration(final String type) {
    this.type = checkNotNull(type);
  }

  public String getType() {
    return type;
  }

  // TODO: preemptive?

  public AuthenticationConfiguration copy() {
    try {
      return (AuthenticationConfiguration) clone();
    }
    catch (CloneNotSupportedException e) {
      throw new RuntimeException(e);
    }
  }
}
