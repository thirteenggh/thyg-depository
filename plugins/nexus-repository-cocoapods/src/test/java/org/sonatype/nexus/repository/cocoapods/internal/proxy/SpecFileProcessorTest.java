package org.sonatype.nexus.repository.cocoapods.internal.proxy;

import java.net.URI;

import org.sonatype.nexus.repository.cocoapods.internal.git.GitApiHelper;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @since 3.19
 */
public class SpecFileProcessorTest
{
  private GitApiHelper gitApiHelper =
      new GitApiHelper("https://api.github.com", "https://bitbucket.org", "https://gitlab.com");

  @Test
  public void gitHubToProxiedSpecPositiveTest() throws Exception {
    String spec = "{" +
        "\"name\": \"MasonryHidden\"," +
        "\"version\": \"1.0.0\"," +
        "\"source\": {\n" +
        "\"git\": \"https://github.com/SunnySunning/MasonryHidden.git\"," +
        "\"tag\": \"0.5.0\"" +
        "}" +
        "}";

    String transformedSpec = "{\n" +
        "  \"name\" : \"MasonryHidden\",\n" +
        "  \"version\" : \"1.0.0\",\n" +
        "  \"source\" : {\n" +
        "    \"http\" : \"http://repouri/pods/MasonryHidden/1.0.0/0.5.0.tar.gz\"\n" +
        "  }\n" +
        "}";

    URI repoUri = URI.create("http://repouri/");

    String res = new SpecFileProcessor(gitApiHelper).toProxiedSpec(spec, repoUri);
    assertThat(res, is(transformedSpec));
  }

  @Test
  public void gitHubToProxiedSpecInvalidTagTest() throws Exception {
    String spec = "{" +
        "\"name\": \"MasonryHidden\"," +
        "\"version\": \"1.0.0\"," +
        "\"source\": {\n" +
        "\"git\": \"https://github.com/SunnySunning/MasonryHidden.git\"," +
        "\"tag\": 0.01" +
        "}" +
        "}";

    String transformedSpec = "{\n" +
        "  \"name\" : \"MasonryHidden\",\n" +
        "  \"version\" : \"1.0.0\",\n" +
        "  \"source\" : {\n" +
        "    \"http\" : \"http://repouri/pods/MasonryHidden/1.0.0/1.0.0.tar.gz\"\n" +
        "  }\n" +
        "}";

    URI repoUri = URI.create("http://repouri/");

    String res = new SpecFileProcessor(gitApiHelper).toProxiedSpec(spec, repoUri);
    assertThat(res, is(transformedSpec));
  }

  @Test
  public void httpToProxiedSpecTest() throws Exception {
    String spec = "{" +
        "\"name\": \"AppSpectorTVSDK\"," +
        "\"version\": \"1.0.0\"," +
        "\"source\": {\n" +
        "\"http\": \"https://github.com/appspector/ios-sdk/blob/master/AppSpectorTVSDK.zip?raw=true\"" +
        "}" +
        "}";

    String transformedSpec = "{\n" +
        "  \"name\" : \"AppSpectorTVSDK\",\n" +
        "  \"version\" : \"1.0.0\",\n" +
        "  \"source\" : {\n" +
        "    \"http\" : \"http://repouri/pods/AppSpectorTVSDK/1.0.0/AppSpectorTVSDK.zip\"\n" +
        "  }\n" +
        "}";

    URI repoUri = URI.create("http://repouri/");

    String res = new SpecFileProcessor(gitApiHelper).toProxiedSpec(spec, repoUri);
    assertThat(res, is(transformedSpec));
  }

  @Test(expected = InvalidSpecFileException.class)
  public void gitHubToProxiedSpecInvalidGitUriTest() throws Exception {
    String spec = "{" +
        "\"name\": \"MasonryHidden\"," +
        "\"version\": \"1.0.0\"," +
        "\"source\": {\n" +
        "\"git\": \"git@gitlab.gbksoft.net:gbksoft-mobile-department/ios/gbkslidemenu.git\"," +
        "\"tag\": \"0.5.0\"" +
        "}" +
        "}";

    URI repoUri = URI.create("http://repouri/");

    new SpecFileProcessor(gitApiHelper).toProxiedSpec(spec, repoUri);
  }

  @Test(expected = InvalidSpecFileException.class)
  public void testInvalidJson() throws Exception {
    new SpecFileProcessor(gitApiHelper).toProxiedSpec("invalid_json", URI.create("http://repouri/"));
  }

  @Test(expected = InvalidSpecFileException.class)
  public void gitHubToProxiedSpecNoSourceTest() throws Exception {
    String spec = "{\"name\": \"MasonryHidden\",\"version\": \"1.0.0\"}";

    URI repoUri = URI.create("http://repouri/");

    new SpecFileProcessor(gitApiHelper).toProxiedSpec(spec, repoUri);
  }

  @Test(expected = InvalidSpecFileException.class)
  public void gitHubToProxiedSpecNoNameTest() throws Exception {
    String spec = "{" +
        "\"version\": \"1.0.0\"," +
        "\"source\": {\n" +
        "\"git\": \"git@gitlab.gbksoft.net:gbksoft-mobile-department/ios/gbkslidemenu.git\"," +
        "\"tag\": \"0.5.0\"" +
        "}" +
        "}";

    URI repoUri = URI.create("http://repouri/");

    new SpecFileProcessor(gitApiHelper).toProxiedSpec(spec, repoUri);
  }

  @Test(expected = InvalidSpecFileException.class)
  public void gitHubToProxiedSpecNoVersionTest() throws Exception {
    String spec = "{" +
        "\"name\": \"MasonryHidden\"," +
        "\"source\": {\n" +
        "\"git\": \"git@gitlab.gbksoft.net:gbksoft-mobile-department/ios/gbkslidemenu.git\"," +
        "\"tag\": \"0.5.0\"" +
        "}" +
        "}";

    URI repoUri = URI.create("http://repouri/");

    new SpecFileProcessor(gitApiHelper).toProxiedSpec(spec, repoUri);
  }


  @Test
  public void gitHubToProxiedSpecVersionIncludeNonPrintableCharactersTest() throws Exception {
    String spec = "{\n" +
      "  \"name\": \"Realm\",\n" +
      "  \"version\": \"0.92.3\\n\",\n" +
      "  \"source\": {\n" +
      "    \"git\": \"https://github.com/realm/realm-cocoa.git\",\n" +
      "    \"tag\": \"v0.92.3\"\n" +
      "  }\n" +
      "}";

    URI repoUri = URI.create("http://repouri/");

    String res = new SpecFileProcessor(gitApiHelper).toProxiedSpec(spec, repoUri);
    assertThat(res.contains("/Realm/0.92.3/"), is(true));
  }
}
