/*global Ext, NX*/

/**
 * Migration preview screen.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.migration.PreviewScreen', {
  extend: 'NX.wizard.GridScreen',
  alias: 'widget.nx-coreui-migration-preview',
  requires: [
    'NX.I18n'
  ],

  /**
   * @override
   */
  initComponent: function () {
    var me = this;

    Ext.apply(me, {
      title: NX.I18n.render(me, 'Title'),

      description: NX.I18n.render(me, 'Description'),

      grid: {
        xtype: 'grid',

        viewConfig: {
          stripeRows: true
        },

        hideHeaders: true,

        columns: [
          {
            xtype: 'nx-iconcolumn',
            width: 36,
            iconVariant: 'x16',
            iconName: function (value, meta, record) {
              var state = record.get('state');
              switch (state) {
                case 'INITIALIZED':
                  return 'migration-step-pending';

                default:
                  return 'migration-step-error';
              }
            }
          },
          {
            header: NX.I18n.render(me, 'Name_Column'),
            dataIndex: 'name',
            flex: 1
          },
          {
            header: NX.I18n.render(me, 'State_Column'),
            dataIndex: 'state'
          }
        ],

        store: 'NX.coreui.migration.PreviewStore',

        features: [
          {
            ftype: 'grouping',
            collapsible: false,
            enableGroupingMenu: false
          }
        ]
      },

      buttons: [
        'back',
        {
          text: NX.I18n.render(me, 'Begin_Button'),
          action: 'begin',
          ui: 'nx-primary',
          disabled: true
        },
        'cancel'
      ]
    });

    me.callParent();
  }
});
