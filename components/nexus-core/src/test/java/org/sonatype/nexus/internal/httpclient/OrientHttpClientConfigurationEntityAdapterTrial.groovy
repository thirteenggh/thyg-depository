package org.sonatype.nexus.internal.httpclient

import org.sonatype.goodies.common.Time
import org.sonatype.goodies.testsupport.TestSupport
import org.sonatype.nexus.httpclient.config.ConnectionConfiguration
import org.sonatype.nexus.httpclient.config.UsernameAuthenticationConfiguration
import org.sonatype.nexus.internal.httpclient.orient.OrientHttpClientConfigurationEntityAdapter
import org.sonatype.nexus.orient.testsupport.DatabaseInstanceRule

import org.junit.Before
import org.junit.Rule
import org.junit.Test
/**
 * Trials for {@link OrientHttpClientConfigurationEntityAdapter}.
 */
class OrientHttpClientConfigurationEntityAdapterTrial
    extends TestSupport
{
  @Rule
  public DatabaseInstanceRule database = new DatabaseInstanceRule('test')

  private OrientHttpClientConfigurationEntityAdapter underTest

  @Before
  void setUp() {
    underTest = new OrientHttpClientConfigurationEntityAdapter()
  }

  @Test
  void 'set and get simple'() {
    database.instance.connect().withCloseable {db ->
      underTest.register(db)

      def config = new TestHttpClientConfiguration(
          connection: new ConnectionConfiguration(
              timeout: Time.seconds(1234)
          ),
          authentication: new UsernameAuthenticationConfiguration(
              username: 'admin',
              password: 'admin123'
          )
      )
      underTest.singleton.set(db, config)

      config = underTest.singleton.get(db)
      log config
    }
  }
}
