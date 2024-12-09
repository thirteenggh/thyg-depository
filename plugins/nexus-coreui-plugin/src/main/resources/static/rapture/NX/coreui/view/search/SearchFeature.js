/*global Ext, NX*/

/**
 * Search feature.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.view.search.SearchFeature', {
  extend: 'NX.view.drilldown.Drilldown',
  alias: 'widget.nx-coreui-searchfeature',

  cls: 'nx-coreui-searchfeature',
  iconCls: 'x-fa fa-search',

  initComponent: function() {
    var me = this;

    me.masters = [
      {
        // FIXME: change to container
        xtype: 'panel',
        layout: {
          type: 'vbox',
          align: 'stretch',
          pack: 'start'
        },
        items: [
          {
            // FIXME: change to container
            xtype: 'panel',
            cls: 'criteria',
            itemId: 'criteria',

            header: false,
            layout: 'column'

            // disable saving for now
            //tbar: [
            //  { xtype: 'button', text: 'Save', glyph: 'xf0c7@FontAwesome', action: 'save' },
            //],
          },
          {
            xtype: 'panel',
            itemId: 'info',
            ui: 'nx-info-message',
            cls: 'info-message',
            iconCls: NX.Icons.cls('message-primary', 'x16'),
            hidden: true
          },
          {
            xtype: 'panel',
            itemId: 'warning',
            ui: 'nx-info-message',
            cls: 'warning-message',
            iconCls: NX.Icons.cls('message-danger', 'x16'),
            hidden: true
          },
          {
            xtype: 'nx-coreui-search-result-list',
            cls: 'nx-search-result-list',
            flex: 1,
            header: false
          }
        ]
      },
      {
        // FIXME: change to container?
        xtype: 'panel',
        layout: {
          type: 'vbox',
          align: 'stretch',
          pack: 'start'
        },
        items: [
          {
            xtype: 'nx-coreui-component-details'
          },
          {
            xtype: 'nx-coreui-component-asset-list',
            flex: 1
          }
        ]
      }
    ];

    me.detail = {
      xtype: 'nx-coreui-component-assetcontainer',
      header: false,
      flex: 1
    };

    me.callParent();
  }

});
