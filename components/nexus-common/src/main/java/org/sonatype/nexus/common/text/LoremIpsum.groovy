package org.sonatype.nexus.common.text

/**
 * "Lorem Ipsum" placeholder-text helpers.
 *
 * @since 3.0
 */
class LoremIpsum
{
  public static final String TEXT = '''
Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna
aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint
occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
'''.split('\\s').join(' ').trim()

  public static final List<String> WORDS = TEXT.split('\\s')

  static String words(int count) {
    return WORDS[0..count].join(' ')
  }
}
