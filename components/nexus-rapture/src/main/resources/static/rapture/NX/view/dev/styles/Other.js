/*global Ext, NX*/

/**
 * Other styles.
 *
 * @since 3.0
 */
Ext.define('NX.view.dev.styles.Other', {
  extend: 'NX.view.dev.styles.StyleSection',

  title: 'Other',
  layout: {
    type: 'vbox',
    defaultMargins: {top: 4, right: 0, bottom: 0, left: 0}
  },

  /**
   * @protected
   */
  initComponent: function () {
    var me = this;

    me.items = [
      me.label('date picker'),
      {
        xtype: 'datepicker'
      },

      me.label('sliders'),
      {
        xtype: 'slider',
        hideLabel: true,
        value: 50,
        margin: '5 0 0 0',
        anchor: '100%'
      },
      {
        xtype: 'slider',
        vertical: true,
        value: 50,
        height: 100,
        margin: '5 0 0 0'
      }
    ];

    me.callParent();
  }
});
