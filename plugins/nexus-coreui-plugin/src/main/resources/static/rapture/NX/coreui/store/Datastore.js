/*global Ext, NX*/

/**
 * Datastore format.
 *
 * @since 3.19
 */
Ext.define('NX.coreui.store.Datastore', {
  extend: 'Ext.data.Store',
  model: 'NX.coreui.model.Datastore',

  proxy: {
    type: 'direct',

    api: {
      read: 'NX.direct.coreui_Datastore.read'
    },

    reader: {
      type: 'json',
      rootProperty: 'data',
      successProperty: 'success'
    }
  },

  sorters: { property: 'name', direction: 'ASC' }

});
