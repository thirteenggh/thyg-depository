/*global Ext, NX*/

/**
 * Select task type window.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.view.task.TaskSelectType', {
  extend: 'NX.view.drilldown.Master',
  alias: 'widget.nx-coreui-task-selecttype',
  requires: [
    'NX.I18n'
  ],

  /**
   * @override
   */
  initComponent: function() {
    var me = this;

    me.columns = [
      {
        xtype: 'nx-iconcolumn',
        width: 36,
        iconVariant: 'x16',
        iconName: function() {
          return 'task-default';
        }
      },
      { header: NX.I18n.get('Task_TaskSelectType_Name_Header'), dataIndex: 'name', flex: 1 }
    ];

    me.plugins = [
      { ptype: 'gridfilterbox', emptyText: NX.I18n.get('Task_TaskSelectType_Filter_EmptyText') }
    ];

    me.store = Ext.create('NX.coreui.store.TaskType');
    me.store.addFilter([
      { property: 'exposed', value: true }
    ], false);

    // Add a white background behind the filter, to make it look like part of the header
    me.dockedItems = [
      {
        xtype: 'nx-actions'
      }
    ];

    me.callParent();
  }

});
