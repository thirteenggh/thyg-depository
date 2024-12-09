/*global Ext, NX*/

/**
 * Blob store Quota Type store.
 *
 * @since 3.14
 */
Ext.define('NX.coreui.store.BlobStoreQuotaType', {
  extend: 'Ext.data.Store',
  model: 'NX.coreui.model.Reference',

  proxy: {
    type: 'direct',
    paramsAsHash: false,

    api: {
      read: 'NX.direct.coreui_Blobstore.readQuotaTypes'
    },

    reader: {
      type: 'json',
      rootProperty: 'data',
      idProperty: 'id',
      successProperty: 'success'
    }
  },

  sorters: { property: 'name', direction: 'ASC' }
});
