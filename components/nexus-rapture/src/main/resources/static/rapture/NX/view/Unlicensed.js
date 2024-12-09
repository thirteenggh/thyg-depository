/*global Ext*/

// TODO: This is a placeholder view for what to display to the user when a license is required and missing or invalid

/**
 * Unlicensed uber mode panel.
 *
 * @since 3.0
 */
Ext.define('NX.view.Unlicensed', {
  extend: 'Ext.container.Container',
  alias: 'widget.nx-unlicensed',

  cls: 'nx-unlicensed',
  layout: 'border',

  items: [
    {
      xtype: 'nx-header-panel',
      region: 'north',
      collapsible: false
    },
    {
      xtype: 'panel',
      region: 'center',
      layout: {
        type: 'vbox',
        align: 'center',
        pack: 'center'
      },
      items: [
        {
          xtype: 'label',
          cls: 'title',
          html: 'Product License Required'
        },
        {
          xtype: 'label',
          cls: 'description',
          text: 'A license is required to use this product.'
        }
      ]
    },
    {
      xtype: 'nx-footer',
      region: 'south',
      hidden: false
    },
    {
      xtype: 'nx-dev-panel',
      region: 'south',
      collapsible: true,
      collapsed: true,
      resizable: true,
      resizeHandles: 'n',

      // keep initial constraints to prevent huge panels
      height: 300,

      // default to hidden, only show if debug enabled
      hidden: true
    }
  ]

});
