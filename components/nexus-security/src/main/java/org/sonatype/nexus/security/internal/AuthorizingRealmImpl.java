package org.sonatype.nexus.security.internal;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.security.role.RoleIdentifier;
import org.sonatype.nexus.security.user.RoleMappingUserManager;
import org.sonatype.nexus.security.user.UserManager;
import org.sonatype.nexus.security.user.UserNotFoundException;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Sha1Hash;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.eclipse.sisu.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default {@link AuthorizingRealm}.
 *
 * This realm ONLY handles authorization.
 */
@Singleton
@Named(AuthorizingRealmImpl.NAME)
@Description("Local Authorizing Realm")
public class AuthorizingRealmImpl
    extends AuthorizingRealm
    implements Realm
{
  private static final Logger logger = LoggerFactory.getLogger(AuthorizingRealmImpl.class);

  public static final String NAME = "NexusAuthorizingRealm";

  private final RealmSecurityManager realmSecurityManager;

  private final UserManager userManager;

  private final Map<String, UserManager> userManagerMap;

  @Inject
  public AuthorizingRealmImpl(final RealmSecurityManager realmSecurityManager,
                              final UserManager userManager,
                              final Map<String, UserManager> userManagerMap)
  {
    this.realmSecurityManager = realmSecurityManager;
    this.userManager = userManager;
    this.userManagerMap = userManagerMap;
    HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
    credentialsMatcher.setHashAlgorithmName(Sha1Hash.ALGORITHM_NAME);
    setCredentialsMatcher(credentialsMatcher);
    setName(NAME);
    setAuthenticationCachingEnabled(false); // we authz only, no authc done by this realm
    setAuthorizationCachingEnabled(true);
  }

  @Override
  public boolean supports(final AuthenticationToken token) {
    return false;
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(final AuthenticationToken token) {
    return null;
  }

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(final PrincipalCollection principals) {
    if (principals == null) {
      throw new AuthorizationException("Cannot authorize with no principals.");
    }

    String username = principals.getPrimaryPrincipal().toString();
    Set<String> roles = new HashSet<String>();

    Set<String> realmNames = new HashSet<String>(principals.getRealmNames());

    // if the user belongs to this realm, we are most likely using this realm stand alone, or for testing
    if (!realmNames.contains(this.getName())) {
      // make sure the realm is enabled
      Collection<Realm> configureadRealms = realmSecurityManager.getRealms();
      boolean foundRealm = false;
      for (Realm realm : configureadRealms) {
        if (realmNames.contains(realm.getName())) {
          foundRealm = true;
          break;
        }
      }
      if (!foundRealm) {
        // user is from a realm that is NOT enabled
        throw new AuthorizationException("User for principals: " + principals.getPrimaryPrincipal()
            + " belongs to a disabled realm(s): " + principals.getRealmNames() + ".");
      }
    }

    cleanUpRealmList(realmNames);

    if (RoleMappingUserManager.class.isInstance(userManager)) {
      for (String realmName : realmNames) {
        try {
          for (RoleIdentifier roleIdentifier : ((RoleMappingUserManager) userManager).getUsersRoles(username,
              realmName)) {
            roles.add(roleIdentifier.getRoleId());
          }
        }
        catch (UserNotFoundException e) {
          logger.trace("Failed to find role mappings for user: {} realm: {}", username, realmName);
        }
      }
    }
    else if (realmNames.contains("default")) {
      try {
        for (RoleIdentifier roleIdentifier : userManager.getUser(username).getRoles()) {
          roles.add(roleIdentifier.getRoleId());
        }
      }
      catch (UserNotFoundException e) {
        throw new AuthorizationException("User for principals: " + principals.getPrimaryPrincipal()
            + " could not be found.", e);
      }

    }
    else
    // user not managed by this Realm
    {
      throw new AuthorizationException("User for principals: " + principals.getPrimaryPrincipal()
          + " not manged by Trust Repository realm.");
    }

    return new SimpleAuthorizationInfo(roles);
  }

  private void cleanUpRealmList(final Set<String> realmNames) {
    for (UserManager userManager : this.userManagerMap.values()) {
      String authRealmName = userManager.getAuthenticationRealmName();
      if (authRealmName != null && realmNames.contains(authRealmName)) {
        realmNames.remove(authRealmName);
        realmNames.add(userManager.getSource());
      }
    }

    if (realmNames.contains(getName())) {
      realmNames.remove(getName());
      realmNames.add("default");
    }
  }
}
