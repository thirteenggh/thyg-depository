/*global Ext*/

/**
 * Analyze application window.
 *
 * @since 3.1
 */
Ext.define('NX.coreui.view.component.AnalyzeApplicationWindow', {
  extend: 'NX.view.ModalDialog',
  alias: 'widget.nx-coreui-component-analyze-window',
  requires: [
    'NX.I18n'
  ],

  /**
   * Configuration
   */
  component: undefined,

  /**
   * @override
   */
  initComponent: function () {
    var me = this;

    me.setWidth(NX.view.ModalDialog.LARGE_MODAL);

    me.items = [
      {
        xtype: 'nx-coreui-react-main-container',
        reactView: window.ReactComponents.AnalyzeApplication,
        reactViewProps: {
          componentModel: me.component,
          rerender: function() {
            me.updateLayout();
            me.center();
          },
          onClose: function() {
            me.close();
          }
        }
      }
    ];

    me.callParent();
  },
});
