/*global Ext, NX*/

/**
 * License Agreement window.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.view.licensing.LicenseAgreement', {
  extend: 'Ext.window.Window',
  alias: 'widget.nx-coreui-licensing-agreement',
  requires: [
    'NX.util.Url',
    'NX.I18n'
  ],

  /**
   * @override
   */
  initComponent: function () {
    var me = this;

    me.title = NX.I18n.get('Licensing_LicenseAgreement_Title');

    me.layout = 'fit';
    me.autoShow = true;
    me.modal = true;
    me.constrain = true;
    me.width = 640;
    me.height = 500;

    me.items = {
      xtype: 'box',
      autoEl: {
        tag: 'iframe',
        src: NX.util.Url.licenseUrl()
      }
    };

    me.dockedItems = [
      {
        xtype: 'toolbar',
        dock: 'bottom',
        ui: 'footer',
        items: [
          { xtype: 'button', text: NX.I18n.get('Licensing_LicenseAgreement_Yes_Button'), action: 'agree', formBind: true, ui: 'nx-primary' },
          { xtype: 'button', text: NX.I18n.get('Licensing_LicenseAgreement_No_Button'), handler: function () {
            this.up('window').close();
          }},
          '->',
          { xtype: 'component', html: '<a href="' + NX.util.Url.licenseUrl() +
              '" target="_new">' + NX.I18n.get('Licensing_LicenseAgreement_Download_Button') + '</a>'
          }
        ]
      }
    ];

    me.callParent();
  }

});
