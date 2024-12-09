/*global Ext, NX*/

/**
 * Permissions management controller.
 *
 * @since 3.0
 */
Ext.define('NX.controller.Permissions', {
  extend: 'NX.app.Controller',
  requires: [
    'NX.State',
    'NX.Permissions'
  ],

  stores: [
    'Permission'
  ],

  /**
   * @override
   */
  init: function () {
    var me = this;

    me.listen({
      controller: {
        '#State': {
          userchanged: me.fetchPermissions
        }
      },
      store: {
        '#Permission': {
          load: me.firePermissionsChanged,
          update: me.onUpdate,
          remove: me.firePermissionsChanged
        }
      }
    });
  },

  /**
   * Prime initial set of permissions from state.
   *
   * @override
   */
  onLaunch: function () {
    var me = this;

    me.fetchPermissions();
  },

  /**
   * @private
   */
  onUpdate: function (store, record, operation) {
    if (operation === Ext.data.Model.COMMIT) {
      this.firePermissionsChanged();
    }
  },

  /**
   * @private
   */
  fetchPermissions: function () {
    var me = this;

    //<if debug>
    me.logDebug('Fetching permissions...');
    //</if>
    me.getStore('Permission').load();
  },

  /**
   * @private
   */
  firePermissionsChanged: function () {
    var me = this;

    NX.Permissions.setPermissions(me.getPermissions());

    //<if debug>
    me.logDebug('Permissions changed; Firing event');
    //</if>

    me.fireEvent('changed', NX.Permissions);
  },

  /**
   * @private
   * @return {Object} permissions
   */
  getPermissions: function () {
    var store = this.getStore('Permission'),
        perms = {};

    store.clearFilter();
    store.each(function (rec) {
      perms[rec.get('id')] = rec.get('permitted');
    });

    return perms;
  }

});
