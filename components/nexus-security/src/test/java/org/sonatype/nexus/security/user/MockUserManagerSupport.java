package org.sonatype.nexus.security.user;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class MockUserManagerSupport
    extends AbstractUserManager
{
  private Set<User> users = new HashSet<User>();

  public boolean supportsWrite() {
    return true;
  }

  public User addUser(User user, String password) {
    this.getUsers().add(user);
    return user;
  }

  public User updateUser(User user) throws UserNotFoundException {
    User existingUser = this.getUser(user.getUserId());

    if (existingUser == null) {
      throw new UserNotFoundException(user.getUserId());
    }

    return user;
  }

  public void deleteUser(String userId) throws UserNotFoundException {
    User existingUser = this.getUser(userId);

    if (existingUser == null) {
      throw new UserNotFoundException(userId);
    }

    this.getUsers().remove(existingUser);
  }

  public User getUser(String userId) {
    for (User user : this.getUsers()) {
      if (user.getUserId().equals(userId)) {
        return user;
      }
    }
    return null;
  }

  public Set<String> listUserIds() {
    Set<String> userIds = new HashSet<String>();

    for (User user : this.getUsers()) {
      userIds.add(user.getUserId());
    }

    return userIds;
  }

  public Set<User> listUsers() {
    return Collections.unmodifiableSet(this.getUsers());
  }

  public Set<User> searchUsers(UserSearchCriteria criteria) {
    return this.filterListInMemeory(this.getUsers(), criteria);
  }

  protected Set<User> getUsers() {
    return users;
  }

  public void changePassword(String userId, String newPassword) throws UserNotFoundException {
    // empty
  }
}
