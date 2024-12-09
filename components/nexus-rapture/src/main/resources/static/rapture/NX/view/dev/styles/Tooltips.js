/*global Ext, NX*/

/**
 * Tooltip styles.
 *
 * @since 3.0
 */
Ext.define('NX.view.dev.styles.Tooltips', {
  extend: 'NX.view.dev.styles.StyleSection',

  title: 'Tooltips',

  /**
   * @protected
   */
  initComponent: function () {
    var me = this;

    me.items = [
      { xtype: 'button', text: 'Mouse over me', tooltip: 'This is a tooltip' }
    ];

    me.callParent();
  }
});
