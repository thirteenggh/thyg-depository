/*global Ext*/

/**
 * Npm plugin configuration
 *
 * @since 3.16
 */
Ext.define('NX.npm.app.PluginConfig', {
  '@aggregate_priority': 100,

  controllers: [
    {
      id: 'NX.npm.controller.NpmDependencySnippetController',
      active: true
    }
  ]
});
