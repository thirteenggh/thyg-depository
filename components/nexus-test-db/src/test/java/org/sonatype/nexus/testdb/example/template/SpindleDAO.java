package org.sonatype.nexus.testdb.example.template;

import org.sonatype.nexus.datastore.api.DataAccess;
import org.sonatype.nexus.datastore.api.SchemaTemplate;

@SchemaTemplate("material")
public interface SpindleDAO
    extends DataAccess
{
  // no additional behaviour
}
