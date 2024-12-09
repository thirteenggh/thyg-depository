package org.sonatype.nexus.testsuite.testsupport.maven

import groovy.util.logging.Slf4j
import org.apache.maven.it.Verifier

/**
 * Exercise maven goals against a project.
 * @since 3.2
 */
@Slf4j
class MavenRunner
{
  void run(MavenDeployment deployment, String... goals) {
    log.debug "Deploying: $deployment"
    Verifier verifier = new Verifier(deployment.projectDir.absolutePath)
    verifier.addCliOption("-s ${deployment.settingsFile().absolutePath}")
    verifier.addCliOption(
        "-DaltDeploymentRepository=local-nexus-admin::default::${deployment.deployUrl}".replace('//', '////'))
    
    log.info("Executing maven goals {}", Arrays.asList(goals))
    verifier.executeGoals(Arrays.asList(goals))
    
    verifier.verifyErrorFreeLog()
    log.debug("Finished running maven from ${deployment.projectDir}")
  }
}
