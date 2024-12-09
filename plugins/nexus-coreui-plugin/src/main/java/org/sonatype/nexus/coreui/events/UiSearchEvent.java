package org.sonatype.nexus.coreui.events;

/**
 * Emitted when an Ui search has been performed on {@link org.sonatype.nexus.coreui.SearchComponent} (read method)
 *
 * @since 3.20
 */
public class UiSearchEvent
{
  @Override
  public String toString() {
    return getClass().getSimpleName();
  }
}
