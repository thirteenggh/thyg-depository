/*global Ext, NX*/

/**
 * @since 3.17
 */
Ext.define('NX.s3blobstore.controller.S3Blobstore', {
  extend: 'NX.wizard.Controller',

  stores: [
    'NX.s3blobstore.store.S3Region',
    'NX.s3blobstore.store.S3SignerType',
    'NX.s3blobstore.store.S3EncryptionType'
  ],
  models: [
    'NX.s3blobstore.model.S3Region',
    'NX.s3blobstore.model.S3SignerType',
    'NX.s3blobstore.model.S3EncryptionType'
  ],
  views: [
    'NX.s3blobstore.view.BlobstoreSettings'
  ]
});
