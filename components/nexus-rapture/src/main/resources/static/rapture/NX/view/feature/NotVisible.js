/*global Ext*/

/**
 * Panel shown in case a bookmarked feature cannot be shown (403 like).
 *
 * @since 3.0
 */
Ext.define('NX.view.feature.NotVisible', {
  extend: 'Ext.container.Container',
  alias: 'widget.nx-feature-notvisible',
  requires: [
    'NX.I18n'
  ],

  cls: [
    'nx-feature-notvisible',
    'nx-hr'
  ],

  layout: {
    type: 'vbox',
    align: 'center',
    pack: 'center'
  },

  /**
   * @override
   */
  initComponent: function () {
    var me = this;

    me.items = [
      {
        xtype: 'label',
        cls: 'title',
        text: me.text
      },
      {
        xtype: 'label',
        cls: 'description',
        // TODO: i18n
        text: '功能未授权，请选择其他功能'
      }
    ];

    me.callParent();
  }

});
