package org.sonatype.nexus.internal.httpclient

import org.sonatype.goodies.common.Time
import org.sonatype.goodies.testsupport.TestSupport
import org.sonatype.nexus.httpclient.config.AuthenticationConfiguration
import org.sonatype.nexus.httpclient.config.UsernameAuthenticationConfiguration
import org.sonatype.nexus.security.PasswordHelper

import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import groovy.transform.ToString
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

import static org.mockito.Mockito.*

/**
 * Tests for {@link AuthenticationConfigurationDeserializer}.
 */
class AuthenticationConfigurationDeserializerTest
    extends TestSupport
{
  @Mock
  PasswordHelper passwordHelper

  private ObjectMapper objectMapper

  @Before
  void setUp() {
    when(passwordHelper.encrypt(anyString())).then({ it.arguments[0] != null ? 'encrypted:' + it.arguments[0] : null })
    when(passwordHelper.tryDecrypt(anyString())).then({ it.arguments[0] ==~ /encrypted:.*/ ? it.arguments[0].substring(10) : it.arguments[0] })
    objectMapper = new ObjectMapper().registerModule(
        new SimpleModule().addSerializer(
            Time.class,
            new SecondsSerializer()
        ).addDeserializer(
            Time.class,
            new SecondsDeserializer()
        ).addSerializer(
            AuthenticationConfiguration.class,
            new AuthenticationConfigurationSerializer(passwordHelper)
        ).addDeserializer(
            AuthenticationConfiguration.class,
            new AuthenticationConfigurationDeserializer(passwordHelper)
        )
    )
  }

  @ToString
  static class AuthContainer
  {
    AuthenticationConfiguration auth
  }

  @Test
  void 'read username'() {
    def example = new AuthContainer(auth:
        new UsernameAuthenticationConfiguration(username: 'admin', password: 'admin123')
    )

    def json = objectMapper.writeValueAsString(example)
    log json

    assert json.contains('username')
    assert json.contains('admin')
    assert json.contains('password')
    assert json.contains('encrypted:admin123')

    def obj = objectMapper.readValue(json, AuthContainer.class)
    log obj

    assert obj != null
    assert obj.auth != null
    assert obj.auth instanceof UsernameAuthenticationConfiguration
    assert obj.auth.type == UsernameAuthenticationConfiguration.TYPE

    UsernameAuthenticationConfiguration target = obj.auth as UsernameAuthenticationConfiguration
    assert target.username == 'admin'
    assert target.password == 'admin123'
  }

  @Test(expected = JsonMappingException.class)
  void 'invalid type'() {
    def json = '{"auth":{"type":"invalid"}}'
    objectMapper.readValue(json, AuthContainer.class)
  }
}
