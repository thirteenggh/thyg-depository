/*global Ext, NX*/

/**
 * Migration progress model.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.migration.ProgressModel', {
  extend: 'Ext.data.Model',
  fields: [
    {name: 'id', type: 'string'},
    {name: 'name', type: 'string'},
    {name: 'state', type: 'string'},
    {name: 'complete', type: 'number'},
    {name: 'status', type: 'string'},
    {name: 'phase', type: 'string'}
  ]
});
