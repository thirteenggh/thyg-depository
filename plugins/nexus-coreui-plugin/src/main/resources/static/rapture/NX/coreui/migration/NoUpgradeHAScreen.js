/*global Ext, NX*/

/**
 * @since 3.19
 */
Ext.define('NX.coreui.migration.NoUpgradeHAScreen', {
  extend: 'NX.wizard.Screen',
  alias: 'widget.nx-coreui-migration-no-upgrade-ha',

  /**
   * @override
   */
  initComponent: function () {
    Ext.apply(this, {
      title: NX.I18n.render(this, 'Title'),

      description: NX.I18n.render(this, 'Description')
    });

    this.callParent();
  }
});
