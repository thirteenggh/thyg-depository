/*global Ext*/

/**
 * Unlicensed uber mode controller.
 *
 * @since 3.0
 */
Ext.define('NX.controller.Unlicensed', {
  extend: 'NX.app.Controller',
  requires: [
    'NX.Bookmarks',
    'NX.Messages',
    'NX.I18n'
  ],
  
  
  /**
   * Show {@link NX.view.Unlicensed} view from {@link Ext.container.Viewport}.
   *
   * @override
   */
  onLaunch: function () {
    var me = this;
    //<if debug>
    me.logDebug('Adding unlicensed listeners');
    //</if>
    Ext.History.on('change', me.forceLicensing);
    me.forceLicensing();
  },

  /**
   * Removes {@link NX.view.Unlicensed} view from {@link Ext.container.Viewport}.
   *
   * @override
   */
  onDestroy: function () {
    var me = this;
    //<if debug>
    me.logDebug('Removing unlicensed listeners');
    //</if>
    Ext.History.un('change', me.forceLicensing);
  },

  /**
   * Show a message and force navigation to the Licensing page, preventing all other navigation in the UI.
   */
  forceLicensing: function () {
    NX.Messages.error(NX.I18n.get('State_License_Invalid_Message'));
    NX.Bookmarks.navigateTo(NX.Bookmarks.fromToken('admin/system/licensing'));
  }

});
