/*global Ext, NX*/

/**
 * Copy window controller
 *
 * @since 3.0
 */
Ext.define('NX.controller.Copy', {
  extend: 'NX.app.Controller',

  views: [
    'CopyWindow'
  ],

  refs: [
    {
      ref: 'copyModal',
      selector: 'nx-copywindow'
    }
  ],

  /**
   * @override
   */
  init: function () {
    var me = this;

    me.listen({
      component: {
        'nx-copywindow button[action=close]': {
          click: me.copyToClipboard
        }
      }
    });
  },

  copyToClipboard: function() {
    this.getCopyModal().close();
  }
});
