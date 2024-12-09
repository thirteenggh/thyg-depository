package org.sonatype.nexus.common.collect;

import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Function;

import org.sonatype.goodies.testsupport.TestSupport;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class DetachingListTest
    extends TestSupport
{
  @Mock
  private List<String> backing;

  @Mock
  private BooleanSupplier allowDetach;

  @Mock
  private Function<String, String> detach;

  private DetachingList<String> underTest;

  @Before
  public void setUp() {
    underTest = new DetachingList<>(backing, allowDetach, detach);
  }

  @Test
  public void nonEscapingQueriesNeverDetach() {

    underTest.contains(null);
    underTest.containsAll(null);
    underTest.equals(null);
    underTest.hashCode();
    underTest.indexOf(null);
    underTest.isEmpty();
    underTest.lastIndexOf(null);
    underTest.size();
    underTest.toString();

    InOrder inOrder = inOrder(backing);

    inOrder.verify(backing).contains(null);
    inOrder.verify(backing).containsAll(null);
    // Mockito ignores equals
    // Mockito ignores hashCode
    inOrder.verify(backing).indexOf(null);
    inOrder.verify(backing).isEmpty();
    inOrder.verify(backing).lastIndexOf(null);
    inOrder.verify(backing).size();
    // Mockito ignores toString

    verifyNoMoreInteractions(backing);

    verifyZeroInteractions(allowDetach, detach);
  }

  @Test
  public void escapingQueriesTriggerDetach() {
    when(allowDetach.getAsBoolean()).thenReturn(true);

    underTest.iterator();
    underTest.listIterator();
    underTest.listIterator(0);
    underTest.subList(0, 0);
    underTest.toArray();
    underTest.toArray(new String[0]);

    InOrder inOrder = inOrder(backing, allowDetach, detach);

    inOrder.verify(allowDetach).getAsBoolean();
    inOrder.verify(backing).size();
    inOrder.verify(backing).forEach(anyObject());
    // the rest happens on the detached list

    verifyNoMoreInteractions(backing, allowDetach, detach);
  }

  @Test
  public void mutationsTriggerDetach() {
    when(allowDetach.getAsBoolean()).thenReturn(true);

    underTest.add("");
    underTest.add(0, "");
    underTest.clear();
    underTest.remove("");

    InOrder inOrder = inOrder(backing, allowDetach, detach);

    inOrder.verify(allowDetach).getAsBoolean();
    inOrder.verify(backing).size();
    inOrder.verify(backing).forEach(anyObject());
    // the rest happens on the detached list

    verifyNoMoreInteractions(backing, allowDetach, detach);
  }

  @Test
  public void detachingCanBeDisallowed() {
    when(allowDetach.getAsBoolean()).thenReturn(false);

    underTest.add("");
    underTest.add(0, "");
    underTest.clear();
    underTest.remove("");
    underTest.iterator();
    underTest.listIterator();
    underTest.listIterator(0);
    underTest.subList(0, 0);
    underTest.toArray();
    underTest.toArray(new String[0]);

    InOrder inOrder = inOrder(backing, allowDetach, detach);

    inOrder.verify(allowDetach).getAsBoolean();
    inOrder.verify(backing).add("");
    inOrder.verify(allowDetach).getAsBoolean();
    inOrder.verify(backing).add(0, "");
    inOrder.verify(allowDetach).getAsBoolean();
    inOrder.verify(backing).clear();
    inOrder.verify(allowDetach).getAsBoolean();
    inOrder.verify(backing).remove("");
    inOrder.verify(allowDetach).getAsBoolean();
    inOrder.verify(backing).iterator();
    inOrder.verify(allowDetach).getAsBoolean();
    inOrder.verify(backing).listIterator();
    inOrder.verify(allowDetach).getAsBoolean();
    inOrder.verify(backing).listIterator(0);
    inOrder.verify(allowDetach).getAsBoolean();
    inOrder.verify(backing).subList(0, 0);
    inOrder.verify(allowDetach).getAsBoolean();
    inOrder.verify(backing).toArray();
    inOrder.verify(allowDetach).getAsBoolean();
    inOrder.verify(backing).toArray(new String[0]);

    verifyNoMoreInteractions(backing, allowDetach, detach);
  }

  @Test
  public void simpleDetach() {
    List<String> original = ImmutableList.of("HELLO", "THERE");

    underTest = new DetachingList<>(original, allowDetach, detach);

    when(allowDetach.getAsBoolean()).thenReturn(true);
    when(detach.apply(anyObject())).thenAnswer(returnsFirstArg());

    assertThat(underTest.remove("THERE"), is(true));
    assertThat(underTest.add("WORLD"), is(true));
    assertThat(underTest, contains("HELLO", "WORLD"));

    // original list contents should be unchanged
    assertThat(original, contains("HELLO", "THERE"));

    InOrder inOrder = inOrder(allowDetach, detach);

    inOrder.verify(allowDetach).getAsBoolean();
    inOrder.verify(detach).apply("HELLO");
    inOrder.verify(detach).apply("THERE");

    verifyNoMoreInteractions(allowDetach, detach);
  }
}
