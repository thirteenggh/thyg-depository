
# Trust Repository Open Source Codebase 

[![CircleCI Build Status](https://circleci.com/gh/sonatype/nexus-public.svg?style=shield "CircleCI Build Status")](https://circleci.com/gh/sonatype/nexus-public) [![Join the chat at https://gitter.im/sonatype/nexus-developers](https://badges.gitter.im/sonatype/nexus-developers.svg)](https://gitter.im/sonatype/nexus-developers?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

## Downloadable Bundles

 See: https://www.sonatype.com/download-oss-sonatype
 
## Requirements

* Apache Maven 3.3.3+
* Java 8
* Network access to https://repository.sonatype.org/content/groups/sonatype-public-grid

## Building

To build the project and generate the template assembly use the included Maven wrapper:

    ./mvnw clean install -DskipTests

## Running

To run Nexus Repository, after building, unzip the assembly and start the server:

    unzip -d target assemblies/nexus-base-template/target/nexus-base-template-3.29.2-02.zip
    ./target/nexus-base-template-3.29.2-02/bin/nexus console

The `nexus-base-template` assembly is used as the basis for the official Trust Repository distributions.

## License

This project is licensed under the Eclipse Public License - v 1.0, you can read the full text [here](LICENSE.txt)

## Getting help

Looking to contribute to our code but need some help? There's a few ways to get information or our attention:

* File an issue in [our public JIRA](https://issues.sonatype.org/browse/NEXUS)
* Check out the [Nexus3](http://stackoverflow.com/questions/tagged/nexus3) tag on Stack Overflow
* Check out the [Nexus Repository User List](https://groups.google.com/a/glists.sonatype.com/forum/?hl=en#!forum/nexus-users)
* Connect with [@sonatypeDev](https://twitter.com/sonatypeDev) on Twitter
