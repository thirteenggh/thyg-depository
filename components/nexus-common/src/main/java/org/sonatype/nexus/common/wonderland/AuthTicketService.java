package org.sonatype.nexus.common.wonderland;

/**
 * Authentication ticket service.
 *
 * @since 2.7
 */
public interface AuthTicketService
{
  /**
   * Header for passing authentication tickets in via web requests.
   */
  String AUTH_TICKET_HEADER = "X-NX-AuthTicket";

  /**
   * Create a new authentication ticket.
   */
  String createTicket(String user);

  /**
   * Create a new authentication ticket for the currently logged in user.
   */
  String createTicket();

  /**
   * Redeem an authentication ticket.
   *
   * @return True if the authentication ticket was redeemed, else false if the ticket is invalid.
   */
  boolean redeemTicket(String user, String ticket);

  /**
   * Redeem an authentication ticket for the currently logged in user.
   *
   * @return True if the authentication ticket was redeemed, else false if the ticket is invalid.
   */
  boolean redeemTicket(String ticket);
}
