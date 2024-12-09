/*global Ext, NX*/

/**
 * Button styles.
 *
 * @since 3.0
 */
Ext.define('NX.view.dev.styles.Buttons', {
  extend: 'NX.view.dev.styles.StyleSection',
  requires: [
    'Ext.XTemplate'
  ],

  title: 'Buttons',
  layout: {
    type: 'vbox',
    defaultMargins: {top: 4, right: 0, bottom: 0, left: 0}
  },

  /**
   * @protected
   */
  initComponent: function () {
    var me = this;

    var colorSampleTpl = Ext.create('Ext.XTemplate',
        '<table>',
        '<tpl for=".">',
        '<tr>',
        '<td><div class="nx-color {.}"></div></td>',
        '<td><div style="padding: 0 10px 0 0">$color-{.}</div></td>',
        '</tr>',
        '</tpl>',
        '</table>'
    );

    function button(ui, text, disabled, pressed, menu) {
      var button = {
        xtype: 'button',
        text: text,
        ui: ui,
        margin: "0 10 10 0",
        width: 100
      };

      // Initialize optional button parameters
      if (disabled) {
        button['disabled'] = true;
      }
      if (pressed) {
        button['pressed'] = true;
        button['enableToggle'] = true;
      }
      if (menu) {
        button['menu'] = [
          { text: 'First' },
          '-',
          { text: 'Second' }
        ];
      }
      else {
        button['glyph'] = 'xf036@FontAwesome';
      }

      return button;
    }

    function buttonStyle(name, colors) {
      return {
        xtype: 'container',
        layout: {
          type: 'hbox',
          defaultMargins: {top: 0, right: 4, bottom: 0, left: 0}
        },
        items: [
          me.label('ui: ' + name, { width: 80 }),
          button(name, name, false, false, false),
          button(name, name, true, false, false),
          button(name, name, false, false, true),
          me.html(colorSampleTpl.apply(colors))
        ]
      };
    }

    me.items = [
      buttonStyle('default', ['white', 'light-gainsboro', 'light-gray', 'silver', 'suva-gray', 'gray']),
      buttonStyle('nx-plain', ['white', 'light-gainsboro', 'light-gray', 'silver', 'suva-gray', 'gray']),
      buttonStyle('nx-primary', ['denim', 'light-cobalt', 'dark-denim', 'smalt', 'dark-cerulean', 'prussian-blue']),
      buttonStyle('nx-danger', ['light-cerise', 'brick-red', 'old-rose', 'fire-brick', 'shiraz', 'falu-red']),
      buttonStyle('nx-warning', ['sea-buckthorn', 'tahiti-gold', 'zest', 'rich-gold', 'afghan-tan', 'russet']),
      buttonStyle('nx-success', ['elf-green', 'dark-pigment-green', 'salem', 'jewel', 'fun-green', 'dark-jewel'])
    ];

    me.callParent();
  }
});
