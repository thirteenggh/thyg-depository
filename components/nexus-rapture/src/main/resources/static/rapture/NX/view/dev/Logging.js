/*global Ext*/

/**
 * Logging dev-panel.
 *
 * @since 3.0
 */
Ext.define('NX.view.dev.Logging', {
  extend: 'Ext.grid.Panel',
  alias: 'widget.nx-dev-logging',

  title: 'Logging',
  store: 'LogEvent',
  emptyText: 'No events in buffer',
  viewConfig: {
    deferEmptyText: false,
    // allow browser text selection
    enableTextSelection: true
  },
  multiSelect: true,

  stateful: true,
  stateId: 'nx-dev-logging',

  columns: [
    {text: 'level', dataIndex: 'level'},
    {text: 'logger', dataIndex: 'logger', flex: 1},
    {
      text: 'message',
      dataIndex: 'message',
      flex: 3,
      renderer: function(value) {
        return value.join(' ');
      }
    },
    {text: 'timestamp', dataIndex: 'timestamp', width: 130}
  ],

  tbar: [
    {
      xtype: 'button',
      text: 'Clear events',
      action: 'clear',
      iconCls: 'x-fa fa-eraser'
    },
    {
      xtype: 'button',
      text: 'Export selection',
      action: 'export',
      iconCls: 'x-fa fa-download'
    },
    '-',
    {
      xtype: 'label',
      text: 'Threshold:'
    },
    {
      xtype: 'combo',
      itemId: 'threshold',
      store: 'LogLevel',
      width: 80,
      displayField: 'name',
      valueField: 'name',
      queryMode: 'local',
      allowBlank: false,
      editable: false
    },
    '-',
    {
      xtype: 'checkbox',
      itemId: 'buffer',
      boxLabel: 'Buffer'
    },
    {
      xtype: 'numberfield',
      itemId: 'bufferSize',
      width: 50,
      allowDecimals: false,
      allowExponential: false,
      minValue: -1,
      maxValue: 999,
      value: 200,

      // listen for key events
      enableKeyEvents: true,

      // disable the spinner muck
      hideTrigger: true,
      mouseWheelEnabled: false,
      keyNavEnabled: false
    },
    '-',
    {
      xtype: 'checkbox',
      itemId: 'console',
      boxLabel: 'Mirror console'
    },
    {
      xtype: 'checkbox',
      itemId: 'remote',
      boxLabel: 'Remote events'
    }
  ],

  plugins: [
    {
      ptype: 'rowexpander',
      rowBodyTpl: Ext.create('Ext.XTemplate',
          '<table class="nx-rowexpander">',
          '<tr>',
          '<td class="x-selectable">{[this.render(values)]}</td>',
          '</tr>',
          '</table>',
          {
            compiled: true,
            render: function (values) {
              return Ext.encode(values.message);
            }
          })
    },
    {ptype: 'gridfilterbox'}
  ]
});
