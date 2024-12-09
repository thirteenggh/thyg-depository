package org.sonatype.nexus.security.realm;

import org.sonatype.nexus.security.AbstractSecurityTest;
import org.sonatype.nexus.security.SecuritySystem;
import org.sonatype.nexus.security.user.User;

import com.google.common.collect.ImmutableList;
import org.junit.Assert;
import org.junit.Test;

public class OrderingRealmsTest
    extends AbstractSecurityTest
{
  @Test
  public void testOrderedGetUser() throws Exception {
    SecuritySystem securitySystem = this.lookup(SecuritySystem.class);
    RealmManager realmManager = lookup(RealmManager.class);
    RealmConfiguration realmConfiguration;

    realmConfiguration = new TestRealmConfiguration();
    realmConfiguration.setRealmNames(ImmutableList.of("MockRealmA", "MockRealmB"));
    realmManager.setConfiguration(realmConfiguration);

    User jcoder = securitySystem.getUser("jcoder");
    Assert.assertNotNull(jcoder);

    // make sure jcoder is from MockUserManagerA
    Assert.assertEquals("MockUserManagerA", jcoder.getSource());

    // now change the order
    realmConfiguration = new TestRealmConfiguration();
    realmConfiguration.setRealmNames(ImmutableList.of("MockRealmB", "MockRealmA")); // order changed
    realmManager.setConfiguration(realmConfiguration);

    jcoder = securitySystem.getUser("jcoder");
    Assert.assertNotNull(jcoder);

    // make sure jcoder is from MockUserManagerA
    Assert.assertEquals("MockUserManagerB", jcoder.getSource());
  }
}
