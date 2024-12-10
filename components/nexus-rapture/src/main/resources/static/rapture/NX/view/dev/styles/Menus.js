/*global Ext, NX*/

/**
 * Menu styles.
 *
 * @since 3.0
 */
Ext.define('NX.view.dev.styles.Menus', {
  extend: 'NX.view.dev.styles.StyleSection',

  title: 'Menus',
  layout: {
    type: 'hbox',
    defaultMargins: {top: 0, right: 4, bottom: 0, left: 0}
  },

  /**
   * @protected
   */
  initComponent: function () {
    var me = this;

    function menu(text, iconCls, tooltip, action) {
      return {
        text: text,
        iconCls: iconCls,
        tooltip: tooltip,
        action: action
      };
    }

    me.items = [
      {
        xtype: 'menu',
        floating: false,
        items: [
          menu('Help for [Feature]', 'nx-icon-search-default-x16', 'Help for the current feature', 'feature'),
          '-',
          menu('About', 'nx-icon-nexus-white-x16', 'About 可信软件仓库', 'about'),
          menu('Documentation', 'nx-icon-help-manual-x16', 'Product documentation', 'docs'),
          menu('Knowledge Base', 'nx-icon-help-kb-x16', 'Knowledge base', 'kb'),
          menu('TianheCloud Guides', 'nx-icon-help-guides-x16', 'TianheCloud Guides', 'guides'),
          menu('Community', 'nx-icon-help-community-x16', 'Community information', 'community'),
          menu('Issue Tracker', 'nx-icon-help-issues-x16', 'Issue and bug tracker', 'issues'),
          '-',
          menu('Support', 'nx-icon-help-support-x16', 'Product support', 'support')
        ]
      }
    ];

    me.callParent();
  }
});
