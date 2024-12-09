/**
 * Repository settings form for an apt repository
 *
 * @since 3.17
 */
Ext.define('NX.coreui.view.repository.recipe.AptHosted', {
  extend: 'NX.coreui.view.repository.RepositorySettingsForm',
  alias: 'widget.nx-coreui-repository-apt-hosted',
  requires: [
    'NX.coreui.view.repository.facet.AptHostedFacet',
    'NX.coreui.view.repository.facet.AptSigningFacet',
    'NX.coreui.view.repository.facet.StorageFacet',
    'NX.coreui.view.repository.facet.StorageFacetHosted',
    'NX.coreui.view.repository.facet.CleanupPolicyFacet'
  ],

  /**
   * @override
   */
  initComponent: function () {
    var me = this;

    me.items = [
      {xtype: 'nx-aptui-repository-apthosted-facet'},
      {xtype: 'nx-aptui-repository-aptsigning-facet'},
      {xtype: 'nx-coreui-repository-storage-facet'},
      {xtype: 'nx-coreui-repository-storage-hosted-facet'},
      {xtype: 'nx-coreui-repository-cleanup-policy-facet'}
    ];

    me.callParent();
  }
});
