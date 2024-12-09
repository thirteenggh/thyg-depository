/*global Ext*/

/**
 * List of current state.
 *
 * @since 3.0
 */
Ext.define('NX.view.dev.State', {
  extend: 'Ext.grid.Panel',
  alias: 'widget.nx-dev-state',

  title: 'State',
  store: 'State',
  emptyText: 'No values',
  viewConfig: {
    deferEmptyText: false
  },

  columns: [
    { text: 'key', dataIndex: 'key', width: 250 },
    { text: 'hash', dataIndex: 'hash' },
    { text: 'value', dataIndex: 'value', flex: 1,
      renderer: function (value) {
        return Ext.JSON.encode(value);
      }
    }
  ]
});
