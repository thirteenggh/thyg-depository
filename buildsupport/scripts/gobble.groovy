//
// Helper to consume the output of `mvn versions:display-dependency-updates versions:display-plugin-updates`
// and spit out unique version upgrades available.
//
// Usage:
//
//     mvn versions:display-dependency-updates versions:display-plugin-updates | groovy buildsupport/scripts/gobble.groovy
//

def lines = System.in.readLines()

def iter = lines.iterator()
while (iter.hasNext()) {
    def line = iter.next()
    if (line.startsWith('[INFO] --- versions-maven-plugin')) {
        break
    }
}

def chomp(line) {
    return line[6..-1].trim()
}

def updates = new HashSet()
while (iter.hasNext()) {
    def line = chomp(iter.next())

    if (line.endsWith(' ...')) {
        line = line + ' ' + chomp(iter.next())
    }

    if (line.contains('->')) {
        def parts = line.split('\\s')
        def update="${parts[0]}: ${parts[2]} -> ${parts[4]}"
        updates << update
    }
}

updates = new ArrayList(updates)
updates.sort()

for (update in updates) {
    println update
}
