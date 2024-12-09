/*global Ext, NX*/

/**
 * About window.
 *
 * @since 3.0
 */
Ext.define('NX.view.AboutWindow', {
  extend: 'NX.view.ModalDialog',
  alias: 'widget.nx-aboutwindow',
  requires: [
    'NX.I18n',
    'NX.Icons',
    'NX.State',
    'NX.util.Url'
  ],

  cls: 'nx-aboutwindow',

  /**
   * @override
   */
  initComponent: function () {
    var me = this;

    me.layout = {
      type: 'vbox',
      align: 'stretch'
    };

    me.height = 480;
    me.width = NX.view.ModalDialog.LARGE_MODAL;

    me.title = NX.I18n.get('AboutWindow_Title');

    me.items = [
      {
        xtype: 'container',
        cls: 'summary',
        layout: {
          type: 'hbox',
          align: 'stretch'
        },
        items: [
          {
            xtype: 'component',
            cls: 'logo',
            html: NX.Icons.img('nexus-black', 'x100')
          },
          {
            xtype: 'nx-info',
            itemId: 'aboutInfo',
            flex: 1
          }
        ]
      },
      {
        xtype: 'tabpanel',
        ui: 'nx-light',
        flex: 1,
        items: [
          {
            title: NX.I18n.get('AboutWindow_About_Title'),
            xtype: 'uxiframe',
            src: NX.util.Url.urlOf('/COPYRIGHT.html')
          },
          {
            title: NX.I18n.get('AboutWindow_License_Tab'),
            xtype: 'uxiframe',
            src: NX.util.Url.licenseUrl()
          }
        ]
      }
    ];

    me.buttons = [
      { text: NX.I18n.get('Button_Close'), action: 'close', ui: 'nx-primary', handler: function () { me.close(); }}
    ];
    me.buttonAlign = 'left';

    me.callParent();

    // populate initial details
    me.down('#aboutInfo').showInfo({
      '版本号': NX.State.getVersion(),
      '版本名': NX.State.getEdition(),
      '构建版本': NX.State.getBuildRevision(),
      '构建时间': NX.State.getBuildTimestamp()
    });
  }
});
