
package org.sonatype.nexus.datastore.mybatis.handlers;

import org.sonatype.nexus.datastore.mybatis.AbstractSerializableTypeHandler;

import org.apache.ibatis.type.TypeHandler;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * MyBatis {@link TypeHandler} that serializes and encrypts {@link PrincipalCollection}s to/from SQL.
 *
 * @since 3.21
 */
// not @Named because we register this manually
public class PrincipalCollectionTypeHandler
    extends AbstractSerializableTypeHandler<PrincipalCollection>
{
  // nothing to add
}
