/*global Ext, NX*/

/**
 * Migration repository defaults step.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.migration.RepositoryDefaultsStep', {
  extend: 'NX.wizard.Step',
  requires: [
    'NX.coreui.migration.RepositoryDefaultsScreen',
    'NX.I18n'
  ],

  config: {
    screen: 'NX.coreui.migration.RepositoryDefaultsScreen',
    enabled: true
  },

  /**
   * @override
   */
  init: function () {
    var me = this;

    me.control({
      'button[action=back]': {
        click: me.moveBack
      },
      'button[action=next]': {
        click: me.doNext
      },
      'button[action=cancel]': {
        click: me.cancel
      }
    });

    // toggle step enabled when content-options change
    me.getContext().on('add', function(index, value, key, opts) {
      if (key === 'content-options') {
        me.setEnabled(value['repositories']);
      }
    });
  },

  /**
   * Prepare the defaults form.
   *
   * @private
   */
  prepare: function () {
    var me = this;

    me.mask(NX.I18n.render(me, 'Loading_Mask'));

    me.getStore('Datastore').load();
    me.getStore('Blobstore').load();

    // load defaults from server
    NX.direct.migration_Repository.defaults(function (response, event) {
      if (event.status && response.success) {
        me.getScreenCmp().getForm().setValues(response.data);
      }
      me.unmask();
    });
  },

  /**
   * @override
   */
  reset: function () {
    var me = this,
        screen = me.getScreenCmp();

    if (screen) {
      screen.getForm().reset();
    }
    me.unset('repository-defaults');
    me.callParent();
  },

  /**
   * @private
   */
  doNext: function () {
    var me = this,
        values = me.getScreenCmp().getForm().getFieldValues();

    me.set('repository-defaults', {
      dataStore: values.dataStore,
      blobStore: values.blobStore,
      ingestMethod: values.ingestMethod
    });

    me.moveNext();
  }
});
