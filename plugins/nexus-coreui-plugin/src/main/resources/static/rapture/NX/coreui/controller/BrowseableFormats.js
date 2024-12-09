/*global Ext, NX*/

/**
 * BrowseableFormats management controller.
 *
 * @since 3.2.1
 */
Ext.define('NX.coreui.controller.BrowseableFormats', {
  extend: 'NX.app.Controller',
  requires: [
    'NX.State',
    'NX.coreui.util.BrowseableFormats'
  ],

  stores: [
    'BrowseableFormat'
  ],

  /**
   * @override
   */
  init: function () {
    var me = this;

    me.primeInitialFormats();

    me.listen({
      controller: {
        '#State': {
          userchanged: me.fetchFormats,
          browseableformatschanged: me.setFormats
        }
      },
      store: {
        '#BrowseableFormat': {
          load: me.fireFormatsChanged,
          update: me.onUpdate,
          remove: me.fireFormatsChanged
        }
      }
    });
  },

  /**
   * @private
   */
  primeInitialFormats: function () {
    var me = this,
        rawData = NX.State.getValue('browseableformats');

    //<if debug>
    me.logTrace('Initial visible formats:', rawData);
    //</if>

    me.getStore('BrowseableFormat').loadRawData(rawData, false);
    NX.coreui.util.BrowseableFormats.setFormats(me.getFormats());

    //<if debug>
    me.logInfo('VisiblePermissions primed');
    //</if>
  },

  /**
   * @private
   */
  onUpdate: function (store, record, operation) {
    if (operation === Ext.data.Model.COMMIT) {
      this.fireFormatsChanged();
    }
  },

  /**
   * @private
   */
  fetchFormats: function () {
    var me = this;

    NX.coreui.util.BrowseableFormats.resetFormats();
    //<if debug>
    me.logDebug('Fetching formats...');
    //</if>
    me.getStore('BrowseableFormat').load();
    NX.coreui.util.BrowseableFormats.setFormats(me.getFormats());
  },

  /**
   * @private
   */
  setFormats: function (formats) {
    var me = this;

    //<if debug>
    me.logDebug('Loading visible formats...');
    //</if>

    me.getStore('BrowseableFormat').loadRawData(formats, false);
    me.fireFormatsChanged();
  },

  /**
   * @private
   */
  fireFormatsChanged: function () {
    var me = this;

    NX.coreui.util.BrowseableFormats.setFormats(me.getFormats());

    //<if debug>
    me.logDebug('BrowseableFormats changed; Firing event');
    //</if>

    me.fireEvent('changed', NX.coreui.util.BrowseableFormats);
  },

  /**
   * @private
   * @return {Object} formats
   */
  getFormats: function () {
    var store = this.getStore('BrowseableFormat'),
        formats = [];

    store.each(function (rec) {
      formats.push(rec.get('id'));
    });

    return formats;
  }

});
