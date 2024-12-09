/*global Ext, NX*/

/**
 * Helpers to interact with **{@link NX.controller.User}** controller.
 *
 * @since 3.0
 */
Ext.define('NX.Security', {
  singleton: true,
  requires: [
    'NX.controller.User'
  ],

  /**
   * @private
   * @returns {NX.controller.User}
   */
  controller: function () {
    return NX.getApplication().getController('User');
  },

  /**
   * @see NX.controller.User#hasUser
   */
  hasUser: function () {
    var me = this;
    if (me.controller()) {
      return me.controller().hasUser();
    }
  },

  /**
   * @see NX.controller.User#askToAuthenticate
   */
  askToAuthenticate: function (message, options) {
    var me = this;
    if (me.controller()) {
      me.controller().askToAuthenticate(message, options);
    }
  },

  /**
   * @see NX.controller.User#doWithAuthenticationToken
   */
  doWithAuthenticationToken: function (message, options) {
    var me = this;
    if (me.controller()) {
      me.controller().doWithAuthenticationToken(message, options);
    }
  },

  /**
   * @see NX.controller.User#signOut
   */
  signOut: function () {
    var me = this;
    if (me.controller()) {
      me.controller().signOut();
    }
  }

});
