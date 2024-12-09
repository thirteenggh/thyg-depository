/*global Ext, NX*/

/**
 * Repository "Settings" form for a bower Group repository.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.view.repository.recipe.BowerGroup', {
  extend: 'NX.coreui.view.repository.RepositorySettingsForm',
  alias: 'widget.nx-coreui-repository-bower-group',
  requires: [
    'NX.coreui.view.repository.facet.StorageFacet',
    'NX.coreui.view.repository.facet.GroupFacet'
  ],

  /**
   * @override
   */
  initComponent: function() {
    var me = this;

    me.items = [
      {xtype: 'nx-coreui-repository-storage-facet'},
      {xtype: 'nx-coreui-repository-group-facet', format: 'bower'}
    ];

    me.callParent();
  }
});
