package org.sonatype.nexus.blobstore.restore.datastore;

import java.util.List;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.blobstore.restore.datastore.RestoreMetadataTaskDescriptor;
import org.sonatype.nexus.formfields.FormField;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class RestoreMetadataTaskDescriptorTest
    extends TestSupport
{
  RestoreMetadataTaskDescriptor underTest;

  @Before
  public void setup() {
    underTest = new RestoreMetadataTaskDescriptor();
  }

  @Test
  public void testGetFormFields() {
    List<FormField> formField = underTest.getFormFields();
    assertThat(formField, hasSize(5));
  }
}
