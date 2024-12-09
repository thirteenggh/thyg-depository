package org.sonatype.nexus.security.realm.api;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @since 3.20
 */
@Api(value = "Security management: realms")
public interface RealmApiResourceDoc
{
  @ApiOperation("List the available realms")
  List<RealmApiXO> getRealms();

  @ApiOperation("List the active realm IDs in order")
  List<String> getActiveRealms();

  @ApiOperation("Set the active security realms in the order they should be used")
  void setActiveRealms(@ApiParam("The realm IDs") List<String> realmIds);
}
