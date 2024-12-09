/*global Ext, NX*/

/**
 * Message notification styles.
 *
 * @since 3.0
 */
Ext.define('NX.view.dev.styles.Messages', {
  extend: 'NX.view.dev.styles.StyleSection',

  title: 'Messages',
  layout: {
    type: 'hbox',
    defaultMargins: {top: 0, right: 4, bottom: 0, left: 0}
  },

  /**
   * @protected
   */
  initComponent: function () {
    var me = this;

    function message(type) {
      return {
        xtype: 'window',
        baseCls: 'x-toast ' + type,
        html: "ui: '" + type + "'",
        hidden: false,
        collapsible: false,
        floating: false,
        closable: false,
        draggable: false,
        resizable: false,
        width: 200
      };
    }

    me.items = [
      message('info'),
      message('success'),
      message('warning'),
      message('error')
    ];

    me.callParent();
  }
});
