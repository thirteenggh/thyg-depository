/*global Ext, NX*/
/**
 * Repository "Settings" form for a P2 Proxy repository
 */
Ext.define('NX.coreui.view.repository.recipe.P2Proxy', {
  extend: 'NX.coreui.view.repository.RepositorySettingsForm',
  alias: 'widget.nx-coreui-repository-p2-proxy',
  requires: [
    'NX.coreui.view.repository.facet.ProxyFacet',
    'NX.coreui.view.repository.facet.StorageFacet',
    'NX.coreui.view.repository.facet.RoutingRuleFacet',
    'NX.coreui.view.repository.facet.HttpClientFacet',
    'NX.coreui.view.repository.facet.NegativeCacheFacet',
    'NX.coreui.view.repository.facet.CleanupPolicyFacet'
  ],
  /**
   * @override
   */
  initComponent: function () {
    var me = this;
    me.items = [
      {xtype: 'nx-coreui-repository-proxy-facet'},
      {xtype: 'nx-coreui-repository-storage-facet'},
      {xtype: 'nx-coreui-repository-routing-rule-facet'},
      {xtype: 'nx-coreui-repository-httpclient-facet'},
      {xtype: 'nx-coreui-repository-negativecache-facet'},
      {xtype: 'nx-coreui-repository-cleanup-policy-facet'}
     ];
    me.callParent();
    Ext.ComponentQuery.query('checkbox[name=attributes.httpclient.autoBlock]')[0].setValue(false);
  }
});
