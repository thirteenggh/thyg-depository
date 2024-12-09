/*global Ext, NX*/

/**
 * Configuration specific to npm proxy repos
 *
 * @since 3.29
 */
Ext.define('NX.coreui.view.repository.facet.NpmProxyFacet', {
  extend: 'Ext.form.FieldContainer',
  alias: 'widget.nx-coreui-repository-npm-proxy-facet',
  requires: [
    'NX.I18n'
  ],

  defaults: {
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
        cls: 'nx-form-section',
        title: NX.I18n.get('Repository_Facet_Npm_Title'),
        items: [
          {
            xtype: 'checkbox',
            name: 'attributes.npm.removeNonCataloged',
            value: false,
            fieldLabel: NX.I18n.get('Repository_Facet_Npm_RemoveNonCataloged_Label'),
            helpText: NX.I18n.get('Repository_Facet_Npm_RemoveNonCataloged_HelpText')
          }
        ]
      }
    ];

    me.callParent();
  }

});
