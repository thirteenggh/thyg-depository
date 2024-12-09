/*global Ext, NX*/

/**
 * Repository model.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.model.Repository', {
  extend: 'Ext.data.Model',
  idProperty: 'name',
  fields: [
    {name: 'name', type: 'string', sortType: 'asUCText'},
    {name: 'type', type: 'string', sortType: 'asUCText'},
    {name: 'format', type: 'string', sortType: 'asUCText'},
    {name: 'recipe', type: 'string', sortType: 'asUCText'},
    {name: 'online', type: 'boolean'},
    {name: 'status', type: 'auto' /*object*/},
    {name: 'attributes', type: 'auto' /*object*/},
    {name: 'url', type: 'string', sortType: 'asUCText'}
  ]
});
