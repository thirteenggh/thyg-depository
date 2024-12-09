/*global Ext, NX*/

/**
 * Migration preview step.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.migration.PreviewStep', {
  extend: 'NX.wizard.Step',
  requires: [
    'NX.coreui.migration.PreviewScreen'
  ],

  config: {
    screen: 'NX.coreui.migration.PreviewScreen',
    enabled: true
  },

  resetOnBack: true,

  /**
   * @override
   */
  init: function () {
    var me = this;

    me.control({
      'button[action=back]': {
        click: me.moveBack
      },
      'button[action=begin]': {
        click: me.doBegin
      },
      'button[action=cancel]': {
        click: me.cancel
      },
      'gridpanel': {
        cellclick: me.doDisplayDetail
      }
    });
  },

  /**
   * @override
   */
  prepare: function () {
    var preview = this.get('plan-preview'),
        store = this.getStore('NX.coreui.migration.PreviewStore');

    store.loadData(preview.steps, false);

    // if plan-preview is valid, then enable begin button
    if (preview.valid) {
      this.getScreenCmp().down('button[action=begin]').enable();
    }
  },

  /**
   * @override
   */
  reset: function () {
    var me = this,
        screen = me.getScreenCmp();

    if (screen) {
      screen.down('button[action=begin]').disable();
    }

    me.getStore('NX.coreui.migration.PreviewStore').removeAll(true);
    me.callParent();
  },

  /**
   * @private
   */
  doBegin: function () {
    var me = this;

    NX.Dialogs.askConfirmation(
        NX.I18n.render(me, 'Begin_Confirm_Title'),
        NX.I18n.render(me, 'Begin_Confirm_Text'),
        function () {
          me.mask(NX.I18n.render(me, 'Begin_Mask'));

          NX.direct.migration_Assistant.prepare(function (response, event) {
            me.unmask();

            if (event.status && response.success) {
              me.moveNext();

              NX.Messages.success(NX.I18n.render(me, 'Begin_Message'));
            }
          });
        }
    );
  },

  /**
   *@private
   */
  doDisplayDetail: function (grid, td, cellIndex, record) {
    this.controller.displayPlanStepDetail(record.get('id'));
    return false;
  }
});
