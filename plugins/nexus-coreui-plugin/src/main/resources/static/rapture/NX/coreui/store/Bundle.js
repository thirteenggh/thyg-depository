/*global Ext, NX*/

/**
 * Bundle store.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.store.Bundle', {
  extend: 'Ext.data.Store',
  model: 'NX.coreui.model.Bundle',

  proxy: {
    type: 'direct',
    paramsAsHash: false,
    api: {
      read: 'NX.direct.coreui_Bundle.read'
    },

    reader: {
      type: 'json',
      rootProperty: 'data',
      successProperty: 'success'
    }
  },

  sorters: { property: 'id', direction: 'ASC' }

});
