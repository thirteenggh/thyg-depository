/*global Ext, NX*/

/**
 * Security Realm model.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.store.RealmType', {
  extend: 'Ext.data.Store',
  model: 'NX.coreui.model.Reference',

  proxy: {
    type: 'direct',
    paramsAsHash: false,

    api: {
      read: 'NX.direct.coreui_RealmSettings.readRealmTypes'
    },

    reader: {
      type: 'json',
      rootProperty: 'data',
      successProperty: 'success'
    }
  }

});
