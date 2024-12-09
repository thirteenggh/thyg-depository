/*global Ext, NX*/

/**
 * Repository "Settings" form for a PyPI Hosted repository.
 *
 * @since 3.1
 */
Ext.define('NX.coreui.view.repository.recipe.PyPiHosted', {
  extend: 'NX.coreui.view.repository.RepositorySettingsForm',
  alias: 'widget.nx-coreui-repository-pypi-hosted',
  requires: [
    'NX.coreui.view.repository.facet.StorageFacet',
    'NX.coreui.view.repository.facet.StorageFacetHosted',
    'NX.coreui.view.repository.facet.CleanupPolicyFacet'
  ],

  /**
   * @override
   */
  initComponent: function() {
    var me = this;

    me.items = [
      { xtype: 'nx-coreui-repository-storage-facet'},
      { xtype: 'nx-coreui-repository-storage-hosted-facet'},
      { xtype: 'nx-coreui-repository-cleanup-policy-facet'}
    ];

    me.callParent();
  }
});
