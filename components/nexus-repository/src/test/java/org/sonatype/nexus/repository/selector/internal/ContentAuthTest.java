package org.sonatype.nexus.repository.selector.internal;

import org.sonatype.goodies.testsupport.TestSupport;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link ContentAuth}.
 */
public class ContentAuthTest
    extends TestSupport
{
  private static final String REPOSITORY_NAME = "repository-name";

  private static final String PATH = "path";

  private static final String FORMAT = "format";

  @Mock
  OrientContentAuthHelper contentAuthHelper;

  ContentAuth underTest;

  @Before
  public void setup() {
    underTest = new ContentAuth(contentAuthHelper);
  }

  @Test
  public void testPathPermitted() {
    when(contentAuthHelper.checkPathPermissions(PATH, FORMAT, new String[]{REPOSITORY_NAME})).thenReturn(true);
    assertThat(underTest.execute(underTest, null, null, new Object[] { PATH, FORMAT, REPOSITORY_NAME }, null), is(true));
    verify(contentAuthHelper, times(1)).checkPathPermissions(PATH, FORMAT, new String[]{REPOSITORY_NAME});
  }

  @Test
  public void testPathNotPermitted() {
    when(contentAuthHelper.checkPathPermissions(PATH, FORMAT, new String[]{REPOSITORY_NAME})).thenReturn(false);
    assertThat(underTest.execute(underTest, null, null, new Object[] { PATH, FORMAT, REPOSITORY_NAME }, null), is(false));
    verify(contentAuthHelper, times(1)).checkPathPermissions(PATH, FORMAT, new String[]{REPOSITORY_NAME});
  }

  @Test
  public void testPathPermitted_withGroupRepo() {
    when(contentAuthHelper.checkPathPermissions(PATH, FORMAT, new String[]{"group_repo"})).thenReturn(true);
    assertThat(underTest.execute(underTest, null, null, new Object[] { PATH, FORMAT, "group_repo" }, null), is(true));
    verify(contentAuthHelper).checkPathPermissions(PATH, FORMAT, new String[]{"group_repo"});
    verify(contentAuthHelper, never()).checkPathPermissions(PATH, FORMAT, new String[]{REPOSITORY_NAME});
  }

  @Test
  public void testPathNotPermitted_withGroupRepo() {
    when(contentAuthHelper.checkPathPermissions(PATH, FORMAT, new String[]{"group_repo"})).thenReturn(false);
    assertThat(underTest.execute(underTest, null, null, new Object[] { PATH, FORMAT, "group_repo" }, null), is(false));
    verify(contentAuthHelper).checkPathPermissions(PATH, FORMAT, new String[]{"group_repo"});
    verify(contentAuthHelper, never()).checkPathPermissions(PATH, FORMAT, new String[]{REPOSITORY_NAME});
  }

  @Test
  public void testPathPermitted_jexlOnly() {
    when(contentAuthHelper.checkPathPermissionsJexlOnly(PATH, FORMAT, new String[]{REPOSITORY_NAME})).thenReturn(true);
    assertThat(underTest.execute(underTest, null, null, new Object[] { PATH, FORMAT, REPOSITORY_NAME, true }, null),
        is(true));
    verify(contentAuthHelper, times(1)).checkPathPermissionsJexlOnly(PATH, FORMAT, new String[] { REPOSITORY_NAME });
  }

  @Test
  public void testPathNotPermitted_jexlOnly() {
    when(contentAuthHelper.checkPathPermissionsJexlOnly(PATH, FORMAT, new String[]{REPOSITORY_NAME})).thenReturn(false);
    assertThat(underTest.execute(underTest, null, null, new Object[] { PATH, FORMAT, REPOSITORY_NAME, true }, null),
        is(false));
    verify(contentAuthHelper, times(1)).checkPathPermissionsJexlOnly(PATH, FORMAT, new String[] { REPOSITORY_NAME });
  }

  @Test
  public void testPathPermitted_withGroupRepo_jexlOnly() {
    when(contentAuthHelper.checkPathPermissionsJexlOnly(PATH, FORMAT, new String[] { "group_repo" })).thenReturn(true);
    assertThat(underTest.execute(underTest, null, null, new Object[] { PATH, FORMAT, "group_repo", true }, null),
        is(true));
    verify(contentAuthHelper).checkPathPermissionsJexlOnly(PATH, FORMAT, new String[] { "group_repo" });
    verify(contentAuthHelper, never()).checkPathPermissionsJexlOnly(PATH, FORMAT, new String[] { REPOSITORY_NAME });
  }

  @Test
  public void testPathNotPermitted_withGroupRepo_jexlOnly() {
    when(contentAuthHelper.checkPathPermissionsJexlOnly(PATH, FORMAT, new String[] { "group_repo" })).thenReturn(false);
    assertThat(underTest.execute(underTest, null, null, new Object[] { PATH, FORMAT, "group_repo", true }, null),
        is(false));
    verify(contentAuthHelper).checkPathPermissionsJexlOnly(PATH, FORMAT, new String[] { "group_repo" });
    verify(contentAuthHelper, never()).checkPathPermissionsJexlOnly(PATH, FORMAT, new String[] { REPOSITORY_NAME });
  }
}
