package org.sonatype.nexus.repository.browse.internal;

import org.sonatype.goodies.testsupport.TestSupport;

import org.junit.Test;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.sonatype.nexus.repository.browse.internal.AssetWhereClauseBuilder.whereClause;

public class AssetWhereClauseBuilderTest
    extends TestSupport
{
  @Test
  public void content() throws Exception {
    assertThat(whereClause("content", false), is(equalTo("content")));
  }

  @Test
  public void contentAndFilter() throws Exception {
    assertThat(whereClause("content", true), is(equalTo("content AND name LIKE :nameFilter")));
  }

  @Test
  public void contentAndLastId() {
    assertThat(whereClause("content", true, true), is(equalTo("content AND name LIKE :nameFilter AND @rid > :rid")));
  }

  @Test
  public void noContent() {
    assertThat(whereClause(null, false), is(nullValue()));
  }

  @Test
  public void noContentAndFilter() {
    assertThat(whereClause("", true), is(equalTo("name LIKE :nameFilter")));
  }

  @Test
  public void noContentFilterAndLastId() {
    assertThat(whereClause(null, true, true), is(equalTo("name LIKE :nameFilter AND @rid > :rid")));
  }
}
