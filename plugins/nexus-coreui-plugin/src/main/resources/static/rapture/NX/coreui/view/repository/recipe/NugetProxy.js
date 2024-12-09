/*global Ext, NX*/

/**
 * Repository "Settings" form for a NuGet Proxy repository.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.view.repository.recipe.NugetProxy', {
  extend: 'NX.coreui.view.repository.RepositorySettingsForm',
  alias: 'widget.nx-coreui-repository-nuget-proxy',
  requires: [
    'NX.coreui.view.repository.facet.ProxyFacet',
    'NX.coreui.view.repository.facet.StorageFacet',
    'NX.coreui.view.repository.facet.RoutingRuleFacet',
    'NX.coreui.view.repository.facet.HttpClientFacet',
    'NX.coreui.view.repository.facet.NegativeCacheFacet',
    'NX.coreui.view.repository.facet.NugetProxyFacet',
    'NX.coreui.view.repository.facet.CleanupPolicyFacet'
  ],

  initComponent: function() {
    var me = this;

    me.items = [
      { xtype: 'nx-coreui-repository-nugetproxy-facet'},
      { xtype: 'nx-coreui-repository-proxy-facet'},
      { xtype: 'nx-coreui-repository-storage-facet'},
      { xtype: 'nx-coreui-repository-routing-rule-facet'},
      { xtype: 'nx-coreui-repository-negativecache-facet'},
      { xtype: 'nx-coreui-repository-cleanup-policy-facet'},
      { xtype: 'nx-coreui-repository-httpclient-facet'}
    ];

    me.callParent();

    me.down('#remoteUrl').setHelpText(NX.I18n.get('Repository_Facet_ProxyFacet_Nuget_Remote_HelpText'));
  }
});
