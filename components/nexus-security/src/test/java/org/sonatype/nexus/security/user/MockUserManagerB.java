package org.sonatype.nexus.security.user;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.security.role.RoleIdentifier;

@Singleton
@Named("MockUserManagerB")
public class MockUserManagerB
    extends MockUserManagerSupport
{
  public MockUserManagerB() {
    User a = new User();
    a.setName("Brenda D. Burton");
    a.setEmailAddress("bburton@sonatype.org");
    a.setSource(this.getSource());
    a.setUserId("bburton");
    a.setStatus(UserStatus.active);
    a.addRole(new RoleIdentifier(this.getSource(), "RoleA"));
    a.addRole(new RoleIdentifier(this.getSource(), "RoleB"));
    a.addRole(new RoleIdentifier(this.getSource(), "RoleC"));

    User b = new User();
    b.setName("Julian R. Blevins");
    b.setEmailAddress("jblevins@sonatype.org");
    b.setSource(this.getSource());
    b.setUserId("jblevins");
    b.setStatus(UserStatus.active);
    b.addRole(new RoleIdentifier(this.getSource(), "RoleA"));
    b.addRole(new RoleIdentifier(this.getSource(), "RoleB"));

    User c = new User();
    c.setName("Kathryn J. Simmons");
    c.setEmailAddress("ksimmons@sonatype.org");
    c.setSource(this.getSource());
    c.setUserId("ksimmons");
    c.setStatus(UserStatus.active);
    c.addRole(new RoleIdentifier(this.getSource(), "RoleA"));
    c.addRole(new RoleIdentifier(this.getSource(), "RoleB"));

    User d = new User();
    d.setName("Florence T. Dahmen");
    d.setEmailAddress("fdahmen@sonatype.org");
    d.setSource(this.getSource());
    d.setUserId("fdahmen");
    d.setStatus(UserStatus.active);
    d.addRole(new RoleIdentifier(this.getSource(), "RoleA"));
    d.addRole(new RoleIdentifier(this.getSource(), "RoleB"));

    User e = new User();
    e.setName("Jill  Codar");
    e.setEmailAddress("jcodar@sonatype.org");
    e.setSource(this.getSource());
    e.setUserId("jcodar");
    e.setStatus(UserStatus.active);

    User f = new User();
    f.setName("Joe Coder");
    f.setEmailAddress("jcoder@sonatype.org");
    f.setSource(this.getSource());
    f.setUserId("jcoder");
    f.setStatus(UserStatus.active);
    f.addRole(new RoleIdentifier(this.getSource(), "Role1"));
    f.addRole(new RoleIdentifier(this.getSource(), "Role2"));
    f.addRole(new RoleIdentifier(this.getSource(), "Role3"));

    this.addUser(a, a.getUserId());
    this.addUser(b, b.getUserId());
    this.addUser(c, c.getUserId());
    this.addUser(d, d.getUserId());
    this.addUser(e, e.getUserId());
    this.addUser(f, f.getUserId());
  }

  public String getSource() {
    return "MockUserManagerB";
  }

  public String getAuthenticationRealmName() {
    return "MockRealmB";
  }
}
