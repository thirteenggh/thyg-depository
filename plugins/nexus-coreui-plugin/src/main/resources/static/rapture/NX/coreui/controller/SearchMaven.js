/*global Ext, NX*/

/**
 * Maven repository search contribution.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.controller.SearchMaven', {
  extend: 'NX.app.Controller',
  requires: [
    'NX.I18n'
  ],

  /**
   * @override
   */
  init: function() {
    var me = this,
        search = me.getController('NX.coreui.controller.Search');

    search.registerCriteria([
      {
        id: 'attributes.maven2.groupId',
        group: NX.I18n.get('SearchMaven_Group'),
        config: {
          format: 'maven2',
          fieldLabel: NX.I18n.get('SearchMaven_GroupID_FieldLabel'),
          width: 250
        }
      },
      {
        id: 'attributes.maven2.artifactId',
        group: NX.I18n.get('SearchMaven_Group'),
        config: {
          format: 'maven2',
          fieldLabel: NX.I18n.get('SearchMaven_ArtifactID_FieldLabel'),
          width: 250
        }
      },
      {
        id: 'attributes.maven2.baseVersion',
        group: NX.I18n.get('SearchMaven_Group'),
        config: {
          format: 'maven2',
          fieldLabel: NX.I18n.get('SearchMaven_BaseVersion_FieldLabel'),
          width: 250
        }
      },
      {
        id: 'assets.attributes.maven2.classifier',
        group: NX.I18n.get('SearchMaven_Group'),
        config: {
          format: 'maven2',
          fieldLabel: NX.I18n.get('SearchMaven_Classifier_FieldLabel')
        }
      },
      {
        id: 'assets.attributes.maven2.extension',
        group: NX.I18n.get('SearchMaven_Group'),
        config: {
          format: 'maven2',
          fieldLabel: NX.I18n.get('SearchMaven_Extension_FieldLabel')
        }
      }
    ], me);

    search.registerFilter({
      id: 'maven2',
      name: 'Maven',
      text: NX.I18n.get('SearchMaven_Text'),
      description: NX.I18n.get('SearchMaven_Description'),
      readOnly: true,
      criterias: [
        { id: 'format', value: 'maven2', hidden: true },
        { id: 'attributes.maven2.groupId' },
        { id: 'attributes.maven2.artifactId' },
        { id: 'version' },
        { id: 'attributes.maven2.baseVersion' },
        { id: 'assets.attributes.maven2.classifier'},
        { id: 'assets.attributes.maven2.extension' }
      ]
    }, me);
  }

});
