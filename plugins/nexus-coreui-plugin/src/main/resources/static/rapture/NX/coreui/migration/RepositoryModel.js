/*global Ext, NX*/

/**
 * Migration repository model.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.migration.RepositoryModel', {
  extend: 'Ext.data.Model',
  idProperty: 'repository',
  fields: [
    {name: 'repository', type: 'string', sortType: 'asUCText'},
    {name: 'name', type: 'string', sortType: 'asUCText'},
    {name: 'type', type: 'string', sortType: 'asUCText'},
    {name: 'format', type: 'string', sortType: 'asUCText'},
    {name: 'supported', type: 'boolean'},
    {name: 'status', type: 'string'},
    {name: 'dataStore', type: 'string'},
    {name: 'blobStore', type: 'string'},
    {name: 'ingestMethod', type: 'string'}
  ]
});
