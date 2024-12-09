package org.sonatype.nexus.testdb.example.template;

import org.sonatype.nexus.datastore.api.DataAccess;
import org.sonatype.nexus.datastore.api.Expects;
import org.sonatype.nexus.datastore.api.SchemaTemplate;

@Expects({ SpindleDAO.class, WidgetDAO.class })
@SchemaTemplate("material")
public interface SprocketDAO
    extends DataAccess
{
  // no additional behaviour
}
