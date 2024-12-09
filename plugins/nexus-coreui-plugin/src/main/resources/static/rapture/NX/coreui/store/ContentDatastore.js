/*global Ext, NX*/

/**
 * Thin wrapper that just exposes content data stores
 *
 * @since 3.19
 */
Ext.define('NX.coreui.store.ContentDatastore', {
  extend: 'Ext.data.ChainedStore',
  source: 'Datastore',
  // drill-down code expects 'load' to exist on all stores
  load: Ext.emptyFn,
  filters: {
    filterFn: function(item) {
      return item.data.isContentStore;
    }
  }
});
