/*global Ext, NX*/

/**
 * REST API controller.
 *
 * @since 3.6
 */
Ext.define('NX.coreui.controller.Api', {
  extend: 'NX.app.Controller',
  requires: [
    'NX.util.Url',
    'NX.State',
    'NX.Permissions',
    'NX.I18n'
  ],

  views: [
    'system.Api'
  ],

  refs: [
    { ref: 'apiPage', selector: 'nx-rest-api' }
  ],

  /**
   * @override
   */
  init: function () {
    var me = this;

    me.getApplication().getFeaturesController().registerFeature({
      mode: 'admin',
      path: '/System/API',
      text: NX.I18n.get('Api_Text'),
      description: NX.I18n.get('Api_Description'),
      view: { xtype: 'nx-rest-api' },
      iconConfig: {
        file: 'http_status_ok_success.png',
        variants: ['x16', 'x32']
      },
      visible: function () {
        return NX.Permissions.check('nexus:settings:read');
      }
    }, me);

    me.listen({
      controller: {
        '#Refresh': {
          refresh: me.refreshApiContent
        },
        '#State': {
          userchanged: me.refreshApiContent
        }
      },
      component: {
        'nx-rest-api': {
          afterrender: me.refreshApiContent
        }
      }
    });
  },

  /**
   * @private
   * Add REST API content to the view
   */
  refreshApiContent: function () {
    var me = this,
        url = NX.util.Url.cacheBustingUrl(NX.util.Url.urlOf('swagger-ui/')),
        apiPage = me.getApiPage();

    if (apiPage) {

      // add the REST API iframe to the view
      apiPage.removeAll();
      apiPage.add({
        xtype: 'uxiframe',
        itemId: 'api',
        anchor: '100%',
        width: '100%',
        flex: 1,
        border: false,
        frame: false,
        hidden: false,
        src: url
      });
    }
  }

});
