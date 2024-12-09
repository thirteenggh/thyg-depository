/*global Ext, NX*/

/**
 * Migration plan-step detail window.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.migration.PlanStepDetailWindow', {
  extend: 'NX.view.ModalDialog',
  alias: 'widget.nx-coreui-migration-progressdetail',
  requires: [
    'Ext.XTemplate',
    'NX.I18n',
    'NX.util.DateFormat'
  ],

  config: {
    /**
     * @cfg {Object} Plan-step detail object.
     */
    detail: undefined
  },

  resizable: true,
  closable: true,
  layout: 'fit',
  height: 480,

  /**
   * @override
   */
  initComponent: function () {
    var me = this,
        store,
        timestampFmt = NX.util.DateFormat.forName('datetime')['short'];

    store = Ext.create('Ext.data.ArrayStore', {
      fields: [
        {name: 'timestamp', type: 'date', dateFormat: 'c'},
        {name: 'message', type: 'string'}
      ]
    });
    store.add(me.getDetail().entries);

    Ext.apply(me, {
      title: NX.I18n.render(me, 'Title', me.getDetail().name),
      width: NX.view.ModalDialog.LARGE_MODAL,
      items: {
        xtype: 'grid',
        viewConfig: {
          stripeRows: true,
          enableTextSelection: true,
          emptyText: NX.I18n.render(me, 'EmptyLog')
        },
        hideHeaders: true,
        columns: [
          {
            header: NX.I18n.render('Timestamp_Column'),
            dataIndex: 'timestamp',
            width: 150,
            renderer: function (value) {
              return Ext.util.Format.date(value, timestampFmt);
            }
          },
          {
            header: NX.I18n.render('Message_Column'),
            dataIndex: 'message',
            flex: 1
          }
        ],
        store: store,
        plugins: [
          {
            ptype: 'rowexpander',
            rowBodyTpl: Ext.create('Ext.XTemplate',
                '<div class="nx-rowexpander">',
                '<span class="x-selectable">{message}</span>',
                '</div>',
                {
                  compiled: true
                })
          }
        ]
      },

      buttonAlign: 'left',
      buttons: [
        {text: NX.I18n.get('Button_Close'), handler: me.close, scope: me}
      ]
    });

    me.callParent();
    me.center();
  }

});
