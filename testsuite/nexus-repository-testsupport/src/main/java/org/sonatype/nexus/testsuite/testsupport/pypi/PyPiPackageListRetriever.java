package org.sonatype.nexus.testsuite.testsupport.pypi;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.sonatype.goodies.common.ComponentSupport;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Fetches lists of PyPI packages using the index endpoints
 * 
 * @since 3.15
 */
public class PyPiPackageListRetriever
    extends ComponentSupport
{
  private static final int LINK_PARSE_TIME_OUT = 60000;

  public List<String> getListOfIndexes(final String repositoryUrl) {
    log.info("Getting list of indexes");
    
    String url = repositoryUrl + "/simple/";

    try {
      List<String> htmlLinks = new ArrayList<>();
      downloadLinks(url, link -> htmlLinks.add(url + link.substring(1)));
      return htmlLinks;
    }
    catch (IOException e) {
      log.warn("Failed to download list of PyPI package indexes for repository at URL {}", repositoryUrl);
      throw new RuntimeException(e);
    }
  }

  public List<String> getListOfPackages(final String repositoryUrl, final String indexUrl) {
    List<String> packageLinks = new ArrayList<>();

      try {
        downloadLinks(indexUrl, link -> {
          String absolute = makeAbsolute(repositoryUrl, link);
          packageLinks.add(absolute);
        });
      }
      catch (IOException e) {
        log.warn("Failed to download list of PyPI packages for index at URL {}", indexUrl);
        throw new RuntimeException(e);
      }

    return packageLinks;
  }

  private String makeAbsolute(final String repositoryUrl, final String link) {
    return repositoryUrl + link.split("#")[0].replaceAll("\\.\\.\\/", "");
  }

  private void downloadLinks(final String url, final Consumer<String> linkHandler) throws IOException
  {
    Document doc = Jsoup.parse(new URL(url), LINK_PARSE_TIME_OUT);
    Elements links = doc.select("a[href]");

    for (Element link : links) {
      String linkText = link.attr("href");

      if (linkText.charAt(0) != '/') {
        linkText = "/" + linkText;
      }

      linkHandler.accept(linkText);
    }
  }
}
