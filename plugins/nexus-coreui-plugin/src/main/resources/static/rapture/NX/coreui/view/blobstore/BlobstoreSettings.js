/*global Ext, NX*/

/**
 * Blobstore "Settings" panel.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.view.blobstore.BlobstoreSettings', {
  extend: 'NX.view.SettingsPanel',
  alias: 'widget.nx-coreui-blobstore-settings',
  requires: [
    'NX.I18n'
  ],

  settingsForm: { xtype: 'nx-coreui-blobstore-settings-form' },

  /**
   * @override
   */
  initComponent: function() {
    var me = this;

    me.title = NX.I18n.get('Blobstore_BlobstoreSettings_Title');

    me.callParent();
  }
});
