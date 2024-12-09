/*global Ext, NX*/

/**
 * Grid styles.
 *
 * @since 3.0
 */
Ext.define('NX.view.dev.styles.Grids', {
  extend: 'NX.view.dev.styles.StyleSection',
  requires: [
    'Ext.data.ArrayStore'
  ],

  title: 'Grids',

  /**
   * @protected
   */
  initComponent: function () {
    var me = this,
        store;

    store = Ext.create('Ext.data.ArrayStore', {
      fields: [
        'id',
        'name'
      ],
      data: [
        ['foo', 'Foo'],
        ['bar', 'Bar'],
        ['baz', 'Baz']
      ]
    });

    me.items = [
      {
        xtype: 'grid',
        store: store,
        height: 200,
        width: 200,
        columns: [
          { text: 'ID', dataIndex: 'id' },
          { text: 'Name', dataIndex: 'name' }
        ]
      }
    ];

    me.callParent();
  }
});
