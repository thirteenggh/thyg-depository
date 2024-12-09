/*global Ext, NX*/

/**
 * Repository "Settings" form for a Conan Hosted repository.
 *
 * @since 3.28
 */
Ext.define('NX.coreui.view.repository.recipe.ConanHosted', {
  extend: 'NX.coreui.view.repository.RepositorySettingsForm',
  alias: 'widget.nx-coreui-repository-conan-hosted',
  requires: [
    'NX.coreui.view.repository.facet.StorageFacet',
    'NX.coreui.view.repository.facet.StorageFacetHosted'
  ],

  /**
   * @override
   */
  initComponent: function() {
    var me = this;

    me.items = [
      {xtype: 'nx-coreui-repository-storage-facet'},
      {xtype: 'nx-coreui-repository-storage-hosted-facet', writePolicy: 'ALLOW'}
    ];

    me.callParent();
  }
});
