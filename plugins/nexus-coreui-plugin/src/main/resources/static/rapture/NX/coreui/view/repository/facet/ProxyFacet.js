/*global Ext, NX*/

/**
 * Configuration specific to proxy repositories.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.view.repository.facet.ProxyFacet', {
  extend: 'Ext.form.FieldContainer',
  alias: 'widget.nx-coreui-repository-proxy-facet',
  requires: [
    'NX.I18n'
  ],

  defaults: {
    allowBlank: false,
    itemCls: 'required-field'
  },

  /**
   * @override
   */
  initComponent: function() {
    var me = this;

    me.items = [
      {
        xtype: 'fieldset',
        itemId: 'proxyFieldSet',
        cls: 'nx-form-section',
        title: NX.I18n.get('Repository_Facet_ProxyFacet_Title'),

        items: [
          {
            xtype: 'nx-url',
            itemId: 'remoteUrl',
            name: 'attributes.proxy.remoteUrl',
            fieldLabel: NX.I18n.get('Repository_Facet_ProxyFacet_Remote_FieldLabel'),
            helpText: NX.I18n.get('Repository_Facet_ProxyFacet_Remote_HelpText'),
            emptyText: NX.I18n.get('Repository_Facet_ProxyFacet_Remote_EmptyText'),
            allowBlank: false,
            useTrustStore: function (field) {
              if (Ext.String.startsWith(field.getValue(), 'https://')) {
                return {
                  name: 'attributes.httpclient.connection.useTrustStore',
                  url: field
                };
              }
              return undefined;
            }
          },
          {
            xtype: 'checkbox',
            name: 'attributes.httpclient.blocked',
            fieldLabel: NX.I18n.get('Repository_Facet_ProxyFacet_Blocked_FieldLabel'),
            helpText: NX.I18n.get('Repository_Facet_ProxyFacet_Blocked_HelpText'),
            value: false
          },
          {
            xtype: 'checkbox',
            name: 'attributes.httpclient.autoBlock',
            fieldLabel: NX.I18n.get('Repository_Facet_ProxyFacet_Autoblock_FieldLabel'),
            helpText: NX.I18n.get('Repository_Facet_ProxyFacet_Autoblock_HelpText'),
            value: true
          },
          {
            xtype: 'numberfield',
            name: 'attributes.proxy.contentMaxAge',
            fieldLabel: NX.I18n.get('Repository_Facet_ProxyFacet_ArtifactAge_FieldLabel'),
            helpText: NX.I18n.get('Repository_Facet_ProxyFacet_ArtifactAge_HelpText'),
            minValue: -1,
            value: 1440
          },
          {
            xtype: 'numberfield',
            name: 'attributes.proxy.metadataMaxAge',
            fieldLabel: NX.I18n.get('Repository_Facet_ProxyFacet_MetadataAge_FieldLabel'),
            helpText: NX.I18n.get('Repository_Facet_ProxyFacet_MetadataAge_HelpText'),
            minValue: -1,
            value: 1440
          }
        ]
      }
    ];

    me.callParent();
  }

});
