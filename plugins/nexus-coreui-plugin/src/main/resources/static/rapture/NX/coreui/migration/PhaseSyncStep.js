/*global Ext, NX*/

/**
 * Migration SYNC phase step.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.migration.PhaseSyncStep', {
  extend: 'NX.coreui.migration.ProgressStepSupport',
  requires: [
    'NX.coreui.migration.PhaseSyncScreen',
    'NX.I18n'
  ],

  config: {
    screen: 'NX.coreui.migration.PhaseSyncScreen',
    enabled: true
  },

  phase: 'SYNC',

  /**
   * @override
   */
  init: function () {
    var me = this;

    me.control({
      'button[action=abort]': {
        click: me.doAbort
      },
      'button[action=continue]': {
        click: me.doContinue
      }
    });

    me.callParent();
  },

  /**
   * @private
   * @type {boolean}
   */
  checkSyncStatus: true,

  /**
   * @private
   * @type {boolean}
   */
  waitingToFinish: false,

  /**
   * @override
   */
  prepare: function () {
    var me = this,
        selectedRepos = me.controller.getContext().get('selected-repositories');

    me.checkSyncStatus = selectedRepos && selectedRepos.length;
    me.waitingToFinish = false;
    me.callParent();
  },

  /**
   * @override
   */
  reset: function() {
    var me = this,
        screen = me.getScreenCmp();

    if (screen) {
      screen.down('button[action=continue]').setVisible(true);
      screen.down('button[action=continue]').disable();
      screen.down('button[action=abort]').enable();
    }
    me.callParent();
  },

  /**
   * @override
   */
  refresh: function() {
    var me = this,
        screen = me.getScreenCmp();

    me.callParent();

    if (screen && (me.checkSyncStatus || me.controller.getContext().get('checkSyncStatus'))) {
      NX.direct.migration_Assistant.syncStatus(function (response, event) {
        var isComplete = response.success && response.data.waitingForChanges && response.data.scanComplete;
        if (!me.waitingToFinish && event.status && isComplete) {
          screen.down('button[action=continue]').enable().setText(NX.I18n.render(screen, 'Continue_Button'));
        }
      });
    }
  },

  /**
   * @override
   */
  doComplete: function() {
    var me = this;

    me.mask(NX.I18n.render(me, 'Finish_Mask'));

    NX.direct.migration_Assistant.finish(function (response, event) {
      me.unmask();

      if (event.status && response.success) {
        me.moveNext();

        NX.Messages.success(NX.I18n.render(me, 'Finish_Message'));
      }
    });
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
  doContinue: function() {
    var me = this;

    NX.Dialogs.askConfirmation(
      NX.I18n.render(me, 'Stop_Waiting_Confirm_Title'),
      NX.I18n.render(me, 'Stop_Waiting_Confirm_Text'),
      function () {
        var screen = me.getScreenCmp();
        screen.down('button[action=continue]').disable()
            .setText(NX.I18n.render(screen, 'Continue_Button_Pending'));
        NX.direct.migration_Assistant.stopWaiting(function (response, event) {
          if (event.status && response.success && response.data) {
            me.waitingToFinish = true;
          }
        });
      }
    );
  }

});
