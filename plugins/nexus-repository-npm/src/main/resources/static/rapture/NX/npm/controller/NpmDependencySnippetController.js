/*global Ext, NX*/

/**
 * @since 3.16
 */
Ext.define('NX.npm.controller.NpmDependencySnippetController', {
  extend: 'NX.app.Controller',

  /**
   * @override
   */
  init: function() {
    NX.getApplication().getDependencySnippetController().addDependencySnippetGenerator('npm', this.snippetGenerator);
  },

  snippetGenerator: function(componentModel, assetModel) {
    var group = componentModel.get('group'),
        name = componentModel.get('name'),
        version = componentModel.get('version'),
        dependencyName = '';

    if (group) {
      dependencyName = '@' + group + '/';
    }

    dependencyName = dependencyName + name;

    return [
      {
        displayName: 'npm',
        description: 'Install runtime dependency',
        snippetText: 'npm install ' + dependencyName + '@' + version
      },
      {
        displayName: 'Yarn',
        description: 'Install runtime dependency',
        snippetText: 'yarn add ' + dependencyName + '@' + version
      },
      {
        displayName: 'package.json',
        description: 'Install runtime dependency to the package.json\'s "dependencies" section',
        snippetText: '"' + dependencyName + '": "' + version + '"'
      }
    ]
  }
});
