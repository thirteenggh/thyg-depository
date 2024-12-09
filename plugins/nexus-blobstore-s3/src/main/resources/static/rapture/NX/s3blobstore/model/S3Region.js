/*global Ext, NX*/

/**
 * S3Region model.
 *
 * @since 3.17
 */
Ext.define('NX.s3blobstore.model.S3Region', {
  extend: 'Ext.data.Model',
  fields: [
    {name: 'id', type: 'string', sortType: 'asUCText'},
    {name: 'name', type: 'string', sortType: 'asUCText'},
    {name: 'order', sortType: 'asInt'}
  ]
});
