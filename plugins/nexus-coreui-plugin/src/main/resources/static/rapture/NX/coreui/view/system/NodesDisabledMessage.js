/**
 * Nodes disabled.
 *
 * @since 3.2
 */
Ext.define('NX.coreui.view.system.NodesDisabledMessage', {
  extend: 'Ext.panel.Panel',
  alias: 'widget.nx-coreui-system-nodes-disabled',
  requires: [
    'NX.I18n'
  ],

  dockedItems: [
    {
      xtype: 'nx-actions',
      items: [
        {
          xtype: 'button',
          text: 'Enable read-only mode',
          iconCls: 'x-fa fa-binoculars',
          action: 'freeze'
        }
      ]
    }
  ],
  items: [
    {
      xtype: 'panel',
      ui: 'nx-inset',
      items: {
        xtype: 'panel',
        ui: 'nx-subsection-framed',
        html: NX.I18n.get('Nodes_OSS_Message')
      }
    }
  ]
});
