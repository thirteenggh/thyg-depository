/*global Ext, NX*/

/**
 * Remote {@link NX.util.log.Sink}.
 *
 * Sends events to server via Ext.Direct.
 *
 * @since 3.0
 */
Ext.define('NX.util.log.RemoteSink', {
  extend: 'NX.util.log.Sink',

  // default to disabled
  enabled: false,

  /**
   * @override
   */
  receive: function (event) {
    // copy event to transform message
    var copy = Ext.clone(event);
    copy.message = copy.message.join(' ');
    NX.direct.rapture_LogEvent.recordEvent(copy);
  }
});
