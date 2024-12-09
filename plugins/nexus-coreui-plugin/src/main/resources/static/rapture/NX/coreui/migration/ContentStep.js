/*global Ext, NX*/

/**
 * Migration content-options step.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.migration.ContentStep', {
  extend: 'NX.wizard.Step',
  requires: [
    'NX.State',
    'NX.coreui.migration.ContentScreen'
  ],

  screen: 'NX.coreui.migration.ContentScreen',

  /**
   * @override
   */
  init: function () {
    var me = this;

    me.control({
      'button[action=back]': {
        click: me.moveBack
      },
      'button[action=next]': {
        click: me.doNext
      },
      'button[action=cancel]': {
        click: me.cancel
      }
    });
  },

  /**
   * @override
   */
  reset: function() {
    var me = this,
        screen = me.getScreenCmp();

    if (screen) {
      screen.getForm().reset();
    }

    me.unset('content-options');
    me.callParent();
  },

  /**
   * @private
   */
  doNext: function() {
    var values = this.getScreenCmp().getForm().getFieldValues();
    this.set('content-options', values);

    // when options indicate repositories, move to next
    if (values['repositories']) {
      this.moveNext();
    }
    else {
      // otherwise there is no more configuration, prepare plan
      this.controller.configure();
    }
  }
});
