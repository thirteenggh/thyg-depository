/*global Ext, NX*/

/**
 * Migration check HA step
 *
 * @since 3.19
 */
Ext.define('NX.coreui.migration.NoUpgradeHAStep', {
  extend: 'NX.wizard.Step',
  requires: [
    'NX.coreui.migration.NoUpgradeHAScreen'
  ],

  config: {
    screen: 'NX.coreui.migration.NoUpgradeHAScreen',
    enabled: true
  }
});
