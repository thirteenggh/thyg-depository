/*global Ext, NX*/

/**
 * Search results grid.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.view.search.SearchResultList', {
  extend: 'NX.view.drilldown.Master',
  alias: 'widget.nx-coreui-search-result-list',
  requires: [
    'NX.I18n'
  ],

  allowClearSort: true,

  /**
   * @override
   */
  initComponent: function() {
    Ext.apply(this, {
      // Mark grid as health check columns target
      healthCheckColumnsTarget: true,
      // Mark grid as a component list
      componentList: true,

      store: 'SearchResult',

      // Prevent the store from automatically loading
      loadStore: Ext.emptyFn,

      viewConfig: {
        emptyText: NX.I18n.get('Search_SearchResultList_EmptyText'),
        deferEmptyText: false
      },

      columns: [
        {
          xtype: 'nx-iconcolumn',
          width: 36,
          iconVariant: 'x16',
          iconName: function () {
            return 'search-component';
          }
        },
        {
          text: NX.I18n.get('Search_SearchResultList_Name_Header'),
          dataIndex: 'name',
          stateId: 'name',
          flex: 3,
          renderer: Ext.htmlEncode
        },
        {
          header: NX.I18n.get('Search_SearchResultList_Group_Header'),
          dataIndex: 'group',
          stateId: 'group',
          flex: 4,
          renderer: NX.ext.grid.column.Renderers.optionalData
        },
        {
          header: NX.I18n.get('Search_SearchResultList_Version_Header'),
          dataIndex: 'version',
          stateId: 'version',
          flex: 1,
          renderer: NX.ext.grid.column.Renderers.optionalData
        },
        {
          header: NX.I18n.get('Search_SearchResultList_Format_Header'),
          dataIndex: 'format',
          stateId: 'format',
          width: 100,
          renderer: Ext.htmlEncode
        },
        {
          header: NX.I18n.get('Search_SearchResultList_Repository_Header'),
          dataIndex: 'repositoryName',
          stateId: 'repositoryName',
          hidden: true,
          renderer: Ext.htmlEncode
        }
      ],

      // Show all results up to the page size
      trailingBufferZone: 300,
      leadingBufferZone: 300
    });

    this.callParent();
  }

});
