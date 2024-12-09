package org.sonatype.nexus.pax.exam;

import java.io.File;
import java.io.IOException;

import org.ops4j.pax.exam.ExamSystem;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.RelativeTimeout;
import org.ops4j.pax.exam.TestProbeBuilder;

/**
 * Simple delegating {@link ExamSystem}.
 *
 * @since 3.14
 */
public class DelegatingExamSystem
    implements ExamSystem
{
  private final ExamSystem delegate;

  public DelegatingExamSystem(final ExamSystem delegate) {
    this.delegate = delegate;
  }

  @Override
  public <T extends Option> T getSingleOption(final Class<T> optionType) {
    return delegate.getSingleOption(optionType);
  }

  @Override
  public <T extends Option> T[] getOptions(final Class<T> optionType) {
    return delegate.getOptions(optionType);
  }

  @Override
  public ExamSystem fork(final Option[] options) {
    return delegate.fork(options);
  }

  @Override
  public File getConfigFolder() {
    return delegate.getConfigFolder();
  }

  @Override
  public File getTempFolder() {
    return delegate.getTempFolder();
  }

  @Override
  public RelativeTimeout getTimeout() {
    return delegate.getTimeout();
  }

  @Override
  public TestProbeBuilder createProbe() throws IOException {
    return delegate.createProbe();
  }

  @Override
  public String createID(final String purposeText) {
    return delegate.createID(purposeText);
  }

  @Override
  public void clear() {
    delegate.clear();
  }
}
