/*global Ext, NX*/

/**
 * S3 Region store.
 *
 * @since 3.17
 */
Ext.define('NX.s3blobstore.store.S3Region', {
  extend: 'Ext.data.Store',
  model: 'NX.s3blobstore.model.S3Region',

  proxy: {
    type: 'direct',
    paramsAsHash: false,

    api: {
      read: 'NX.direct.s3_S3.regions'
    },

    reader: {
      type: 'json',
      rootProperty: 'data',
      successProperty: 'success'
    }
  },

  sorters: [
    { property: 'order', direction: 'ASC' },
    { property: 'name', direction: 'ASC' }
  ]

});
