package org.sonatype.nexus.repository.content.store;

import java.util.Arrays;

import org.sonatype.nexus.blobstore.api.BlobRef;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import static org.junit.Assert.assertThat;
import static org.sonatype.goodies.testsupport.hamcrest.DiffMatchers.equalTo;

@RunWith(Parameterized.class)
public class BlobRefTypeHandlerTest
{
  @Parameter
  public String blobRef;

  @Parameter(1)
  public String expectedStore;

  @Parameter(2)
  public String expectedBlob;

  @Parameter(3)
  public String expectedNode;

  @Parameter(4)
  public Class<? extends Exception> expectedException;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Parameters(name = "parseTest: blobRef={0}")
  public static Iterable<Object[]> data() {
    return Arrays.asList(new Object[][]{
        {"default:50bc2227-b63b-4faa-85e1-b3dd1eee9afe@042D35AD-6E5E3FF2-C2326B6D-18C20A57-CAB2540C",
         "default", "50bc2227-b63b-4faa-85e1-b3dd1eee9afe", "042D35AD-6E5E3FF2-C2326B6D-18C20A57-CAB2540C", null},
        {"default:path$foo/bar/baz@042D35AD-6E5E3FF2-C2326B6D-18C20A57-CAB2540C",
         "default", "path$foo/bar/baz", "042D35AD-6E5E3FF2-C2326B6D-18C20A57-CAB2540C", null},
        {"default:http://example.com/foo/bar/baz@042D35AD-6E5E3FF2-C2326B6D-18C20A57-CAB2540C",
         "default", "http://example.com/foo/bar/baz", "042D35AD-6E5E3FF2-C2326B6D-18C20A57-CAB2540C", null},
        {":blobid@node", "N/A", "N/A", "N/A", IllegalArgumentException.class},
        {"default:@node", "N/A", "N/A", "N/A", IllegalArgumentException.class},
        {"default:blobid@", "N/A", "N/A", "N/A", IllegalArgumentException.class},
        {":@", "N/A", "N/A", "N/A", IllegalArgumentException.class},
        {"", "N/A", "N/A", "N/A", IllegalArgumentException.class},
        });
  }

  @Test
  public void parseTest() {
    if (expectedException != null) {
      thrown.expect(expectedException);
    }
    BlobRef ref = BlobRefTypeHandler.parsePersistableFormat(blobRef);
    assertThat(ref.getStore(), equalTo(expectedStore));
    assertThat(ref.getBlob(), equalTo(expectedBlob));
    assertThat(ref.getNode(), equalTo(expectedNode));
  }
}
