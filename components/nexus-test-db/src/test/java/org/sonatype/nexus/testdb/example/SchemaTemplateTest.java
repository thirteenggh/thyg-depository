package org.sonatype.nexus.testdb.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.datastore.api.DataSession;
import org.sonatype.nexus.datastore.api.SchemaTemplate;
import org.sonatype.nexus.testdb.DataSessionRule;
import org.sonatype.nexus.testdb.example.template.metal.MetalSpindleDAO;
import org.sonatype.nexus.testdb.example.template.metal.MetalSprocketDAO;
import org.sonatype.nexus.testdb.example.template.plastic.PlasticSpindleDAO;
import org.sonatype.nexus.testdb.example.template.plastic.PlasticSprocketDAO;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;
import static org.sonatype.nexus.common.text.Strings2.lower;

/**
 * Test the {@link SchemaTemplate} annotation.
 */
public class SchemaTemplateTest
    extends TestSupport
{
  @Rule
  public DataSessionRule sessionRule = new DataSessionRule()
      .access(PlasticSpindleDAO.class)
      .access(PlasticSprocketDAO.class)
      .access(MetalSpindleDAO.class)
      .access(MetalSprocketDAO.class);

  @Test
  public void testExpectedAccessTypesRegisteredFirst() throws SQLException {
    try (DataSession<?> session = sessionRule.openSession("config")) {
      // will fail if @Expects and @SchemaTemplate are not respected
      session.access(MetalSprocketDAO.class);
    }

    // check the extra column added by plastic_sprocket exists
    assertColumns("metal_sprocket", containsInAnyOrder("id", "spindle_id", "widget_id"));
    assertColumns("plastic_sprocket", containsInAnyOrder("id", "spindle_id", "widget_id", "notes"));
  }

  private void assertColumns(final String tableName, final Matcher<Iterable<?>> matcher) throws SQLException {
    List<String> columnNames = new ArrayList<>();
    try (Connection connection = sessionRule.openConnection("config");
        ResultSet rs = connection.getMetaData().getColumns(null, null, "%", null)) {

      while (rs.next()) {
        if (tableName.equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
          columnNames.add(lower(rs.getString("COLUMN_NAME")));
        }
      }
    }
    assertThat(columnNames, matcher);
  }
}
