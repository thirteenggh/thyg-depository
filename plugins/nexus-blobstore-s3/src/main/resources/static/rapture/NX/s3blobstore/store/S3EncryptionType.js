/*global Ext, NX*/

/**
 * S3 Encryption Type store.
 *
 * @since 3.19
 */
Ext.define('NX.s3blobstore.store.S3EncryptionType', {
  extend: 'Ext.data.Store',
  model: 'NX.s3blobstore.model.S3EncryptionType',

  proxy: {
    type: 'direct',
    paramsAsHash: false,

    api: {
      read: 'NX.direct.s3_S3.encryptionTypes'
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
