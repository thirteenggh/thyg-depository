package org.sonatype.nexus.repository.view;

import org.sonatype.nexus.common.collect.StringMultimap;

import com.google.common.collect.ListMultimap;

/**
 * Headers container.
 *
 * @since 3.0
 */
public class Headers
  extends StringMultimap
{
  public Headers(final ListMultimap<String, String> entries) {
    super(entries);
  }

  public Headers() {
    super();
  }
}
