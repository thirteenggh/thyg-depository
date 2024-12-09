package org.sonatype.nexus.security

import org.sonatype.nexus.security.anonymous.AnonymousConfiguration

class TestAnonymousConfiguration implements AnonymousConfiguration
{
  boolean enabled
  String realmName
  String userId

  AnonymousConfiguration copy() {
    return this
  }
}
