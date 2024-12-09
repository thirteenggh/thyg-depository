/*global Ext, NX*/

/**
 * Toolbar styles.
 *
 * @since 3.0
 */
Ext.define('NX.view.dev.styles.Toolbars', {
  extend: 'NX.view.dev.styles.StyleSection',

  title: 'Toolbars',

  /**
   * @protected
   */
  initComponent: function () {
    var me = this;

    function toolbar(scale) {
      var obj = {
        xtype: 'toolbar',
        items: [
          'text',
          {
            xtype: 'button',
            text: 'plain'
          },
          {
            xtype: 'button',
            text: 'with icon',
            iconCls: 'nx-icon-help-kb-x16'
          },
          ' ', // spacer
          {
            xtype: 'button',
            text: 'button menu',
            menu: [
              { text: 'plain' },
              { text: 'with icon', iconCls: 'nx-icon-help-kb-x16'}
            ]
          },
          '-', // sep
          {
            xtype: 'splitbutton',
            text: 'split button',
            menu: Ext.widget('menu', {
              items: [
                {text: 'Item 1'},
                {text: 'Item 2'}
              ]
            })
          },
          {
            xtype: 'button',
            enableToggle: true,
            pressed: true,
            text: 'toggle button'
          },
          '->', // spring
          {
            xtype: 'nx-searchbox',
            ariaLabel: 'Sample search box',
            width: 200
          }
        ]
      };

      if (scale) {
        Ext.apply(obj, {
          defaults: {
            scale: scale
          }
        });
      }

      return obj;
    }

    me.items = [
      me.label('default'),
      toolbar(undefined),
      me.label('scale: medium'),
      toolbar('medium'),
      me.label('scale: large'),
      toolbar('large')
    ];

    me.callParent();
  }
});
