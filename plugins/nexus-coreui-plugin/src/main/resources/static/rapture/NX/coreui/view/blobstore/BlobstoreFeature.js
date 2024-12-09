/*global Ext, NX*/

/**
 * Blobstore feature panel.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.view.blobstore.BlobstoreFeature', {
  extend: 'NX.view.drilldown.Drilldown',
  alias: 'widget.nx-coreui-blobstore-feature',
  requires: [
    'NX.I18n'
  ],

  /**
   * @override
   */
  initComponent: function() {
    Ext.apply(this, {
      iconName: 'blobstore-default',

      masters: [
        { xtype: 'nx-coreui-blobstore-list' }
      ],

      tabs: { xtype: 'nx-coreui-blobstore-settings' },

      nxActions: [
        { xtype: 'button', text: NX.I18n.get('Blobstore_BlobstoreFeature_Delete_Button'), iconCls: 'x-fa fa-trash', action: 'delete', disabled: true },
        { xtype: 'button', text: NX.I18n.get('Blobstore_BlobstoreFeature_Promote_Button'), iconCls: 'x-fa fa-wrench', action: 'promoteToGroup', disabled: true}
      ]
    });

    this.callParent();
  }
});
