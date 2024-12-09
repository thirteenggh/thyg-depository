package org.sonatype.nexus.elasticsearch.internal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * ElasticSearch {@link Client} provider.
 *
 * @since 3.0
 */
@Named
@Singleton
public class ClientProvider
    implements Provider<Client>
{
  private final Provider<Node> node;

  @Inject
  public ClientProvider(final Provider<Node> node) {
    this.node = checkNotNull(node);
  }

  @Override
  public Client get() {
    return node.get().client();
  }
}
