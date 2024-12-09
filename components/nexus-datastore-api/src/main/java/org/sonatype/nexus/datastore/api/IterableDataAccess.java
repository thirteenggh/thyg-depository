package org.sonatype.nexus.datastore.api;

import java.util.Optional;

import org.sonatype.nexus.common.entity.EntityId;
import org.sonatype.nexus.common.entity.HasEntityId;
import org.sonatype.nexus.common.entity.HasName;

/**
 * Generic CRUD {@link DataAccess} that accesses zero to many entities of the same type.
 *
 * @since 3.19
 */
public interface IterableDataAccess<E extends HasEntityId>
    extends DataAccess
{
  /**
   * Browse existing entities.
   */
  Iterable<E> browse();

  /**
   * Create a new entity.
   */
  void create(E entity);

  /**
   * Retrieve the entity with the given id.
   */
  Optional<E> read(EntityId id);

  /**
   * Update an existing entity.
   */
  boolean update(E entity);

  /**
   * Delete the entity with the given id.
   */
  boolean delete(EntityId id);

  /**
   * {@link IterableDataAccess} extension for entities that can also be accessed by name.
   *
   * @since 3.21
   */
  interface WithName<E extends HasName & HasEntityId>
      extends IterableDataAccess<E>
  {
    /**
     * Retrieve the entity with the given name.
     */
    Optional<E> readByName(String name);

    /**
     * Delete the entity with the given name.
     */
    boolean deleteByName(String name);
  }
}
