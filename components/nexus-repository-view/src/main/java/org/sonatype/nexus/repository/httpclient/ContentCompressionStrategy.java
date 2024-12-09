package org.sonatype.nexus.repository.httpclient;

/**
 * Strategy to disable in the HttpClient automatic content compression/decompression
 *
 * @since 3.27
 */
public interface ContentCompressionStrategy
{
  boolean shouldDisableContentCompression(String repositoryName);
}
