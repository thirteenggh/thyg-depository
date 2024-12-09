/*global Ext, NX*/

/**
 * Migration agent connection step.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.migration.AgentStep', {
  extend: 'NX.wizard.Step',
  requires: [
    'NX.coreui.migration.AgentScreen',
    'NX.I18n'
  ],

  config: {
    screen: 'NX.coreui.migration.AgentScreen',
    enabled: true
  },

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

    me.callParent();
  },

  /**
   * @private
   */
  doNext: function() {
    var me = this,
        input = me.getScreenCmp().getForm().getFieldValues();

    me.mask(NX.I18n.render(me, 'Connect_Mask'));

    NX.direct.migration_Assistant.connect(input.url, input.accessToken, input.fetchSize, input.useTrustStoreForUrl, function (response, event) {
      me.unmask();

      // FIXME: handle validation/errors

      if (event.status && response.success) {
        me.moveNext();

        NX.Messages.success(NX.I18n.render(me, 'Connect_Message'));
      }
    });
  }
});
