/*global Ext, NX*/

/**
 * Asset info panel.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.view.component.AssetAttributes', {
  extend: 'Ext.grid.Panel',
  alias: 'widget.nx-coreui-component-assetattributes',
  requires: [
    'NX.I18n'
  ],

  cls: 'nx-coreui-component-assetattributes',

  manageHeight: false,

  disableSelection: true,
  rowLines: false,

  /**
   * model to display
   */
  assetModel: null,
  viewConfig: {
    enableTextSelection: true
  },
  itemId: 'attributeGrid',
  store: Ext.create('Ext.data.Store', {
    fields: ['facet', 'label', 'value'],
    groupField: 'facet'
  }),
  columns: {
    items: [
      {
        text: 'Facet',
        hidden: true,
        dataIndex: 'facet'
      },
      {
        text: 'label',
        flex: 1,
        dataIndex: 'label',
        renderer: function(value, metaData) {
          metaData.tdAttr = 'data-qtip="' + value + '"';
          return value;
        }
      },
      {
        text: 'Value',
        flex: 2,
        dataIndex: 'value',
        renderer: function (val) {
          return Ext.DomHelper.markup({
            tag: 'div',
            cls: 'attribute-value',
            html: Ext.util.Format.htmlEncode(val)
          });
        }
      }
    ]
  },
  hideHeaders: true,
  features: [
    {
      ftype: 'grouping',
      groupHeaderTpl: '{name:capitalize}'
    }
  ],

  /**
   * @public
   * @param {Object} assetModel the asset to display
   */
  setAssetModel: function(assetModel) {
    var me = this,
      store = me.getStore();
    me.assetModel = assetModel;

    // update the grid attribute data
    store.removeAll();
    Ext.iterate(me.assetModel.get('attributes'), function(facet, facetValues) {
      Ext.iterate(facetValues, function(key, value) {
        store.add({facet: facet, label: key, value: Ext.isObject(value) ? Ext.encode(value) : value});
      });
    });
  }
});
