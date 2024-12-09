/*global Ext, NX*/

/**
 * Datastore model.
 *
 * @since 3.19
 */
Ext.define('NX.coreui.model.Datastore', {
  extend: 'Ext.data.Model',
  idProperty: 'name',
  fields: [
    {name: 'name', type: 'string', sortType: 'asUCText'},
    {name: 'isContentStore', type: 'boolean'}
  ]
});
