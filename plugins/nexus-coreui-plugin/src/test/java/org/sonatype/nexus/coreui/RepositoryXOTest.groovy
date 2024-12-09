package org.sonatype.nexus.coreui

import javax.inject.Inject
import javax.validation.Validator

import org.sonatype.nexus.validation.ValidationModule
import org.sonatype.nexus.validation.group.Create

import spock.guice.UseModules
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Test bean validation for {@link RepositoryXO}
 */
@UseModules([ValidationModule, TestRepositoryManagerModule])
class RepositoryXOTest
    extends Specification
{
  @Inject
  @Shared
  Validator validator

  // semi-random sample of invalid characters from Latin and other character sets
  static List INVALID_NAMES = (
      '#,* #\'\\/?<>| \r\n\t,+@&å©不βخ'.collect { it }
          + '_leadingUnderscore'
          + '.'
          + '..'
  ).asImmutable()

  static final List VALID_NAMES = ['Foo_1.2-3' , 'foo.', '-0.', 'a', '1'].asImmutable()

  def "Name is always required"() {
    when:
      def validations = validator.validate(new RepositoryXO([attributes: [any: 'any'], online: true]))

    then:
      validations.size() == 1
      validations[0].propertyPath.toString() == 'name'
  }

  def "Attributes are always required, and cannot be empty"(Map attributes) {
    when:
      Map properties = [
          name      : 'foo',
          attributes: attributes,
          online: true
      ]
      def validations = validator.validate(new RepositoryXO(properties))

    then:
      validations.size() == 1
      validations[0].propertyPath.toString() == 'attributes'

    where:
      attributes << [null, [:]]
  }

  @Unroll
  def "The name: #name should not validate"(String name) {
    when:
      Map properties = [
          name      : name,
          attributes: [any: 'any'] ,
          online: true
      ]
      def validations = validator.validate(new RepositoryXO(properties))

    then:
      validations.size() == 1
      validations[0].propertyPath.toString() == 'name'

    where:
      name << INVALID_NAMES
  }

  @Unroll
  def "The name: #name should be valid"(String name) {
    when:
      Map properties = [
          name      : name,
          attributes: [any: 'any'],
          online: true
      ]
      def validations = validator.validate(new RepositoryXO(properties))

    then:
      validations.isEmpty()

    where:
      name << VALID_NAMES
  }

  def "The recipe field is only required on creation"() {
    setup:
      Map baseConfiguration = [
          name      : 'bob',
          attributes: [any: 'any']
      ]
    when:
      def validations = validator.validate(new RepositoryXO(baseConfiguration), Create)

    then:
      validations.size() == 1
      validations[0].propertyPath.toString() == 'recipe'

    when:
      baseConfiguration.recipe = 'any'
      validations = validator.validate(new RepositoryXO(baseConfiguration), Create)

    then:
      validations.isEmpty()
  }

  def "On creation the name should be validated as case-insensitively unique"() {
    when:
      Map properties = [
          name      : name,
          attributes: [any: 'any'],
          online    : true,
          recipe    : 'any'
      ]
      def validations = validator.validate(new RepositoryXO(properties), Create)

    then:
      validations.size() == 1
      validations[0].propertyPath.toString() == 'name'
      validations[0].message == 'Name is already used, must be unique (ignoring case)'

    where:
      name << ['Foo', 'bAr', 'baZ']
  }
}
