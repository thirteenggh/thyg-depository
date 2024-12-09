package org.sonatype.nexus.testsuite.helpers.realm

import javax.annotation.Priority
import javax.inject.Named
import javax.inject.Singleton

import org.sonatype.nexus.common.app.FeatureFlag

@FeatureFlag(name = "nexus.orient.store.content")
@Named("orient")
@Singleton
@Priority(Integer.MAX_VALUE)
class OrientRealmTestHelper
    implements RealmTestHelper
{

  // The returned list should be inlined in the RealmConfigIT when the number of available Realms on the
  // Realm Configuration page are the same for both Orient and New DB.
  @Override
  List<String> getAvailableRealms() {
    ['Conan Bearer Token Realm', 'Crowd Realm', 'Default Role Realm', 'Docker Bearer Token Realm',
     'LDAP Realm', 'npm Bearer Token Realm', 'NuGet API-Key Realm', 'Rut Auth Realm', 'SAML Realm',
     'User Token Realm']
  }
}
