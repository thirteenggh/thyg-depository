package org.sonatype.nexus.orient;

import java.io.File;
import java.io.PrintStream;
import java.io.StringWriter;

import org.sonatype.goodies.testsupport.TestSupport;

import com.google.common.collect.Lists;
import com.orientechnologies.orient.core.Orient;
import com.orientechnologies.orient.core.config.OGlobalConfiguration;
import com.orientechnologies.orient.server.OServer;
import com.orientechnologies.orient.server.config.OServerConfiguration;
import com.orientechnologies.orient.server.config.OServerEntryConfiguration;
import com.orientechnologies.orient.server.config.OServerNetworkConfiguration;
import com.orientechnologies.orient.server.config.OServerNetworkListenerConfiguration;
import com.orientechnologies.orient.server.config.OServerNetworkProtocolConfiguration;
import com.orientechnologies.orient.server.config.OServerSecurityConfiguration;
import com.orientechnologies.orient.server.config.OServerStorageConfiguration;
import com.orientechnologies.orient.server.config.OServerUserConfiguration;
import com.orientechnologies.orient.server.network.protocol.binary.ONetworkProtocolBinary;
import org.apache.commons.io.output.WriterOutputStream;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.bridge.SLF4JBridgeHandler;

/**
 * Trials of using OrientDB embedded.
 */
@Ignore("Disable for now, as these cause other tests which do not explicitly setup embedding to fail")
public class OrientDbEmbeddedTrial
    extends TestSupport
{
  static {
    SLF4JBridgeHandler.removeHandlersForRootLogger();
    SLF4JBridgeHandler.install();
  }

  /**
   * Tests configuring the server w/o xml but configuring the JAXB objects directly.
   */
  @SuppressWarnings("java:S2699") //sonar wants assertions, but this test is not run in CI
  @Test
  public void embeddedServerProgrammatic() throws Exception {
    File homeDir = util.createTempDir("orientdb-home").getCanonicalFile();
    System.setProperty("orient.home", homeDir.getPath());
    System.setProperty(Orient.ORIENTDB_HOME, homeDir.getPath());

    OServer server = new OServer();
    OServerConfiguration config = new OServerConfiguration();

    // Unsure what this is used for, its apparently assigned to xml location, but forcing it here
    config.location = "DYNAMIC-CONFIGURATION";

    File databaseDir = new File(homeDir, "db");
    config.properties = new OServerEntryConfiguration[] {
        new OServerEntryConfiguration("server.database.path", databaseDir.getPath())
    };

    config.handlers = Lists.newArrayList();

    config.hooks = Lists.newArrayList();

    config.network = new OServerNetworkConfiguration();
    config.network.protocols = Lists.newArrayList(
        new OServerNetworkProtocolConfiguration("binary", ONetworkProtocolBinary.class.getName())
    );

    OServerNetworkListenerConfiguration binaryListener = new OServerNetworkListenerConfiguration();
    binaryListener.ipAddress = "0.0.0.0";
    binaryListener.portRange = "2424-2430";
    binaryListener.protocol = "binary";
    binaryListener.socket = "default";

    config.network.listeners = Lists.newArrayList(
        binaryListener
    );

    config.storages = new OServerStorageConfiguration[] {};

    config.users = new OServerUserConfiguration[] {
        new OServerUserConfiguration("admin", "admin", "*")
    };

    config.security = new OServerSecurityConfiguration();
    config.security.users = Lists.newArrayList();
    config.security.resources = Lists.newArrayList();

    server.startup(config);

    // Dump config to log stream
    StringWriter buff = new StringWriter();
    OGlobalConfiguration.dumpConfiguration(new PrintStream(new WriterOutputStream(buff), true));
    log("Global configuration:\n{}", buff);

    server.activate();
    server.shutdown();
  }
}
