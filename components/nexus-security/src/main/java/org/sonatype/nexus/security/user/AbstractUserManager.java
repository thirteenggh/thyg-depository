package org.sonatype.nexus.security.user;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.common.text.Strings2;
import org.sonatype.nexus.security.role.RoleIdentifier;

import com.google.common.collect.Sets;

/**
 * An abstract UserManager that handles filtering UserSearchCriteria in memory, this can be used in addition to an
 * external query ( if all the parameters can not be pased to the external source).
 *
 * @author Brian Demers
 */
public abstract class AbstractUserManager
    extends ComponentSupport
    implements UserManager
{
  protected Set<User> filterListInMemeory(final Set<User> users, final UserSearchCriteria criteria) {
    HashSet<User> result = new HashSet<>();

    for (User user : users) {
      if (userMatchesCriteria(user, criteria)) {
        // add the user if it matches the search criteria
        result.add(user);
      }
    }

    return result;
  }

  protected boolean userMatchesCriteria(final User user, final UserSearchCriteria criteria) {
    Set<String> userRoles = new HashSet<>();
    if (user.getRoles() != null) {
      for (RoleIdentifier roleIdentifier : user.getRoles()) {
        userRoles.add(roleIdentifier.getRoleId());
      }
    }

    return matchesCriteria(user.getUserId(), user.getSource(), userRoles, criteria);
  }

  protected boolean matchesCriteria(final String userId,
                                    final String userSource,
                                    final Collection<String> usersRoles,
                                    final UserSearchCriteria criteria)
  {
    if (!Strings2.isBlank(criteria.getUserId())
        && !userId.toLowerCase().startsWith(criteria.getUserId().toLowerCase())) {
      return false;
    }

    if (criteria.getSource() != null && !criteria.getSource().equals(userSource)) {
      return false;
    }

    if (criteria.getOneOfRoleIds() != null && !criteria.getOneOfRoleIds().isEmpty()) {
      Set<String> userRoles = new HashSet<>();
      if (usersRoles != null) {
        userRoles.addAll(usersRoles);
      }

      // check the intersection of the roles
      if (Sets.intersection(criteria.getOneOfRoleIds(), userRoles).isEmpty()) {
        return false;
      }
    }

    return true;
  }
}
