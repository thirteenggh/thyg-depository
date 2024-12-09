/*global Ext, NX*/

/**
 * S3 blob store plugin configuration
 *
 * @since 3.17
 */
Ext.define('NX.s3blobstore.app.PluginConfig', {
  '@aggregate_priority': 100,

  requires: [
    'NX.s3blobstore.app.PluginStrings'
  ],

  controllers: [
    {
      id: 'NX.s3blobstore.controller.S3Blobstore',
      active: true
    }
  ]
});
