/*global Ext, NX*/

/**
 * Migration preview store.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.migration.PreviewStore', {
  extend: 'Ext.data.ArrayStore',
  requires: [
    'NX.coreui.migration.PreviewModel'
  ],

  model: 'NX.coreui.migration.PreviewModel',

  groupField: 'phase',

  // data comes back in proper order, do not attempt to re-sort
  remoteSort: true
});
