/*global Ext, NX*/

/**
 * Search criteria model.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.model.SearchCriteria', {
  extend: 'Ext.data.Model',
  fields: [
    {name: 'id', type: 'string', sortType: 'asUCText'},
    {name: 'group', type: 'string', sortType: 'asUCText'},
    {name: 'config', type: 'auto' /*object*/}
  ]
});
