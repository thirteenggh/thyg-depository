/*global Ext*/

/**
 * List of permissions.
 *
 * @since 3.0
 */
Ext.define('NX.view.dev.Permissions', {
  extend: 'Ext.grid.Panel',
  requires: [
    'NX.Permissions'
  ],
  alias: 'widget.nx-dev-permissions',

  title: 'Permissions',
  store: 'Permission',
  emptyText: 'No permissions',

  viewConfig: {
    deferEmptyText: false,
    markDirty: false
  },

  columns: [
    { text: 'permission', dataIndex: 'id', flex: 1, editor: { xtype: 'textfield', allowBlank: false } },
    {
      xtype: 'nx-iconcolumn',
      text: 'Permitted',
      dataIndex: 'permitted',
      width: 100,
      align: 'center',
      editor: 'checkbox',
      iconVariant: 'x16',
      iconName: function (value) {
        return value ? 'permission-granted' : 'permission-denied';
      }
    }
  ],

  plugins: [
    { pluginId: 'editor', ptype: 'rowediting', clicksToEdit: 1, errorSummary: false },
    'gridfilterbox'
  ],

  tbar: [
    { xtype: 'button', text: 'Add', action: 'add' },
    { xtype: 'button', text: 'Delete', action: 'delete', disabled: true }
  ]
});
