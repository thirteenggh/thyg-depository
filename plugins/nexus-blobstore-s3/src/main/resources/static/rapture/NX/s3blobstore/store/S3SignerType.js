/*global Ext, NX*/

/**
 * S3 Signer Type store.
 *
 * @since 3.17
 */
Ext.define('NX.s3blobstore.store.S3SignerType', {
  extend: 'Ext.data.Store',
  model: 'NX.s3blobstore.model.S3SignerType',

  proxy: {
    type: 'direct',
    paramsAsHash: false,

    api: {
      read: 'NX.direct.s3_S3.signertypes'
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
