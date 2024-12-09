/*global Ext, NX*/

/**
 * Configuration specific to Maven repositories.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.view.repository.facet.Maven2Facet', {
  extend: 'Ext.form.FieldContainer',
  alias: 'widget.nx-coreui-repository-maven2-facet',
  requires: [
    'NX.I18n'
  ],

  defaults: {
    allowBlank: false,
    queryMode: 'local',
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
        title: NX.I18n.get('Repository_Facet_Maven2Facet_Title'),

        items: [
          {
            xtype: 'combo',
            name: 'attributes.maven.versionPolicy',
            itemId: 'versionPolicy',
            fieldLabel: NX.I18n.get('Maven2Facet_VersionPolicy_FieldLabel'),
            helpText: NX.I18n.get('Maven2Facet_VersionPolicy_HelpText'),
            emptyText: NX.I18n.get('Maven2Facet_VersionPolicy_EmptyText'),
            editable: false,
            store: [
              ['RELEASE', NX.I18n.get('Maven2Facet_VersionPolicy_ReleaseItem')],
              ['SNAPSHOT', NX.I18n.get('Maven2Facet_VersionPolicy_SnapshotItem')],
              ['MIXED', NX.I18n.get('Maven2Facet_VersionPolicy_MixedItem')]
            ],
            value: 'RELEASE',
            readOnlyOnUpdate: true
          },
          {
            xtype: 'combo',
            name: 'attributes.maven.layoutPolicy',
            fieldLabel: NX.I18n.get('Repository_Facet_Maven2Facet_LayoutPolicy_FieldLabel'),
            helpText: NX.I18n.get('Repository_Facet_Maven2Facet_LayoutPolicy_HelpText'),
            emptyText: NX.I18n.get('Repository_Facet_Maven2Facet_LayoutPolicy_EmptyText'),
            editable: false,
            store: [
              ['STRICT', NX.I18n.get('Repository_Facet_Maven2Facet_LayoutPolicy_StrictItem')],
              ['PERMISSIVE', NX.I18n.get('Repository_Facet_Maven2Facet_LayoutPolicy_PermissiveItem')]
            ],
            value: 'STRICT'
          }
        ]
      }
    ];

    me.callParent();
  }

});
