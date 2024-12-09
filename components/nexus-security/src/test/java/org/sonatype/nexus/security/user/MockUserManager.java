package org.sonatype.nexus.security.user;

import java.util.HashSet;
import java.util.Set;

import org.sonatype.nexus.security.role.ExternalRoleMappedTest;
import org.sonatype.nexus.security.role.RoleIdentifier;

/**
 * @see ExternalRoleMappedTest
 * @see UserManagementTest
 */
public class MockUserManager
    extends AbstractReadOnlyUserManager
{
  @Override
  public String getSource() {
    return "Mock";
  }

  @Override
  public String getAuthenticationRealmName() {
    return "Mock";
  }

  @Override
  public Set<User> listUsers() {
    Set<User> users = new HashSet<User>();

    User jcohen = new User();
    jcohen.setEmailAddress("JamesDCohen@example.com");
    jcohen.setFirstName("James");
    jcohen.setLastName("Cohen");
    // jcohen.setName( "James E. Cohen" );
    // jcohen.setReadOnly( true );
    jcohen.setSource("Mock");
    jcohen.setStatus(UserStatus.active);
    jcohen.setUserId("jcohen");
    jcohen.addRole(new RoleIdentifier("Mock", "mockrole1"));
    users.add(jcohen);

    return users;
  }

  @Override
  public Set<String> listUserIds() {
    Set<String> userIds = new HashSet<String>();
    for (User user : this.listUsers()) {
      userIds.add(user.getUserId());
    }
    return userIds;
  }

  @Override
  public Set<User> searchUsers(UserSearchCriteria criteria) {
    return null;
  }

  @Override
  public User getUser(String userId) throws UserNotFoundException {
    for (User user : this.listUsers()) {
      if (user.getUserId().equals(userId)) {
        return user;
      }
    }
    throw new UserNotFoundException(userId);
  }
}
