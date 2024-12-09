/*global Ext, NX*/

/**
 * HTTP System Settings controller.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.controller.HttpSettings', {
  extend: 'NX.app.Controller',
  requires: [
    'NX.Permissions',
    'NX.I18n'
  ],

  views: [
    'system.HttpSettings'
  ],

  /**
   * @override
   */
  init: function () {
    var me = this;

    me.getApplication().getFeaturesController().registerFeature({
      mode: 'admin',
      path: '/System/HTTP',
      text: NX.I18n.get('HttpSettings_Text'),
      description: NX.I18n.get('HttpSettings_Description'),
      view: { xtype: 'nx-coreui-system-http-settings' },
      iconConfig: {
        file: 'lorry.png',
        variants: ['x16', 'x32']
      },
      visible: function () {
        return NX.Permissions.check('nexus:settings:read');
      }
    }, me);

    me.listen({
      component: {
        'nx-coreui-system-http-settings checkbox[name=httpEnabled]': {
          change: me.onHttpEnabledChanged
        }
      }
    });
  },

  /**
   * Enable HTTPS proxy settings only when HTTP proxy settings are enabled.
   *
   * @private
   */
  onHttpEnabledChanged: function (httpEnabled) {
    var form = httpEnabled.up('form'),
        httpsProxy = form.down('#httpsProxy'),
        nonProxyHosts = form.down('#nonProxyHosts');

    if (!httpEnabled.getValue()) {
      httpsProxy.collapse();
      httpsProxy.disable();
      nonProxyHosts.hide();
      nonProxyHosts.disable();
    }
    else if (httpsProxy.isDisabled()) {
      httpsProxy.enable();
      nonProxyHosts.enable();
      nonProxyHosts.show();
    }
  }

});
