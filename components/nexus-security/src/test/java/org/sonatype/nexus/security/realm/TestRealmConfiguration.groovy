package org.sonatype.nexus.security.realm

class TestRealmConfiguration
    implements RealmConfiguration
{
  List<String> realmNames

  RealmConfiguration copy() {
    return this
  }
}
