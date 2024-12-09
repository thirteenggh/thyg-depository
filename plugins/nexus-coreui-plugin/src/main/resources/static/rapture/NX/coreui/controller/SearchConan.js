
/**
 * @since 3.28
 */
Ext.define('NX.coreui.controller.SearchConan', {
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
        id: 'attributes.conan.baseVersion',
        group: NX.I18n.get('SearchConan_Group'),
        config: {
          format: 'conan',
          fieldLabel: NX.I18n.get('SearchConan_BaseVersion_FieldLabel'),
          width: 250
        }
      },
      {
        id: 'attributes.conan.channel',
        group: NX.I18n.get('SearchConan_Group'),
        config: {
          format: 'conan',
          fieldLabel: NX.I18n.get('SearchConan_Channel_FieldLabel'),
          width: 250
        }
      }
    ], me);

    search.registerFilter({
      id: 'conan',
      name: 'Conan',
      text: NX.I18n.get('SearchConan_Text'),
      description: NX.I18n.get('SearchConan_Description'),
      readOnly: true,
      criterias: [
        {id: 'format', value: 'conan', hidden: true},
        {id: 'name.raw'},
        {id: 'attributes.conan.baseVersion'},
        {id: 'attributes.conan.channel'}
      ]
    }, me);
  }
});
