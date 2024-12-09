/*global Ext, NX*/

/**
 * Bundles feature panel.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.view.system.Bundles', {
  extend: 'NX.view.drilldown.Drilldown',
  alias: 'widget.nx-coreui-system-bundles',
  requires: [
    'NX.I18n'
  ],

  /**
   * @override
   */
  initComponent: function() {
    Ext.apply(this, {
      iconName: 'bundle-default',

      masters: [
        { xtype: 'nx-coreui-system-bundlelist' }
      ],

      tabs: {
        xtype: 'nx-info-panel',
        title: NX.I18n.get('System_Bundles_Details_Tab'),
        cls: 'nx-hr'
      }
    });

    this.callParent();
  }
});
