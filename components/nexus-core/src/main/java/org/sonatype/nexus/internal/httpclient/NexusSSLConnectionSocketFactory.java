package org.sonatype.nexus.internal.httpclient;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

import javax.annotation.Nullable;
import javax.net.ssl.SSLContext;

import org.sonatype.nexus.common.text.Strings2;
import org.sonatype.nexus.httpclient.SSLContextSelector;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import org.apache.http.HttpHost;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.protocol.HttpContext;

import static org.apache.http.conn.ssl.SSLConnectionSocketFactory.getDefaultHostnameVerifier;

public class NexusSSLConnectionSocketFactory
    implements LayeredConnectionSocketFactory
{
  private static final Splitter propertiesSplitter = Splitter.on(',').trimResults().omitEmptyStrings();

  private final SSLConnectionSocketFactory defaultSocketFactory;

  @Nullable
  private final List<SSLContextSelector> sslContextSelectors;

  private final String[] supportedProtocols;

  private final String[] supportedCipherSuites;

  public NexusSSLConnectionSocketFactory(@Nullable final List<SSLContextSelector> sslContextSelectors) {
    this.defaultSocketFactory = SSLConnectionSocketFactory.getSystemSocketFactory();
    this.sslContextSelectors = sslContextSelectors; // might be null
    this.supportedProtocols = split(System.getProperty("https.protocols"));
    this.supportedCipherSuites = split(System.getProperty("https.cipherSuites"));
  }

  private SSLConnectionSocketFactory select(final HttpContext context) {
    if (sslContextSelectors != null) {
      for (SSLContextSelector selector : sslContextSelectors) {
        SSLContext sslContext = selector.select(context);
        if (sslContext != null) {
          return new SSLConnectionSocketFactory(
              sslContext, supportedProtocols, supportedCipherSuites, getDefaultHostnameVerifier()
          );
        }
      }
    }
    return defaultSocketFactory;
  }

  @Override
  public Socket createSocket(final HttpContext context) throws IOException {
    return select(context).createSocket(context);
  }

  @Override
  public Socket connectSocket(final int connectTimeout,
                              final Socket socket,
                              final HttpHost host,
                              final InetSocketAddress remoteAddress,
                              final InetSocketAddress localAddress,
                              final HttpContext context)
      throws IOException
  {
    return select(context).connectSocket(connectTimeout, socket, host, remoteAddress, localAddress, context);
  }

  @Override
  public Socket createLayeredSocket(final Socket socket, final String target, final int port, final HttpContext context)
      throws IOException
  {
    return select(context).createLayeredSocket(socket, target, port, context);
  }

  private static String[] split(final String s) {
    if (Strings2.isBlank(s)) {
      return null;
    }
    return Iterables.toArray(propertiesSplitter.split(s), String.class);
  }
}
