/*global Ext, NX*/

/**
 * Migration repository store.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.migration.RepositoryStore', {
  extend: 'Ext.data.ArrayStore',
  requires: [
    'NX.coreui.migration.RepositoryModel'
  ],

  model: 'NX.coreui.migration.RepositoryModel',

  sorters: { property: 'repository', direction: 'ASC' }
});
