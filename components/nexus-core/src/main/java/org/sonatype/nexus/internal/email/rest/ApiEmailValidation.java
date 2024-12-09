package org.sonatype.nexus.internal.email.rest;

public class ApiEmailValidation
{
  private final boolean success;

  private final String reason;

  public ApiEmailValidation(final boolean success, final String reason) {
    this.success = success;
    this.reason = reason;
  }

  public ApiEmailValidation(final boolean success) {
    this(success, "");
  }

  public boolean isSuccess() {
    return success;
  }

  public String getReason() {
    return reason;
  }
}
