/*global Ext, NX*/

/**
 * Unsupported browser uber mode controller.
 *
 * @since 3.0
 */
Ext.define('NX.controller.UnsupportedBrowser', {
  extend: 'NX.app.Controller',
  requires: [
    'NX.State'
  ],

  views: [
    'UnsupportedBrowser',
    'header.Panel',
    'header.Branding',
    'header.Logo',
    'footer.Panel',
    'footer.Branding'
  ],

  refs: [
    {
      ref: 'viewport',
      selector: 'viewport'
    },
    {
      ref: 'unsupportedBrowser',
      selector: 'nx-unsupported-browser'
    }
  ],

  /**
   * @override
   */
  init: function () {
    var me = this;

    me.listen({
      component: {
        'viewport': {
          afterrender: me.onLaunch
        },
        'nx-unsupported-browser button[action=continue]': {
          click: me.onContinue
        }
      }
    });
  },

  /**
   * Show {@link NX.view.UnsupportedBrowser} view from {@link Ext.container.Viewport}.
   *
   * @override
   */
  onLaunch: function () {
    var me = this,
        viewport = me.getViewport();

    if (viewport) {
      //<if debug>
      me.logDebug('Showing unsupported browser view');
      //</if>

      viewport.add({ xtype: 'nx-unsupported-browser' });
    }
  },

  /**
   * Removes {@link NX.view.UnsupportedBrowser} view from {@link Ext.container.Viewport}.
   *
   * @override
   */
  onDestroy: function () {
    var me = this,
        viewport = me.getViewport();

    if (viewport) {
      //<if debug>
      me.logDebug('Removing unsupported browser view');
      //</if>

      viewport.remove(me.getUnsupportedBrowser());
    }
  },

  onContinue: function () {
    NX.State.setBrowserSupported(true);
  }

});
