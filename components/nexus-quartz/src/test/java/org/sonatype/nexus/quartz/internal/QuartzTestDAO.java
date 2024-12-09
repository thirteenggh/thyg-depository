package org.sonatype.nexus.quartz.internal;

import java.util.List;

import org.sonatype.nexus.datastore.api.DataAccess;

public interface QuartzTestDAO
    extends DataAccess
{
  List<String> tables();

  List<String> primaryKeys();

  List<String> foreignKeys();

  List<String> indexes();

  Integer expectedIndexes();
}
