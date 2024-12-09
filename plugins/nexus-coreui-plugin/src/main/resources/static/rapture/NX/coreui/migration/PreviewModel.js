/*global Ext, NX*/

/**
 * Migration preview model.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.migration.PreviewModel', {
  extend: 'Ext.data.Model',
  fields: [
    {name: 'id', type: 'string'},
    {name: 'name', type: 'string'},
    {name: 'state', type: 'string'},
    {name: 'phase', type: 'string'}
  ]
});
