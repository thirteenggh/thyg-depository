package org.sonatype.nexus.orient.testsupport;

import com.orientechnologies.common.exception.OException;
import com.orientechnologies.orient.core.db.ODatabaseRecordThreadLocal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Helper class to simplify mocking of Orient exceptions.
 *
 * @since 3.2
 */
public class OrientExceptionMocker
{
  private OrientExceptionMocker() {
    // empty
  }

  /**
   * Mocks the given Orient exception type so it can participate in unit tests without
   * depending on internal state such as {@link ODatabaseRecordThreadLocal#INSTANCE}.
   */
  public static <E extends OException> E mockOrientException(final Class<E> exceptionType) {
    E exception = mock(exceptionType);
    when(exception.getStackTrace()).thenReturn(new StackTraceElement[0]);
    return exception;
  }
}
