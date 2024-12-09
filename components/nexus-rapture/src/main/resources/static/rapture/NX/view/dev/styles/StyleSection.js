/*global Ext, NX*/

/**
 * Support for sections of the style guild.
 *
 * @since 3.0
 */
Ext.define('NX.view.dev.styles.StyleSection', {
  extend: 'Ext.panel.Panel',

  ui: 'nx-light',
  bodyPadding: '5px 5px 5px 5px',

  /**
   * Render a block of HTML.
   *
   * @protected
   */
  html: function(html, cfg) {
    var obj = {
      xtype: 'container',
      html: html
    };
    if (cfg) {
      Ext.apply(obj, cfg);
    }
    return obj;
  },

  /**
   * Render a label with HTML.
   *
   * @protected
   */
  label: function(html, cfg) {
    var obj = {
      xtype: 'label',
      html: html
    };
    if (cfg) {
      Ext.apply(obj, cfg);
    }
    return obj;
  }
});
