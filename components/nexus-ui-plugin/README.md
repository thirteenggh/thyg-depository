# Developing Shared Components

## Using Shared Components

All components should be exposed by exporting them from the index.js file. Then to use a shared component in another 
project the nexus-ui-plugin should be added to the child project as a file reference.

```"nexus-ui-plugin": "file:../nexus-ui-plugin"```

Finally, just import the component from nexus-ui-plugin as a named ES6 import.

```import { Button } from 'nexus-ui-plugin'```

## Watching for Changes

1. Update the NEXUS_RESOURCE_DIRS environment variable using the output of `./groovyw buildsupport/scripts/nexusresourcedirs.groovy` from the root.

```
  bash: nexusresourcedirs.sh
  fish: set -x NEXUS_RESOURCE_DIRS (./groovyw buildsupport/scripts/nexusresourcedirs.groovy)
```

2. In a new terminal window `npm run watch` inside `nexus-internal/components/nexus-ui-plugin`.
3. In (an)other new terminal window(s) `npm run watch` inside whichever child project(s) needs to be updated. (Such as `nexus-internal/components/nexus-security`) 
4. Open the application in your browser including the `?debug` query parameter in the url.

