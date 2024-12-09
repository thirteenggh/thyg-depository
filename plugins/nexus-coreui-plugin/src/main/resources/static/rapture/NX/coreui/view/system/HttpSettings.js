/*global Ext, NX*/

/**
 * HTTP System Settings form.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.view.system.HttpSettings', {
  extend: 'NX.view.SettingsPanel',
  alias: 'widget.nx-coreui-system-http-settings',
  requires: [
    'NX.Conditions',
    'NX.coreui.view.system.AuthenticationSettings',
    'NX.coreui.view.system.HttpRequestSettings',
    'NX.I18n',
    'NX.ext.form.field.Hostname'
  ],

  /**
   * @override
   */
  initComponent: function () {
    var me = this;

    me.settingsForm = [
      {
        xtype: 'nx-settingsform',
        settingsFormSuccessMessage: NX.I18n.get('System_HttpSettings_Update_Success'),
        api: {
          load: 'NX.direct.coreui_HttpSettings.read',
          submit: 'NX.direct.coreui_HttpSettings.update'
        },
        editableCondition: NX.Conditions.isPermitted('nexus:settings:update'),
        editableMarker: NX.I18n.get('System_HttpSettings_Update_Error'),
        items: [
          // request settings
          {
            xtype: 'nx-coreui-system-httprequestsettings'
          },
          {
            xtype: 'nx-optionalfieldset',
            title: NX.I18n.get('System_HttpSettings_Proxy_Title'),
            checkboxToggle: true,
            checkboxName: 'httpEnabled',
            items: [
              {
                xtype: 'nx-hostname',
                name: 'httpHost',
                fieldLabel: NX.I18n.get('System_HttpSettings_ProxyHost_FieldLabel'),
                helpText: NX.I18n.get('System_HttpSettings_ProxyHost_HelpText'),
                allowBlank: false
              },
              {
                xtype: 'numberfield',
                name: 'httpPort',
                fieldLabel: NX.I18n.get('System_HttpSettings_ProxyPort_FieldLabel'),
                minValue: 1,
                maxValue: 65535,
                allowDecimals: false,
                allowExponential: false,
                allowBlank: false
              },
              {
                xtype: 'nx-optionalfieldset',
                title: NX.I18n.get('System_HttpSettings_Authentication_Title'),
                checkboxToggle: true,
                checkboxName: 'httpAuthEnabled',
                collapsed: true,
                items: {
                  xtype: 'nx-coreui-system-authenticationsettings',
                  namePrefix: 'http'
                }
              }
            ]
          },

          {
            xtype: 'nx-optionalfieldset',
            title: NX.I18n.get('System_HttpSettings_HttpsProxy_Title'),
            itemId: 'httpsProxy',
            checkboxToggle: true,
            checkboxName: 'httpsEnabled',
            collapsed: true,
            items: [
              {
                xtype: 'nx-hostname',
                name: 'httpsHost',
                fieldLabel: NX.I18n.get('System_HttpSettings_HttpsProxyHost_FieldLabel'),
                helpText: NX.I18n.get('System_HttpSettings_HttpsProxyHost_HelpText'),
                allowBlank: false
              },
              {
                xtype: 'numberfield',
                name: 'httpsPort',
                fieldLabel: NX.I18n.get('System_HttpSettings_HttpsProxyPort_FieldLabel'),
                minValue: 1,
                maxValue: 65535,
                allowDecimals: false,
                allowExponential: false,
                allowBlank: false
              },
              {
                xtype: 'nx-optionalfieldset',
                title: NX.I18n.get('System_HttpSettings_HttpsProxyAuthentication_Title'),
                checkboxToggle: true,
                checkboxName: 'httpsAuthEnabled',
                collapsed: true,
                items: {
                  xtype: 'nx-coreui-system-authenticationsettings',
                  namePrefix: 'https'
                }
              }
            ]
          },
          {
            xtype: 'nx-valueset',
            name: 'nonProxyHosts',
            itemId: 'nonProxyHosts',
            fieldLabel: NX.I18n.get('System_HttpSettings_ExcludeHosts_FieldLabel'),
            helpText: NX.I18n.get('System_HttpSettings_ExcludeHosts_HelpText'),
            sorted: true,
            allowBlank: true
          }
        ]
      }
    ];

    me.callParent();
  }
});
