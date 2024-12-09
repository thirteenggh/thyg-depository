package org.sonatype.nexus.pax.exam;

import java.io.File;

import org.ops4j.pax.exam.ExamSystem;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.TestContainer;
import org.ops4j.pax.exam.TestContainerFactory;
import org.ops4j.pax.exam.karaf.container.internal.KarafTestContainerFactory;

import static org.sonatype.nexus.pax.exam.TestCounters.nextInstallDirectory;

/**
 * {@link TestContainerFactory} that injects a custom install location into the {@link ExamSystem}.
 *
 * Uses {@link TestCounters} to track a sequence of numeric directories, one per NXRM installation.
 *
 * @since 3.14
 */
public class NexusPaxExamTestFactory
    implements TestContainerFactory
{
  @Override
  public TestContainer[] create(final ExamSystem system) {
    return new KarafTestContainerFactory().create(
        // Pax-Exam ITs call fork just before unpacking each container instance
        new DelegatingExamSystem(system)
        {
          @Override
          public ExamSystem fork(final Option[] options) {
            final File installDir = nextInstallDirectory();
            return new DelegatingExamSystem(super.fork(options))
            {
              @Override
              public File getConfigFolder() {
                return installDir;
              }
            };
          }
        });
  }
}
