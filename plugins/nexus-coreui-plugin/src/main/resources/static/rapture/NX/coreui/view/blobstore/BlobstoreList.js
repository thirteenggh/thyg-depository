/*global Ext, NX*/

/**
 * Blobstore grid.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.view.blobstore.BlobstoreList', {
  extend: 'NX.view.drilldown.Master',
  alias: 'widget.nx-coreui-blobstore-list',
  requires: [
    'NX.I18n'
  ],

  stateful: true,
  stateId: 'nx-coreui-blobstore-list',

  /**
   * @override
   */
  initComponent: function() {
    Ext.apply(this, {
      store: 'Blobstore',

      columns: [
        {
          xtype: 'nx-iconcolumn',
          width: 36,
          iconVariant: 'x16',
          iconName: function (value, meta, record) {
            return record.data.unavailable === true ? 'blobstore-failure' : 'blobstore-default';
          }
        },
        {header: NX.I18n.get('Blobstore_BlobstoreList_Name_Header'), dataIndex: 'name', stateId: 'name', flex: 1,  renderer: Ext.htmlEncode},
        {header: NX.I18n.get('Blobstore_BlobstoreList_Type_Header'), dataIndex: 'type', stateId: 'type'},
        {
          header: NX.I18n.get('Blobstore_BlobstoreList_State_Header'), dataIndex: 'state', stateId: 'state',
          renderer: function(value, metaData, record) {
            if (record.data.unavailable) {
              return NX.I18n.get('Blobstore_BlobstoreList_Failed');
            }
            else {
              return NX.I18n.get('Blobstore_BlobstoreList_Started');
            }
          }
        },
        {header: NX.I18n.get('Blobstore_BlobstoreList_BlobCount_Header'), dataIndex: 'blobCount', stateId: 'blobCount'},
        {
          header: NX.I18n.get('Blobstore_BlobstoreList_TotalSize_Header'), dataIndex: 'totalSize', stateId: 'totalSize',
          renderer: function(value, metaData, record, row, col, store, gridView) {
            if (record.data.unavailable) {
              return NX.I18n.get('Blobstore_BlobstoreList_Unavailable');
            }
            else {
              return Ext.util.Format.fileSize(value, metaData, record, row, col, store, gridView);
            }
          }
        },
        {
          header: NX.I18n.get('Blobstore_BlobstoreList_AvailableSpace_Header'), dataIndex: 'availableSpace',
          stateId: 'availableSpace', renderer: function(value, metaData, record, row, col, store, gridView) {
            if (record.data.unavailable) {
              return NX.I18n.get('Blobstore_BlobstoreList_Unavailable');
            }
            else if (record.data.unlimited) {
              return NX.I18n.get('Blobstore_BlobstoreList_Unlimited');
            }
            else {
              return Ext.util.Format.fileSize(value, metaData, record, row, col, store, gridView);
            }
          }, flex: 1
        }
      ],

      viewConfig: {
        emptyText: NX.I18n.get('Blobstore_BlobstoreList_EmptyText'),
        deferEmptyText: false
      },

      dockedItems: [
        {
          xtype: 'nx-actions',
          items: [
            {
              xtype: 'button',
              text: NX.I18n.get('Blobstore_BlobstoreList_New_Button'),
              iconCls: 'x-fa fa-plus-circle',
              action: 'new',
              disabled: true
            }
          ]
        }
      ],

      plugins: [
        {ptype: 'gridfilterbox', emptyText: NX.I18n.get('Blobstore_BlobstoreList_Filter_EmptyText')}
      ]
    });

    this.callParent();
  }

});
