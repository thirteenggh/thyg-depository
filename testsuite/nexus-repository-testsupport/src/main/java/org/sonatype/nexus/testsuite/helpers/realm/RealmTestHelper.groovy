package org.sonatype.nexus.testsuite.helpers.realm

interface RealmTestHelper
{
  // This method should be removed when the number of available Realms on the
  // Realm Configuration page are the same for both Orient and New DB.
  List<String> getAvailableRealms();
}
