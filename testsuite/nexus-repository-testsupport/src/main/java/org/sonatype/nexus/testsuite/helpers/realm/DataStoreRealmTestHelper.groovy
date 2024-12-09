package org.sonatype.nexus.testsuite.helpers.realm

import javax.inject.Named
import javax.inject.Singleton

@Named
@Singleton
class DataStoreRealmTestHelper
    implements RealmTestHelper
{
  @Override
  List<String> getAvailableRealms() {
    ['Crowd Realm', 'Default Role Realm', 'Docker Bearer Token Realm',
     'LDAP Realm', 'npm Bearer Token Realm', 'NuGet API-Key Realm', 'Rut Auth Realm', 'SAML Realm', 'User Token Realm']
  }
}
