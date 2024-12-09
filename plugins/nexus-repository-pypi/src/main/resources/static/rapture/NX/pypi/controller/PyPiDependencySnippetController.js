/*global Ext, NX*/

/**
 * PyPI controller.
 *
 * @since 3.15
 */
Ext.define('NX.pypi.controller.PyPiDependencySnippetController', {
  extend: 'NX.app.Controller',

  /**
   * @override
   */
  init: function() {
    NX.getApplication().getDependencySnippetController()
        .addDependencySnippetGenerator('pypi', this.snippetGenerator);
  },

  snippetGenerator: function(componentModel, assetModel) {
    var name = componentModel.get('name'),
        version = componentModel.get('version');

    return [
      {
        displayName: 'pip',
        snippetText: 'pip install ' + name + '==' + version
      }, {
        displayName: 'easy_install',
        snippetText: 'easy_install ' + name + '==' + version
      }, {
        displayName: 'pipenv',
        snippetText: 'pipenv install ' + name + '==' + version
      }, {
        displayName: 'requirements.txt',
        snippetText: name + ' == ' + version
      }
    ];
  }
});
