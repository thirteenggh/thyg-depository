package org.sonatype.nexus.internal.orient

import javax.inject.Provider

import org.sonatype.nexus.supportzip.SupportBundle.ContentSource.Type
import org.sonatype.nexus.orient.DatabaseExternalizer
import org.sonatype.nexus.orient.DatabaseInstance

import groovy.json.JsonSlurper
import org.junit.rules.TemporaryFolder
import org.junit.Rule
import spock.lang.Specification

/**
 * Tests for {@link PasswordSanitizedJsonSource}.
 */
class PasswordSanitizedJsonSourceTest
    extends Specification
{
  @Rule
  TemporaryFolder temporaryFolder = new TemporaryFolder()

  def 'Excludes sensitive classes from the export.'() {
    given: 'a PasswordSanitizedJsonSource'
      DatabaseInstance instance = Mock(DatabaseInstance)
      DatabaseExternalizer externalizer = Mock(DatabaseExternalizer)
      Provider<DatabaseInstance> instanceProvider = Mock(Provider)
      instanceProvider.get() >> instance
      instance.externalizer() >> externalizer
      PasswordSanitizedJsonSource passwordSanitizedJsonSource = new PasswordSanitizedJsonSource(Type.CONFIG,
          'path', instanceProvider)
      File exportJsonFile = temporaryFolder.newFile()

    when: 'a database is exported'
      passwordSanitizedJsonSource.generate(exportJsonFile)

    then: 'the sensitive class names are passed to the externalizer to be excluded'
      1 * externalizer.export(_, _) >> { output, excludedClasses ->
        assert excludedClasses == ['api_key', 'usertoken_record'] as Set
        output.write('{"records": []}'.bytes)
      }
  }

  def 'Excludes sensitive fields from the export.'() {
    given: 'a PasswordSanitizedJsonSource'
      DatabaseInstance instance = Mock(DatabaseInstance)
      DatabaseExternalizer externalizer = Mock(DatabaseExternalizer)
      externalizer.export(_, _) >> {
        output, excludedClassNames ->
          output.write(('{"secret": "secret-key", "applicationPassword": "password", ' +
              '"systemPassword": "password", "password": "password", "secretAccessKey": "password", ' +
              '"sessionToken": "sToken"}').bytes)
      }
      Provider<DatabaseInstance> instanceProvider = Mock(Provider)
      instanceProvider.get() >> instance
      instance.externalizer() >> externalizer
      PasswordSanitizedJsonSource passwordSanitizedJsonSource = new PasswordSanitizedJsonSource(Type.CONFIG,
          'path', instanceProvider)
      File exportJsonFile = temporaryFolder.newFile()

    when: 'a database is exported'
      passwordSanitizedJsonSource.generate(exportJsonFile)

    then: 'the sensitive files are replaced in the exported JSON'
      def result = new JsonSlurper().parse(exportJsonFile)
      assert result.secret == PasswordSanitizedJsonSource.REPLACEMENT
      assert result.applicationPassword == PasswordSanitizedJsonSource.REPLACEMENT
      assert result.systemPassword == PasswordSanitizedJsonSource.REPLACEMENT
      assert result.password == PasswordSanitizedJsonSource.REPLACEMENT
      assert result.secretAccessKey == PasswordSanitizedJsonSource.REPLACEMENT
      assert result.sessionToken == PasswordSanitizedJsonSource.REPLACEMENT
  }

  def 'Blank sensitive fields are not replaced.'() {
    given: 'a PasswordSanitizedJsonSource'
      DatabaseInstance instance = Mock(DatabaseInstance)
      DatabaseExternalizer externalizer = Mock(DatabaseExternalizer)
      externalizer.export(_, _) >> {
        output, excludedClassNames ->
          output.write(('{"secret": "", "password": "password", "secretAccessKey": ""}').bytes)
      }
      Provider<DatabaseInstance> instanceProvider = Mock(Provider)
      instanceProvider.get() >> instance
      instance.externalizer() >> externalizer
      PasswordSanitizedJsonSource passwordSanitizedJsonSource = new PasswordSanitizedJsonSource(Type.CONFIG,
          'path', instanceProvider)
      File exportJsonFile = temporaryFolder.newFile()

    when: 'a database is exported'
      passwordSanitizedJsonSource.generate(exportJsonFile)

    then: 'the sensitive files are replaced in the exported JSON'
      def result = new JsonSlurper().parse(exportJsonFile)
      assert result.secret == ''
      assert result.password == PasswordSanitizedJsonSource.REPLACEMENT
      assert result.secretAccessKey == ''
  }
}
