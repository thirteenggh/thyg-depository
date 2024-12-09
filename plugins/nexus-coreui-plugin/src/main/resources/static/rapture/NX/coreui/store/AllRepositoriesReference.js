/*global Ext, NX*/

/**
 * All Repository reference store. includes items such as 'All Repositories', 'All Maven2 Repositories', etc.
 *
 * @since 3.1
 */
Ext.define('NX.coreui.store.AllRepositoriesReference', {
  extend: 'Ext.data.Store',
  model: 'NX.coreui.model.RepositoryReference',

  proxy: {
    type: 'direct',

    api: {
      read: 'NX.direct.coreui_Repository.readReferencesAddingEntriesForAllFormats'
    },

    reader: {
      type: 'json',
      rootProperty: 'data',
      successProperty: 'success'
    }
  },

  sortOnLoad: true,
  sorters: [{ property: 'sortOrder', direction: 'DESC' }, { property: 'name', direction: 'ASC' }]

});
