package org.sonatype.nexus.repository.rest.internal.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.repository.rest.api.AssetXO;
import org.sonatype.nexus.rest.WebApplicationMessageException;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.sonatype.nexus.repository.rest.internal.resources.AssetDownloadResponseProcessor.SEARCH_RETURNED_MULTIPLE_ASSETS;

public class AssetDownloadResponseProcessorTest
    extends TestSupport
{
  private AssetDownloadResponseProcessor underTest;

  @Test
  public void testProcess_SingleResult() {
    underTest = new AssetDownloadResponseProcessor(generateAssetXOs(1), false);
    Response response = underTest.process();
    //302 reponse is good, that means we are being redirected to the download url
    assertThat(response.getStatus(), is(302));
    assertThat(response.getLocation().toString(), is("http://someurl0"));
  }

  @Test
  public void testProcess_MultipleResults() {
    underTest = new AssetDownloadResponseProcessor(generateAssetXOs(2), false);

    try {
      underTest.process();
      Assert.fail("Exception should have been thrown");
    }
    catch (WebApplicationMessageException e) {
      assertThat(e.getMessage(), is("HTTP 400 Bad Request"));
      assertThat(e.getResponse().getEntity().toString(), is(SEARCH_RETURNED_MULTIPLE_ASSETS));
    }
  }

  @Test
  public void testProcess_MultipleResultsWithSorting() {
    underTest = new AssetDownloadResponseProcessor(generateAssetXOs(2), true);
    Response response = underTest.process();
    //302 reponse is good, that means we are being redirected to the download url
    assertThat(response.getStatus(), is(302));
    assertThat(response.getLocation().toString(), is("http://someurl0"));
  }

  private List<AssetXO> generateAssetXOs(int count) {
    List<AssetXO> assetXOs = new ArrayList<>();
    for (int i = 0 ; i < count ; i++) {
      AssetXO assetXO = new AssetXO();
      assetXO.setDownloadUrl("http://someurl" + i);
      assetXOs.add(assetXO);
    }
    return assetXOs;
  }
}
