package org.sonatype.nexus.coreui

import org.sonatype.goodies.testsupport.TestSupport
import org.sonatype.nexus.blobstore.api.BlobStoreManager

import org.junit.Before
import org.junit.Test
import org.mockito.Mock

import static org.mockito.Mockito.when

/**
 * Tests {@link UniqueBlobStoreNameValidator}
 */
class UniqueBlobStoreNameValidatorTest
    extends TestSupport
{
  @Mock
  BlobStoreManager blobStoreManager

  UniqueBlobStoreNameValidator underTest

  @Before
  public void setup() {
    underTest = new UniqueBlobStoreNameValidator(blobStoreManager)
  }

  @Test
  public void 'test validation'() {
    when(blobStoreManager.exists('test')).thenReturn(true)
    when(blobStoreManager.exists('DEFAULT')).thenReturn(false)

    assert !underTest.isValid('test', null)
    assert underTest.isValid('DEFAULT', null)
  }
}
