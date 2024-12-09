//
// Helper to generate NEXUS_RESOURCE_DIRS environment variable.
//
// Usage from project directory:
//
//     export NEXUS_RESOURCE_DIRS=`groovy ./buildsupport/scripts/nexusresourcedirs.groovy`
//
// Aggregate projects using 'roots' property, from parent of project directories:
//
//     export NEXUS_RESOURCE_DIRS=`groovy -Droots=nexus-oss,nexus-pro,nexus-bundles nexus-oss/buildsupport/scripts/nexusresourcedirs.groovy`
//

def roots = System.getProperty('roots', '.')
def dirs = []

roots.split(',').each { root ->
  def dir = new File(root)
  if (dir.exists()) {
    def excludedDirs = ['.git', 'node_modules', 'assemblies', 'java', 'dist', 'jenkins', 'test', 'target', 'testsuite', 'frontend', 'baseapp']
    dir.traverse(
        type: groovy.io.FileType.DIRECTORIES,
        preDir: { (it.name in excludedDirs || it.name.startsWith('.') || it.path ==~ /.*resources\/(?!static).*$/) ? groovy.io.FileVisitResult.SKIP_SUBTREE : null }
    ) {
      if (it.path.endsWith('src/main/resources/static')) {
        dirs << it.parentFile.canonicalPath
        return groovy.io.FileVisitResult.SKIP_SUBTREE
      }
    }
  }
}

// derived files live in target/classes/static dirs
roots.split(',').each { root ->
  dirs << new File('./components/nexus-ui-plugin/target/classes').canonicalPath
  dirs << new File('./components/nexus-rapture/target/classes').canonicalPath
  dirs << new File('./plugins/nexus-coreui-plugin/target/classes').canonicalPath
  dirs << new File('./private/plugins/nexus-proui-plugin/target/classes').canonicalPath
}

println dirs.join(',')

return dirs.join(',')
