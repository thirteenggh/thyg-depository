/*global Ext, NX*/

/**
 * Core plugin configuration.
 *
 * @since 3.0
 */
Ext.define('NX.app.PluginConfig', {
  '@aggregate_priority': 100,

  requires: [
    'NX.app.PluginStrings'
  ],

  controllers: [
    'Content',
    'Dashboard',
    'Help',
    'Main',
    'Menu',
    'MenuGroup',
    'Refresh',
    'SettingsForm',
    'UiSessionTimeout',
    'User',

    {
      id: 'Branding',
      // branding is active in also when we are unlicensed or browser is not supported
      active: true
    },
    {
      id: 'Unlicensed',
      active: function () {
        return NX.app.Application.supportedBrowser() &&
            (NX.app.Application.unlicensed() || NX.app.Application.licenseExpired());
      }
    },
    {
      id: 'UnsupportedBrowser',
      active: function () {
        return NX.app.Application.unsupportedBrowser();
      }
    },

    // dev controllers (visible when ?debug and rapture capability debugAllowed = true)
    {
      id: 'dev.Conditions',
      active: function () {
        return NX.app.Application.debugMode;
      }
    },
    {
      id: 'dev.Developer',
      active: function () {
        return NX.app.Application.debugMode;
      }
    },
    {
      id: 'dev.Permissions',
      active: function () {
        return NX.app.Application.debugMode;
      }
    },
    {
      id: 'dev.Stores',
      active: function () {
        return NX.app.Application.debugMode;
      }
    },
    {
      id: 'dev.Logging',
      active: function () {
        return NX.app.Application.debugMode;
      }
    }
  ]
});
