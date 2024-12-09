/*global Ext*/

/**
 * List of all known features.
 *
 * @since 3.0
 */
Ext.define('NX.view.dev.Features', {
  extend: 'Ext.grid.Panel',
  alias: 'widget.nx-dev-features',

  title: 'Features',
  store: 'Feature',
  emptyText: 'No features',

  columns: [
    { text: 'mode', dataIndex: 'mode', editor: 'textfield' },
    { text: 'path', dataIndex: 'path', editor: 'textfield', flex: 1 },
    { text: 'bookmark', dataIndex: 'bookmark', editor: 'textfield', flex: 1 },
    { text: 'weight', dataIndex: 'weight', width: 80, editor: 'textfield' },
    { text: 'view', dataIndex: 'view', editor: 'textfield', hidden: true },
    { text: 'description', dataIndex: 'description', editor: 'textfield', flex: 1 },
    { text: 'iconName', dataIndex: 'iconName', editor: 'textfield' },
    {
      xtype: 'nx-iconcolumn',
      dataIndex: 'iconName',
      width: 48,
      iconVariant: 'x16'
    },
    {
      xtype: 'nx-iconcolumn',
      dataIndex: 'iconName',
      width: 48,
      iconVariant: 'x32'
    }
  ],

  plugins: [
    { ptype: 'rowediting', clicksToEdit: 1 },
    'gridfilterbox'
  ],

  viewConfig: {
    deferEmptyText: false,
    markDirty: false
  }
});
