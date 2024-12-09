def ant = new AntBuilder()

def basedir = System.getProperty('basedir', '.') as File
def fixlast = System.getProperty('fixlast', 'false')

println "Normalizing line-endings to LF in: ${basedir.canonicalFile}"
println "    fixlast: $fixlast"

ant.fixcrlf(
    srcDir: basedir,
    eol: 'lf',
    eof: 'remove',
    fixlast: fixlast
) {
    include(name: '**/*.java')
    include(name: '**/*.groovy')
    include(name: '**/*.js')
    include(name: '**/*.css')
    include(name: '**/*.vm')
    include(name: '**/*.tpl')
    include(name: '**/*.properties')
    include(name: '**/*.xml')
    include(name: '**/*.yml')
    include(name: '**/*.txt')
}
