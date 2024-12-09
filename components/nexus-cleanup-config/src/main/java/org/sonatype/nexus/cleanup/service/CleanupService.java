package org.sonatype.nexus.cleanup.service;

import java.util.function.BooleanSupplier;

/**
 * Runs cleanup by looping through all repositories and evaluating their cleanup policies.
 * 
 * @since 3.14
 */
public interface CleanupService
{
  void cleanup(BooleanSupplier cancelledCheck);
}
