package org.sonatype.nexus.script.plugin.internal.orient

import org.sonatype.goodies.testsupport.TestSupport
import org.sonatype.nexus.orient.testsupport.DatabaseInstanceRule
import org.sonatype.nexus.script.Script
import org.sonatype.nexus.script.plugin.internal.ScriptStore

import org.junit.Before
import org.junit.Rule
import org.junit.Test

import static org.hamcrest.CoreMatchers.is
import static org.hamcrest.CoreMatchers.notNullValue
import static org.hamcrest.CoreMatchers.nullValue
import static org.junit.Assert.assertThat

/**
 * Tests for {@link OrientScriptStore}.
 * 
 * @since 3.0
 */
public class OrientScriptStoreTest
    extends TestSupport
{
  @Rule
  public DatabaseInstanceRule database = DatabaseInstanceRule.inMemory('test')

  private OrientScriptEntityAdapter adapter = new OrientScriptEntityAdapter()

  private ScriptStore underTest

  @Before
  void setUp() throws Exception {
    underTest = new OrientScriptStore(database.getInstanceProvider(), adapter)
    underTest.start()
  }

  @Test
  void 'Can create a new Script'() throws Exception {
    createScript()
  }

  @Test
  void 'Can list persisted Scripts'() throws Exception {
    createScript()
    def list = underTest.list()
    assertThat(list.size(), is(1))
    list[0].with {
      assertThat(name, is('test'))
      assertThat(content, is('println "hello"'))
      assertThat(type, is('groovy'))
    }
  }

  @Test
  void 'Can find a script by name'() throws Exception {
    Script script = createScript()
    Script loaded = underTest.get(script.name)
    assertThat(loaded, notNullValue())
    loaded.with {
      assertThat(name, is(script.name))
      assertThat(content, is(script.content))
      assertThat(type, is('groovy'))
    }
  }
  
  @Test
  void 'Finding a script by name returns null if not found'() throws Exception {
    assertThat(underTest.get('foo'), nullValue())
  }

  @Test
  void "Can update an existing Script"() throws Exception {
    Script script = createScript()
    script.content = 'println "foo"'
    underTest.update(script)
    def list = underTest.list()
    assertThat(list.size(), is(1))
    list[0].with {
      assertThat(name, is('test'))
      assertThat(content, is('println "foo"'))
      assertThat(type, is('groovy'))
    }
  }

  @Test
  void "Can delete an existing script"() throws Exception {
    Script script = createScript()
    assertThat(underTest.list().size(), is(1))
    underTest.delete(script)
    assertThat(underTest.list().size(), is(0))
  }
  
  @Test(expected = IllegalStateException)
  void "Cannot delete a Script that is not persisted"() {
    Script script = adapter.newEntity()
    script.name = 'not-persisted'
    script.content = 'content'
    underTest.delete(script)
  }

  @Test(expected = IllegalStateException)
  void "Cannot update a Script that is not persisted"() {
    Script script = adapter.newEntity()
    script.name = 'not-persisted'
    script.content = 'content'
    underTest.update(script)
  }

  private Script createScript() {
    Script script = adapter.newEntity()
    script.name = 'test'
    script.content = 'println "hello"'
    underTest.create(script)
    return script
  }
}
