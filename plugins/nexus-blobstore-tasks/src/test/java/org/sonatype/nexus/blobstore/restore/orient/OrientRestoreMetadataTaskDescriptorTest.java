package org.sonatype.nexus.blobstore.restore.orient;

import java.util.List;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.formfields.FormField;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class OrientRestoreMetadataTaskDescriptorTest
    extends TestSupport
{
  OrientRestoreMetadataTaskDescriptor underTest;

  @Before
  public void setup() {
    underTest = new OrientRestoreMetadataTaskDescriptor();
  }

  @Test
  public void testGetFormFields() {
    List<FormField> formField = underTest.getFormFields();
    assertThat(formField, hasSize(5));
  }
}
