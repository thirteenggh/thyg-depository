/*global Ext, NX*/

/**
 * Supported-repository selection model.
 *
 * Assumes records are {@link NX.coreui.migration.RepositoryModel}.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.migration.SupportedSelectionModel', {
  extend: 'Ext.selection.CheckboxModel',

  mode: 'SIMPLE',

  // note this only prevents use of right-mouse click when there is already some selection :-(
  ignoreRightMouseSelection: true,

  constructor: function () {
    var me = this;
    me.callParent(arguments);

    me.on('beforeselect', function(sm, record, i, opts) {
      // only allow selection of supported records
      return record.get('supported');
    });
  },

  /**
   * @override
   */
  selectAll: function (suppressEvent) {
    var me = this,
        selections = me.store.getRange(),
        i = 0,
        len = selections.length,
        lenMinusUnsupported = len,
        start = me.getSelection().length;

    // Subtract the number of unsupported repositories from the length
    selections.forEach(function (e) {
      if (!e.get('supported')) {
        --lenMinusUnsupported;
      }
    });

    // If the corrected length is different from the starting length, select all
    if (start !== lenMinusUnsupported) {
      me.suspendChanges();
      for (i = 0; i < len; i++) {
        if (selections[i].get('supported')) {
          me.doSelect(selections[i], true, suppressEvent);
        }
      }
      me.resumeChanges();
      // fire selection change only if the number of selections differs
      if (!suppressEvent) {
        me.maybeFireSelectionChange(me.getSelection().length !== start);
      }
    }
    else {
      // Otherwise, deselect all
      me.deselectAll(suppressEvent);
    }
  }
});
