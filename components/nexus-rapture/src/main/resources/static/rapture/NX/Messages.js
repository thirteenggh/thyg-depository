/*global Ext, NX*/

/**
 * Helpers to interact with Message controller.
 *
 * @since 3.0
 */
Ext.define('NX.Messages', {
  singleton: true,

  requires: [
    'NX.State'
  ],

  info: function(message) {
    this.toast(message, 'info', 'fa-info');
  },

  success: function(message) {
    this.toast(message, 'success', 'fa-check-circle');
  },

  warning: function(message) {
    this.toast(message, 'warning', 'fa-exclamation-circle');
  },

  error: function(message) {
    this.toast(message, 'error', 'fa-exclamation-triangle');
  },

  /** @private */
  toast: function(message, type, iconCls) {
    Ext.toast({
      baseCls: type,
      html:
          '<div role="presentation" class="icon x-fa ' + iconCls + '"></div>' +
          '<div class="text">' + Ext.htmlEncode(message) + '</div>' +
          '<div class="dismiss"><a aria-label="Dismiss" href="javascript:;" onclick="Ext.getCmp(this.closest(\'.x-toast\').id).close()"><i class="fa fa-times-circle"></i></a></div>',
      align: 'tr',
      anchor: Ext.ComponentQuery.query('nx-feature-content')[0],
      stickOnClick: true,
      minWidth: 150,
      maxWidth: 400,
      autoCloseDelay: NX.State.getValue('messageDuration', 5000),
      slideInDuration: NX.State.getValue('animateDuration', 800),
      slideBackDuration: NX.State.getValue('animateDuration', 500),
      hideDuration: NX.State.getValue('animateDuration', 500),
      slideInAnimation: 'elasticIn',
      slideBackAnimation: 'elasticIn',
      ariaRole: 'alert'
    });
  }
});
