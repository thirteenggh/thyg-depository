package org.sonatype.nexus.security.user;

/**
 * Read-only {@link UserManager}, which just throws exceptions for all the write methods.
 *
 * Any call to theses methods should be guarded by {@code #supportsWrite}.
 */
public abstract class AbstractReadOnlyUserManager
    extends AbstractUserManager
{
  /**
   * @return Always {@code false}
   */
  @Override
  public boolean supportsWrite() {
    return false;
  }

  @Override
  public User addUser(final User user, final String password) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void changePassword(final String userId, final String newPassword) throws UserNotFoundException {
    throw new UnsupportedOperationException();
  }

  @Override
  public void deleteUser(final String userId) throws UserNotFoundException {
    throw new UnsupportedOperationException();
  }

  @Override
  public User updateUser(final User user) throws UserNotFoundException {
    throw new UnsupportedOperationException();
  }
}
