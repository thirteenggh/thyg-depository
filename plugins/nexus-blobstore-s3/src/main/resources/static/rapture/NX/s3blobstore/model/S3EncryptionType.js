/*global Ext, NX*/

/**
 * S3Encryption model.
 *
 * @since 3.19
 */
Ext.define('NX.s3blobstore.model.S3EncryptionType', {
  extend: 'Ext.data.Model',
  fields: [
    {name: 'id', type: 'string', sortType: 'asUCText'},
    {name: 'name', type: 'string', sortType: 'asUCText'},
    {name: 'order', sortType: 'asInt'}
  ]
});
