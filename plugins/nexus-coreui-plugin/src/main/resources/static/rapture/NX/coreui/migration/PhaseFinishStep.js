/*global Ext, NX*/

/**
 * Migration FINISH phase step.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.migration.PhaseFinishStep', {
  extend: 'NX.coreui.migration.ProgressStepSupport',
  requires: [
    'NX.coreui.migration.PhaseFinishScreen'
  ],

  config: {
    screen: 'NX.coreui.migration.PhaseFinishScreen',
    enabled: true
  },

  phase: 'FINISH',

  /**
   * @override
   */
  init: function () {
    var me = this;

    me.control({
      'button[action=abort]': {
        click: me.doAbort
      },
      'button[action=done]': {
        click: me.doDone
      }
    });

    me.callParent();
  },

  /**
   * @override
   */
  reset: function() {
    var me = this,
        screen = me.getScreenCmp();

    if (screen) {
      screen.down('button[action=abort]').enable();
      screen.down('button[action=done]').disable();
    }
    me.callParent();
  },

  /**
   * @override
   */
  doComplete: function() {
    this.getScreenCmp().down('button[action=abort]').disable();
    this.getScreenCmp().down('button[action=done]').enable();
  },

  /**
   * @private
   */
  doAbort: function() {
    var me = this;

    NX.Dialogs.askConfirmation(
        NX.I18n.render(me, 'Abort_Confirm_Title'),
        NX.I18n.render(me, 'Abort_Confirm_Text'),
        function () {
          me.mask(NX.I18n.render(me, 'Abort_Mask'));

          me.autoRefresh(false);

          NX.direct.migration_Assistant.abort(function (response, event) {
            me.unmask();

            if (event.status && response.success) {
              me.controller.reset();

              NX.Messages.warning(NX.I18n.render(me, 'Abort_Message'));
            }
          });
        }
    );
  },

  /**
   * @private
   */
  doDone: function() {
    var me = this;

    me.mask(NX.I18n.render(me, 'Done_Mask'));

    NX.direct.migration_Assistant.done(function (response, event) {
      me.unmask();

      if (event.status && response.success) {
        me.finish();

        NX.Messages.success(NX.I18n.render(me, 'Done_Message'));
      }
    });
  }
});
