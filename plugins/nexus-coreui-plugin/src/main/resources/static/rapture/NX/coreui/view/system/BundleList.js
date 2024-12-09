/*global Ext, NX*/

/**
 * Bundle list.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.view.system.BundleList', {
  extend: 'NX.view.drilldown.Master',
  alias: 'widget.nx-coreui-system-bundlelist',
  requires: [
    'NX.I18n'
  ],

  stateful: true,
  stateId: 'nx-coreui-system-bundlelist',

  /**
   * @override
   */
  initComponent: function() {
    Ext.apply(this, {
      store: 'Bundle',

      columns: [
        {
          xtype: 'nx-iconcolumn',
          width: 36,
          iconVariant: 'x16',
          iconName: function () {
            return 'bundle-default';
          }
        },
        {
          header: NX.I18n.get('System_BundleList_ID_Header'),
          dataIndex: 'id',
          stateId: 'id',
          width: 60,
          resizable: false,
          renderer: Ext.htmlEncode
        },
        {
          header: NX.I18n.get('System_BundleList_State_Header'),
          dataIndex: 'state',
          stateId: 'state',
          width: 80,
          resizable: false,
          renderer: Ext.htmlEncode
        },
        {
          header: NX.I18n.get('System_BundleList_Level_Header'),
          dataIndex: 'startLevel',
          stateId: 'startLevel',
          width: 60,
          resizable: false,
          renderer: Ext.htmlEncode
        },
        {
          header: NX.I18n.get('System_BundleList_Name_Header'),
          dataIndex: 'name',
          stateId: 'name',
          flex: 2,
          renderer: Ext.htmlEncode
        },
        {
          header: NX.I18n.get('System_BundleList_SymbolicName_Header'),
          dataIndex: 'symbolicName',
          stateId: 'symbolicName',
          flex: 2,
          hidden: true,
          renderer: Ext.htmlEncode
        },
        {
          header: NX.I18n.get('System_BundleList_Version_Header'),
          dataIndex: 'version',
          stateId: 'version',
          flex: 1,
          renderer: Ext.htmlEncode
        },
        {
          header: NX.I18n.get('System_BundleList_Location_Header'),
          dataIndex: 'location',
          stateId: 'location',
          hidden: true,
          renderer: Ext.htmlEncode
        },
        {
          header: NX.I18n.get('System_BundleList_Fragment_Header'),
          dataIndex: 'fragment',
          stateId: 'fragment',
          hidden: true,
          renderer: Ext.htmlEncode
        }
      ],

      // filter will install into toolbar, ensure its properly styled for drilldown
      tbar: {
        xtype: 'nx-actions'
      },

      plugins: [
        {ptype: 'gridfilterbox', emptyText: NX.I18n.get('System_BundleList_Filter_EmptyText')}
      ]
    });

    this.callParent();
  }
});
