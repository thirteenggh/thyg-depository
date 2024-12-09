/*global Ext, NX*/

/**
 * Blobstore Type store.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.store.BlobstoreType', {
  extend: 'Ext.data.Store',
  model: 'NX.coreui.model.BlobstoreType',

  proxy: {
    type: 'direct',
    paramsAsHash: false,

    api: {
      read: 'NX.direct.coreui_Blobstore.readTypes'
    },

    reader: {
      type: 'json',
      rootProperty: 'data',
      successProperty: 'success'
    }
  },

  sorters: { property: 'name', direction: 'ASC' },
  filters: [
    { property: 'isEnabled', value: true }
  ]
});
