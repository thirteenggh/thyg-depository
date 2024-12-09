/*global Ext*/

/**
 * Stores developer panel controller.
 *
 * @since 3.0
 */
Ext.define('NX.controller.dev.Stores', {
  extend: 'NX.app.Controller',
  requires: [
    'Ext.data.StoreManager'
  ],

  refs: [
    {
      ref: 'stores',
      selector: 'nx-dev-stores'
    }
  ],

  /**
   * @override
   */
  init: function () {
    var me = this;

    me.listen({
      component: {
        'nx-dev-stores combobox': {
          change: me.onStoreSelected
        },
        'nx-dev-stores button[action=load]': {
          click: me.loadStore
        },
        'nx-dev-stores button[action=clear]': {
          click: me.clearStore
        }
      }
    });
  },

  /**
   * @private
   */
  onStoreSelected: function (combobox) {
    var storeId = combobox.getValue(),
        panel = this.getStores(),
        grid = panel.down('grid'),
        store, columns = [];

    if (storeId) {
      store = Ext.data.StoreManager.get(storeId);
      if (store) {
        Ext.each(store.model.getFields(), function (field) {
          columns.push({
            text: field.name,
            dataIndex: field.name,
            renderer: function(value) {
              if (Ext.isObject(value) || Ext.isArray(value)) {
                try {
                  return Ext.encode(value);
                }
                catch (e) {
                  console.error('Failed to encode value:', value, e);
                }
              }
              return value;
            }
          });
        });
        panel.removeAll(true);
        panel.add({
          xtype: 'grid',
          autoScroll: true,
          store: store,
          columns: columns
        });
      }
    }
  },

  /**
   * @private
   */
  loadStore: function () {
    var panel = this.getStores(),
        grid = panel.down('grid');

    if (grid) {
      grid.getStore().load();
    }
  },

  /**
   * @private
   */
  clearStore: function () {
    var panel = this.getStores(),
        grid = panel.down('grid');

    if (grid) {
      grid.getStore().removeAll();
    }
  }

});
