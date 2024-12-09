/*global Ext*/

/**
 * List of all known icons.
 *
 * @since 3.0
 */
Ext.define('NX.view.dev.Icons', {
  extend: 'Ext.grid.Panel',
  alias: 'widget.nx-dev-icons',

  title: 'Icons',
  store: 'Icon',
  emptyText: 'No icons',

  viewConfig: {
    deferEmptyText: false
  },

  columns: [
    { text: 'cls', dataIndex: 'cls', width: 200 },
    { text: 'name', dataIndex: 'name' },
    { text: 'file', dataIndex: 'file' },
    { text: 'variant', dataIndex: 'variant', width: 50 },
    { text: 'size', xtype: 'templatecolumn', tpl: '{height}x{width}', width: 80 },
    { text: 'url', xtype: 'templatecolumn', tpl: '<a href="{url}" target="_blank" rel="noopener">{url}</a>', flex: 1 },
    { text: 'img src', xtype: 'templatecolumn', tpl: '<img src="{url}"/>' },
    {
      xtype: 'nx-iconcolumn',
      text: 'img class',
      dataIndex: 'cls',
      iconCls: function(value) {
        return value;
      }
    }
  ],

  plugins: [
    { ptype: 'rowediting', clicksToEdit: 1 },
    'gridfilterbox'
  ]

});
