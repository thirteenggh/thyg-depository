/*global Ext, NX*/

/**
 * Migration overview step.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.migration.OverviewStep', {
  extend: 'NX.wizard.Step',
  requires: [
    'NX.coreui.migration.OverviewScreen'
  ],

  config: {
    screen: 'NX.coreui.migration.OverviewScreen',
    enabled: true
  },

  /**
   * @override
   */
  init: function () {
    var me = this;

    me.control({
      'button[action=next]': {
        click: me.moveNext
      }
    });
  }
});
