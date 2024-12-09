/*global Ext, NX*/

/**
 * Security realms settings form.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.view.security.RealmSettings', {
  extend: 'NX.view.SettingsPanel',
  alias: 'widget.nx-coreui-security-realm-settings',
  requires: [
    'NX.Conditions',
    'NX.ext.form.field.ItemSelector',
    'NX.I18n'
  ],

  /**
   * @override
   */
  initComponent: function () {
    var me = this;

    me.settingsForm = [
      // basic settings
      {
        xtype: 'nx-settingsform',
        settingsFormSuccessMessage: NX.I18n.get('Security_RealmSettings_Update_Success'),
        api: {
          load: 'NX.direct.coreui_RealmSettings.read',
          submit: 'NX.direct.coreui_RealmSettings.update'
        },
        editableCondition: NX.Conditions.isPermitted('nexus:settings:update'),
        editableMarker: NX.I18n.get('Security_RealmSettings_Update_Error'),

        items: [
          {
            xtype: 'nx-itemselector',
            name: 'realms',
            fieldLabel: '可用域',
            buttons: ['up', 'add', 'remove', 'down'],
            fromTitle: NX.I18n.get('Security_RealmSettings_Available_FromTitle'),
            toTitle: NX.I18n.get('Security_RealmSettings_Available_ToTitle'),
            store: 'RealmType',
            valueField: 'id',
            displayField: 'name',
            delimiter: null
          }
        ]
      }
    ];

    me.callParent();
  }
});
