/*global Ext, NX*/

/**
 * Picker styles.
 *
 * @since 3.0
 */
Ext.define('NX.view.dev.styles.Pickers', {
  extend: 'NX.view.dev.styles.StyleSection',
  requires: [
    'Ext.data.ArrayStore'
  ],

  title: 'Pickers',

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
        [ 'foo', 'Foo' ],
        [ 'bar', 'Bar' ],
        [ 'baz', 'Baz' ]
      ]
    });

    me.items = [
      {
        xtype: 'nx-itemselector',
        name: 'realms',
        buttons: ['up', 'add', 'remove', 'down'],
        fromTitle: 'Available',
        toTitle: 'Selected',
        store: store,
        valueField: 'id',
        displayField: 'name',
        delimiter: null
      }
    ];

    me.callParent();
  }
});
