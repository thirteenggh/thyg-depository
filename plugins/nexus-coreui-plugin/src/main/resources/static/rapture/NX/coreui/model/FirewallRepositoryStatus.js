/*global Ext, NX*/

/**
 * Firewall repository status model.
 *
 * @since 3.2
 */
Ext.define('NX.coreui.model.FirewallRepositoryStatus', {
  extend: 'Ext.data.Model',
  idProperty: 'repositoryName',
  fields: [
    {name:'repositoryName', type: 'string', sortType: 'asUCText'},
    {name:'affectedComponentCount', type: 'int'},
    {name:'criticalComponentCount', type: 'int'},
    {name:'moderateComponentCount', type: 'int'},
    {name:'quarantinedComponentCount', type: 'int'},
    {name:'severeComponentCount', type: 'int'},
    {name:'reportUrl', type: 'string', sortType: 'asUCText'},
    {name:'message', type: 'string', sortType: 'asUCText'},
    {name:'errorMessage', type: 'string', sortType: 'asUCText'}
  ]
});
