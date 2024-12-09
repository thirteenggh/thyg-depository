package org.sonatype.nexus.internal.security.upgrade;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.sonatype.nexus.common.upgrade.Upgrades;
import org.sonatype.nexus.orient.DatabaseInstance;
import org.sonatype.nexus.orient.DatabaseInstanceNames;
import org.sonatype.nexus.orient.DatabaseUpgradeSupport;
import org.sonatype.nexus.orient.OClassNameBuilder;

import com.google.common.annotations.VisibleForTesting;
import com.orientechnologies.orient.core.sql.OCommandSQL;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Upgrade step to mark admin user as 'changepassword' so user must change it
 *
 * @since 3.17
 */
@Named
@Singleton
@Upgrades(model = DatabaseInstanceNames.SECURITY, from = "1.1", to = "1.2")
public class SecurityDatabaseUpgrade_1_2 // NOSONAR
    extends DatabaseUpgradeSupport
{
  @VisibleForTesting
  static final String DB_CLASS = new OClassNameBuilder().type("user").build();

  @VisibleForTesting
  static final String ADMIN_PASS = "$shiro1$SHA-512$1024$NE+wqQq/TmjZMvfI7ENh/g==$V4yPw8T64UQ6GfJfxYq2hLsVrBY8D1v+bktfOxGdt4b/9BthpWPNUy/CBk6V9iA0nHpzYzJFWO8v/tZFtES8CA==";

  private static final String QUERY = String
      .format("UPDATE %s SET status = 'changepassword' where id = 'admin' and password = ?", DB_CLASS);

  private final Provider<DatabaseInstance> securityDatabaseInstance;

  @Inject
  public SecurityDatabaseUpgrade_1_2(@Named(DatabaseInstanceNames.SECURITY) final Provider<DatabaseInstance> securityDatabaseInstance) {
    this.securityDatabaseInstance = checkNotNull(securityDatabaseInstance);
  }

  @Override
  public void apply() {
    withDatabaseAndClass(securityDatabaseInstance, DB_CLASS, (db, type) -> {
      int updates = db.command(new OCommandSQL(QUERY)).execute(ADMIN_PASS);
      if (updates > 0) {
        log.info("Updated admin user status to 'changepassword'.");
      }
    });
  }
}
