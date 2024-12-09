package org.sonatype.repository.helm.internal.metadata;

import org.sonatype.goodies.testsupport.TestSupport;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ChartIndexTest
    extends TestSupport
{
  private ChartIndex underTest;

  @Before
  public void setUp() throws Exception {
    underTest = new ChartIndex();
  }

  @Test
  public void addChartEntry() {
    ChartEntry chartEntry = new ChartEntry();
    underTest.addEntry(chartEntry);

    assertThat(underTest.getEntries().size(), is(1));
  }

  @Test
  public void addMultipleChartEntries() throws Exception {
    ChartEntry chartEntry = new ChartEntry();
    ChartEntry chartEntrySameName = new ChartEntry();
    ChartEntry chartEntryDifferentName = new ChartEntry();

    chartEntry.setName("test");
    chartEntrySameName.setName("test");
    chartEntryDifferentName.setName("different name");

    underTest.addEntry(chartEntry);
    underTest.addEntry(chartEntrySameName);

    assertThat(underTest.getEntries().size(), is(1));

    underTest.addEntry(chartEntryDifferentName);

    assertThat(underTest.getEntries().size(), is(2));
  }
}
