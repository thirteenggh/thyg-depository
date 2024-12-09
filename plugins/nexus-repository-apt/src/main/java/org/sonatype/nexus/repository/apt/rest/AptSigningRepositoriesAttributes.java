package org.sonatype.nexus.repository.apt.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @since 3.20
 */
public class AptSigningRepositoriesAttributes
{
  @ApiModelProperty(value = "PGP signing key pair (armored private key e.g. gpg --export-secret-key --armor)",
      example = "")
  @NotEmpty
  private final String keypair;

  @ApiModelProperty(value = "Passphrase to access PGP signing key", example = "")
  private final String passphrase;

  @JsonCreator
  public AptSigningRepositoriesAttributes(
      @JsonProperty("keypair") final String keypair,
      @JsonProperty("passphrase") final String passphrase)
  {
    this.keypair = keypair;
    this.passphrase = passphrase;
  }

  public String getKeypair() {
    return keypair;
  }

  public String getPassphrase() {
    return passphrase;
  }
}
