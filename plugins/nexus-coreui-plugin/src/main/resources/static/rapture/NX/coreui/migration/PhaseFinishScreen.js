/*global Ext, NX*/

/**
 * Migration FINISH phase screen.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.migration.PhaseFinishScreen', {
  extend: 'NX.coreui.migration.ProgressScreenSupport',
  alias: 'widget.nx-coreui-migration-phasefinish',
  requires: [
    'NX.I18n'
  ],

  /**
   * @override
   */
  initComponent: function () {
    var me = this;

    Ext.apply(me, {
      title: NX.I18n.render(me, 'Title'),
      description: NX.I18n.render(me, 'Description'),
      buttons: [
        {
          text: NX.I18n.render(me, 'Abort_Button'),
          action: 'abort',
          ui: 'default'
        },
        {
          text: NX.I18n.render(me, 'Done_Button'),
          action: 'done',
          ui: 'nx-primary',
          disabled: true
        }
      ]
    });

    me.callParent();
  }
});
