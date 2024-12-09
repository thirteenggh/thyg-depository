/*global Ext, NX*/

/**
 * Node list.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.view.system.NodeList', {
  extend: 'NX.view.drilldown.Master',
  alias: 'widget.nx-coreui-system-nodelist',
  requires: [
    'NX.I18n'
  ],

  config: {
    stateful: true,
    stateId: 'nx-coreui-system-nodelist'
  },

  store: 'Node',

  columns: [
    {
      xtype: 'nx-iconcolumn',
      width: 36,
      iconVariant: 'x16',
      iconName: function () {
        return 'node-default';
      }
    },
    {
      header: 'Node Name',
      dataIndex: 'friendlyName',
      stateId: 'friendlyName',
      flex: 1,
      renderer: Ext.htmlEncode
    },
    {
      header: 'Socket Address',
      dataIndex: 'socketAddress',
      stateId: 'socketAddress',
      flex: 1,
      renderer: Ext.htmlEncode
    },
    {
      header: 'Node Identity',
      dataIndex: 'nodeIdentity',
      stateId: 'nodeIdentity',
      flex: 1,
      renderer: Ext.htmlEncode
    }
  ],

  // Add a white background behind the filter, to make it look like part of the header
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

  plugins: [
    {ptype: 'gridfilterbox', emptyText: 'No nodes matched "$filter"'}
  ]
});
