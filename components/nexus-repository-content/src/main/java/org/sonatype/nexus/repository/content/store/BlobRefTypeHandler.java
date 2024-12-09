package org.sonatype.nexus.repository.content.store;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.blobstore.api.BlobRef;
import org.sonatype.nexus.datastore.mybatis.handlers.ContentTypeHandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

/**
 * MyBatis {@link ContentTypeHandler} that maps {@link BlobRef}s to/from SQL.
 *
 * @since 3.20
 */
@Named
@Singleton
public class BlobRefTypeHandler
    extends BaseTypeHandler<BlobRef>
    implements ContentTypeHandler<BlobRef>
{
  @Override
  public void setNonNullParameter(final PreparedStatement ps,
                                  final int parameterIndex,
                                  final BlobRef parameter,
                                  final JdbcType jdbcType)
      throws SQLException
  {
    ps.setString(parameterIndex, toPersistableString(parameter));
  }

  @Override
  public BlobRef getNullableResult(final ResultSet rs, final String columnName) throws SQLException {
    return nullableBlobRef(rs.getString(columnName));
  }

  @Override
  public BlobRef getNullableResult(final ResultSet rs, final int columnIndex) throws SQLException {
    return nullableBlobRef(rs.getString(columnIndex));
  }

  @Override
  public BlobRef getNullableResult(final CallableStatement cs, final int columnIndex) throws SQLException {
    return nullableBlobRef(cs.getString(columnIndex));
  }

  @Nullable
  private BlobRef nullableBlobRef(@Nullable final String blobRef) {
    return blobRef != null ? parsePersistableFormat(blobRef) : null;
  }

  /**
   * @return the {@link BlobRef} encoded as a string, using the syntax {@code store:blob-id@node}
   *
   * @since 3.26
   */
  public String toPersistableString(final BlobRef blobRef) {
    return String.format("%s:%s@%s", blobRef.getStore(), blobRef.getBlob(), blobRef.getNode());
  }

  /**
   * Parse a string representation of a {@link BlobRef}, using the syntax {@code store:blob-id@node}
   *
   * @since 3.26
   */
  public static BlobRef parsePersistableFormat(final String spec) {
    int colon = spec.indexOf(':');
    int at = spec.lastIndexOf('@');

    if (colon > 0 && at > 0 && at-1 > colon && at < spec.length()-1) {
      return new BlobRef(spec.substring(at+1), spec.substring(0, colon), spec.substring(colon+1, at));
    }
    else {
      throw new IllegalArgumentException("Not a valid blob reference: " + spec);
    }
  }
}
