package org.sonatype.nexus.repository.browse.internal.orient;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.repository.config.ConfigurationDeletedEvent;
import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.storage.AssetCreatedEvent;
import org.sonatype.nexus.repository.storage.AssetDeletedEvent;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OrientBrowseNodeEventHandlerTest
    extends TestSupport
{
  private static final String REPOSITORY_NAME = "maven2repsoitory";

  private OrientBrowseNodeEventHandler handler;

  @Mock
  private OrientBrowseNodeManager browseNodeManager;

  @Before
  public void setup() {
    handler = new OrientBrowseNodeEventHandler(browseNodeManager);
  }

  @Test
  public void onAssetCreated() {
    Asset asset = new Asset();

    AssetCreatedEvent event = mock(AssetCreatedEvent.class);
    when(event.getAsset()).thenReturn(asset);
    when(event.getRepositoryName()).thenReturn(REPOSITORY_NAME);
    when(event.isLocal()).thenReturn(true);

    handler.on(event);

    verify(browseNodeManager).createFromAsset(REPOSITORY_NAME, asset);
  }

  @Test
  public void onAssetCreated_remote() {
    AssetCreatedEvent event = mock(AssetCreatedEvent.class);
    when(event.isLocal()).thenReturn(false);

    handler.on(event);

    verify(browseNodeManager, never()).createFromAsset(any(), any());
  }

  @Test
  public void onAssetDeleted() {
    Asset asset = mock(Asset.class);

    AssetDeletedEvent event = mock(AssetDeletedEvent.class);
    when(event.getAsset()).thenReturn(asset);
    when(event.isLocal()).thenReturn(true);

    handler.on(event);

    verify(browseNodeManager).deleteAssetNode(asset);
  }

  @Test
  public void onAssetDeleted_remote() {
    AssetDeletedEvent event = mock(AssetDeletedEvent.class);
    when(event.isLocal()).thenReturn(false);

    handler.on(event);

    verify(browseNodeManager, never()).deleteAssetNode(any());
  }

  @Test
  public void onConfigurationDeleted() {
    ConfigurationDeletedEvent event = mock(ConfigurationDeletedEvent.class);
    when(event.isLocal()).thenReturn(true);
    when(event.getRepositoryName()).thenReturn(REPOSITORY_NAME);

    handler.on(event);

    verify(browseNodeManager).deleteByRepository(REPOSITORY_NAME);
  }

  @Test
  public void onConfigurationDeleted_remote() {
    ConfigurationDeletedEvent event = mock(ConfigurationDeletedEvent.class);
    when(event.isLocal()).thenReturn(false);

    handler.on(event);

    verify(browseNodeManager, never()).deleteByRepository(any());
  }
}
