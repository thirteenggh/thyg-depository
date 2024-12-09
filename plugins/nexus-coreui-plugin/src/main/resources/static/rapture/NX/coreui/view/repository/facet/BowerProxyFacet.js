/*global Ext, NX*/

/**
 * Configuration for enabling Bower urls rewrites.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.view.repository.facet.BowerProxyFacet', {
  extend: 'Ext.form.FieldContainer',
  alias: 'widget.nx-coreui-repository-bower-proxy-facet',
  requires: [
    'NX.I18n'
  ],

  /**
   * @override
   */
  initComponent: function() {
    var me = this;

    me.items = [
      {
        xtype: 'fieldset',
        cls: 'nx-form-section',
        title: NX.I18n.get('Repository_Facet_BowerProxyFacet_Title'),
        width: 600,
        items: [
          {
            xtype: 'checkbox',
            name: 'attributes.bower.rewritePackageUrls',
            fieldLabel: NX.I18n.get('Repository_Facet_BowerProxyFacet_RewritePackageUrls_FieldLabel'),
            helpText: NX.I18n.get('Repository_Facet_BowerProxyFacet_RewritePackageUrls_HelpText'),
            value: true
          }
        ]
      }
    ];

    me.callParent();
  }

});

