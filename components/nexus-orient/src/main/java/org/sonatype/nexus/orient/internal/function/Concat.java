package org.sonatype.nexus.orient.internal.function;

import javax.inject.Named;
import javax.inject.Singleton;

import com.orientechnologies.orient.core.command.OCommandContext;
import com.orientechnologies.orient.core.db.record.OIdentifiable;
import com.orientechnologies.orient.core.sql.functions.OSQLFunctionAbstract;

/**
 * Custom CONCAT function for OrientDB.
 *
 * @since 3.0
 */
@Named
@Singleton
public class Concat
    extends OSQLFunctionAbstract
{
  public Concat() {
    super("concat", 1, 32);
  }

  @Override
  public Object execute(final Object iThis,
                        final OIdentifiable iCurrentRecord,
                        final Object iCurrentResult,
                        final Object[] iParams,
                        final OCommandContext iContext)
  {
    StringBuilder b = new StringBuilder();

    for (Object param : iParams) {
      b.append(param);
    }

    return b.toString();
  }


  @Override
  public String getSyntax() {
    return "concat(<string> [, <string ...>])";
  }

  @Override
  public boolean aggregateResults() {
    return false;
  }
}
