/*global Ext*/

/**
 * Footer panel.
 *
 * @since 3.0
 */
Ext.define('NX.view.footer.Panel', {
  extend: 'Ext.container.Container',
  alias: 'widget.nx-footer',
  requires: [
    'NX.I18n'
  ],

  cls: 'nx-footer',
  ariaRole: 'contentinfo',

  /**
   * @override
   */
  initComponent: function() {
    Ext.apply(this, {
      layout: {
        type: 'vbox',
        align: 'stretch',
        pack: 'start'
      },

      items: [
        {
          xtype: 'container',
          cls: 'copyright',
          html: NX.I18n.get('Footer_Panel_HTML')
        },
        { xtype: 'nx-footer-branding', hidden: true }
      ]
    });

    this.callParent();
  }
});
