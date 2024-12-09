/*global Ext*/

/**
 * Developer Conditions grid.
 *
 * @since 3.0
 */
Ext.define('NX.view.dev.Conditions', {
  extend: 'Ext.grid.Panel',
  alias: 'widget.nx-dev-conditions',

  title: 'Conditions',
  store: 'NX.store.dev.Condition',
  emptyText: 'No condition',
  viewConfig: {
    deferEmptyText: false
  },

  columns: [
    { text: 'id', dataIndex: 'id', flex: 1 },
    { text: 'condition', dataIndex: 'condition', flex: 3 },
    {
      xtype: 'nx-iconcolumn',
      text: 'satisfied',
      dataIndex: 'satisfied',
      width: 80,
      align: 'center',
      iconVariant: 'x16',
      iconName: function (value) {
        return value ? 'permission-granted' : 'permission-denied';
      }
    }
  ],

  plugins: [
    'gridfilterbox'
  ],

  tbar : [
    { xtype: 'checkbox', itemId: 'showSatisfied', boxLabel: 'Show Satisfied', value: true },
    { xtype: 'checkbox', itemId: 'showUnsatisfied', boxLabel: 'Show Unsatisfied', value: true }
  ]

});
