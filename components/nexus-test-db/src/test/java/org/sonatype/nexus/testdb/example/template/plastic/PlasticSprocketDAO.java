package org.sonatype.nexus.testdb.example.template.plastic;

import org.sonatype.nexus.testdb.example.template.SprocketDAO;

public interface PlasticSprocketDAO
    extends SprocketDAO
{
  void extendSchema();
}
