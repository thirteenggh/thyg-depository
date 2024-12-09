package org.sonatype.nexus.orient.freeze;

import java.util.List;

/**
 * Saves the database frozen state.
 *
 * @since 3.3
 */
public interface DatabaseFrozenStateManager
{
  /**
   * @return the current state
   */
  List<FreezeRequest> getState();

  /**
   * @param request the {@link FreezeRequest} to remove from state
   * @return true if the request was removed, false otherwise
   */
  boolean remove(FreezeRequest request);

  /**
   * @param request the {@link FreezeRequest} to add to the state.
   * @return the request if added successfully, null otherwise
   */
  FreezeRequest add(FreezeRequest request);
}
