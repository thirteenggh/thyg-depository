/*global Ext, NX*/

/**
 * Migration progress store.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.migration.ProgressStore', {
  extend: 'Ext.data.ArrayStore',
  requires: [
    'NX.coreui.migration.ProgressModel'
  ],

  model: 'NX.coreui.migration.ProgressModel'
});
