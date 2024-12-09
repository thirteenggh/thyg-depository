/*global Ext, NX*/

/**
 * Migration content-options screen.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.migration.ContentScreen', {
  extend: 'NX.wizard.FormScreen',
  alias: 'widget.nx-coreui-migration-content',
  requires: [
    'NX.I18n'
  ],

  /**
   * @override
   */
  initComponent: function () {
    var me = this;

    Ext.apply(me, {
      title: NX.I18n.render(me, 'Title'),

      description: NX.I18n.render(me, 'Description'),

      fields: [
        {
          xtype: 'checkboxgroup',
          columns: 1,
          allowBlank: false,
          items: [
            {
              xtype: 'checkbox',
              name: 'repositories',
              boxLabel: NX.I18n.render(me, 'Repositories_FieldLabel'),
              checked: true
            },
            {
              xtype: 'checkbox',
              name: 'configuration',
              boxLabel: NX.I18n.render(me, 'Configuration_FieldLabel'),
              checked: true
            }
          ]
        }
      ],

      buttons: ['back', 'next', 'cancel']
    });

    me.callParent();
    me.down('form').settingsForm = true;
  },

  /**
   * Returns the state of the screen form
   *
   * @return {boolean}
   */
  isDirty: function() {
    return this.down('form').isDirty();
  }
});
