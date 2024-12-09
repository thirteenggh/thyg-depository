/*global Ext, NX*/

/**
 * PyPI plugin configuration.
 *
 * @since 3.15
 */
Ext.define('NX.pypi.app.PluginConfig', {
  '@aggregate_priority': 100,

  controllers: [
    {
      id: 'NX.pypi.controller.PyPiDependencySnippetController',
      active: true
    }
  ]
});
