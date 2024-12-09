package org.sonatype.nexus.email;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;

/**
 * Email manager.
 *
 * @since 3.0
 */
public interface EmailManager
{
  /**
   * Returns copy of current email configuration.
   */
  EmailConfiguration getConfiguration();

  /**
   * Installs new email configuration.
   */
  void setConfiguration(EmailConfiguration configuration);

  /**
   * Send an email.
   */
  void send(Email mail) throws EmailException;

  /**
   * Send verification email to given address.
   */
  void sendVerification(EmailConfiguration configuration, String address) throws EmailException;

  /**
   * Create a new and empty {@link EmailConfiguration}
   * @since 3.20
   */
  EmailConfiguration newConfiguration();
}
