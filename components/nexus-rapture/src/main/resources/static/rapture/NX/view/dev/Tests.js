/*global Ext*/

/**
 * Contains various buttons to execute actions for development/testing.
 *
 * @since 3.0
 */
Ext.define('NX.view.dev.Tests', {
  extend: 'Ext.panel.Panel',
  alias: 'widget.nx-dev-tests',

  title: 'Tests',

  layout: {
    type: 'vbox',
    padding: 4,
    defaultMargins: {top: 0, right: 0, bottom: 4, left: 0}
  },

  items: [
    { xtype: 'button', text: 'clear local state', action: 'clearLocalState' },
    { xtype: 'button', text: 'javascript error', action: 'testError' },
    { xtype: 'button', text: 'ext error', action: 'testExtError' },
    { xtype: 'button', text: 'message types', action: 'testMessages' },
    { xtype: 'button', text: 'toggle unsupported browser', action: 'toggleUnsupportedBrowser'},
    { xtype: 'button', text: 'show quorum warning', action: 'showQuorumWarning'}
  ]
});
