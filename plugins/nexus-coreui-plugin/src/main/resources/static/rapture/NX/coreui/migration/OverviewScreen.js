/*global Ext, NX*/

/**
 * ???
 *
 * @since 3.0
 */
Ext.define('NX.coreui.migration.OverviewScreen', {
  extend: 'NX.wizard.Screen',
  alias: 'widget.nx-coreui-migration-overview',

  /**
   * @override
   */
  initComponent: function () {
    var me = this;

    Ext.apply(me, {
      title: NX.I18n.render(me, 'Title'),

      description: NX.I18n.render(me, 'Description'),

      buttons: ['next']
    });

    me.callParent();
  }
});
