/*global Ext, NX*/

/**
 * bower repository search contribution.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.controller.SearchBower', {
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

    search.registerFilter({
      id: 'bower',
      name: 'bower',
      text: NX.I18n.get('SearchBower_Text'),
      description: NX.I18n.get('SearchBower_Description'),
      readOnly: true,
      criterias: [
        { id: 'format', value: 'bower', hidden: true },
        { id: 'name.raw' },
        { id: 'version' }
      ]
    }, me);
  }

});
