/*global Ext*/

/**
 * The developer panel.
 *
 * @since 3.0
 */
Ext.define('NX.view.dev.Panel', {
  extend: 'Ext.panel.Panel',
  requires: [
    'NX.view.dev.Styles'
  ],
  alias: 'widget.nx-dev-panel',
  ariaRole: 'region',
  title: 'Developer',
  iconCls: 'x-fa fa-bug',
  ui: 'nx-developer',
  stateful: true,
  stateId: 'nx-dev-panel',

  tools: [
    { type: 'maximize', tooltip: 'Maximize' }
  ],

  layout: 'fit',
  items: {
    xtype: 'tabpanel',
    tabPosition: 'bottom',

    stateful: true,
    stateId: 'nx-dev-panel.tabs',
    stateEvents: [ 'tabchange' ],

    /**
     * @override
     */
    getState: function() {
      return {
        activeTabId: this.items.findIndex('id', this.getActiveTab().id)
      };
    },

    /**
     * @override
     */
    applyState: function(state) {
      this.setActiveTab(state.activeTabId);
    },

    items: [
      { xtype: 'nx-dev-tests' },
      { xtype: 'nx-dev-styles' },
      { xtype: 'nx-dev-icons' },
      { xtype: 'nx-dev-features' },
      { xtype: 'nx-dev-permissions' },
      { xtype: 'nx-dev-state' },
      { xtype: 'nx-dev-stores' },
      { xtype: 'nx-dev-logging' }
    ]
  }
});
