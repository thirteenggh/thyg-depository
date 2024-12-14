
# Trust Repository Open Source Codebase 
 
## Requirements

* Apache Maven 3.3.3+
* Java 8

## Building

To build the project and generate the template assembly use the included Maven wrapper:

    ./mvnw clean install -DskipTests

## Running

To run Trust Repository, after building, unzip the assembly and start the server:

    unzip -d target assemblies/nexus-base-template/target/nexus-base-template-3.29.2-02.zip
    ./target/nexus-base-template-3.29.2-02/bin/nexus console

The `nexus-base-template` assembly is used as the basis for the official Trust Repository distributions.