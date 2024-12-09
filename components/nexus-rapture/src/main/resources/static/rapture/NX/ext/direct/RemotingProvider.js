/*global Ext*/

/**
 *  **{@link Ext.direct.RemotingProvider}** overrides.
 *
 *  @since 3.0
 */
Ext.define('NX.ext.direct.RemotingProvider', {
  override: 'Ext.direct.RemotingProvider',

  /**
   * Avoid buffering if "enableBuffer" option is false.
   * Ensure timeout is set on requests.
   *
   * @override
   */
  queueTransaction: function(transaction) {
    // NEXUS-18220, NEXUS-18494 - Usages of NX.direct.* may not have set the user specified connection timeout
    transaction.timeout = transaction.timeout || Ext.Ajax.getTimeout();

    if (transaction.callbackOptions && transaction.callbackOptions.enableBuffer === false) {
      this.sendTransaction(transaction);
      return;
    }
    this.callParent(arguments);
  },

  /**
   * Ensure timeout is set on requests.
   *
   * @since 3.15
   * @override
   */
  sendTransaction: function(transaction) {
    // NEXUS-18220, NEXUS-18494 - Usages of NX.direct.* may not have set the user specified connection timeout
    transaction.timeout = transaction.timeout || Ext.Ajax.getTimeout();
    this.callParent(arguments);
  }
});
