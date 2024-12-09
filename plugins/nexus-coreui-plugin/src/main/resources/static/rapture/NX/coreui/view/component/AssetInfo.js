/*global Ext, NX*/

/**
 * Asset info panel.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.view.component.AssetInfo', {
  extend: 'NX.view.info.Panel',
  alias: 'widget.nx-coreui-component-assetinfo',
  requires: [
    'NX.I18n',
    'NX.coreui.util.RepositoryUrls'
  ],
  
  mixins: {
    componentUtils: 'NX.coreui.mixin.ComponentUtils'
  },

  /**
   * model to display
   */
  assetModel: null,

  /**
   * @public
   * @param {Object} assetModel the asset to display
   */
  setAssetModel: function (assetModel) {
    var me = this,
        info = {};
    me.assetModel = assetModel;

    // display common data
    var contentType = assetModel.get('contentType');
    var size = assetModel.get('size');
    info[NX.I18n.get('Assets_Info_Path')] = NX.coreui.util.RepositoryUrls.asRepositoryLink(
        assetModel, assetModel.get('format'));
    info[NX.I18n.get('Assets_Info_ContentType')] = Ext.htmlEncode(contentType);
    info[NX.I18n.get('Assets_Info_FileSize')] = Ext.util.Format.fileSize(size);
    info[NX.I18n.get('Assets_Info_Blob_Created')] = Ext.htmlEncode(assetModel.get('blobCreated'));
    info[NX.I18n.get('Assets_Info_Blob_Updated')] = Ext.htmlEncode(assetModel.get('blobUpdated'));

    if (assetModel.get('downloadCount')) {
      info[NX.I18n.get('Assets_Info_Downloaded_Count')] = Ext.htmlEncode(assetModel.get('downloadCount')) + ' ' +
          NX.I18n.get('Assets_Info_Downloaded_Unit');
    }

    info[NX.I18n.get('Assets_Info_Last_Downloaded')] = Ext.htmlEncode(
        me.mixins.componentUtils.getLastDownloadDateForDisplay(assetModel));
    info[NX.I18n.get('Assets_Info_Locally_Cached')] = Ext.htmlEncode(contentType !== 'unknown' && size > 0);
    info[NX.I18n.get('Assets_Info_BlobRef')] = Ext.htmlEncode(assetModel.get('blobRef'));
    info[NX.I18n.get('Assets_Info_ContainingRepositoryName')] = Ext.htmlEncode(assetModel.get('containingRepositoryName'));

    info[NX.I18n.get('Assets_Info_UploadedBy')] = Ext.htmlEncode(assetModel.get('createdBy'));
    info[NX.I18n.get('Assets_Info_UploadedIp')] = Ext.htmlEncode(assetModel.get('createdByIp'));

    me.showInfo(info);
  }

});
