package org.sonatype.nexus.coreui.internal.wonderland;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.common.property.SystemPropertiesHelper;
import org.sonatype.nexus.rest.Resource;

import com.google.common.collect.Lists;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;

/**
 * Expose Nexus settings.
 *
 * @since 2.7
 */
@Named
@Singleton
@Path(SettingsResource.RESOURCE_URI)
public class SettingsResource
    extends ComponentSupport
    implements Resource
{
  public static final String RESOURCE_URI = "/wonderland/settings";

  @GET
  @Produces({APPLICATION_JSON, APPLICATION_XML})
  public List<PropertyXO> get() {
    List<PropertyXO> properties = Lists.newArrayList();

    properties.add(
        new PropertyXO().withKey("keepAlive")
            .withValue(Boolean.toString(SystemPropertiesHelper.getBoolean("nexus.ui.keepAlive", true)))
    );

    return properties;
  }
}
