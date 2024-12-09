/*global Ext, NX*/

/**
 * Repository "Settings" form for a Maven Hosted repository.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.view.repository.recipe.RawHosted', {
  extend: 'NX.coreui.view.repository.RepositorySettingsForm',
  alias: 'widget.nx-coreui-repository-raw-hosted',
  requires: [
    'NX.coreui.view.repository.facet.RawFacet',
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
      {xtype: 'nx-coreui-repository-raw-facet'},
      {xtype: 'nx-coreui-repository-storage-facet', strictContentTypeValidation: false},
      {xtype: 'nx-coreui-repository-storage-hosted-facet', writePolicy: 'ALLOW'},
      {xtype: 'nx-coreui-repository-cleanup-policy-facet'}
    ];

    me.callParent();
  }
});
