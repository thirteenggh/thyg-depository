/*global Ext, NX*/

/**
 * Logo image.
 *
 * @since 3.0
 */
Ext.define('NX.view.header.Logo', {
  extend: 'Ext.Img',
  requires: [
    'NX.Icons'
  ],
  alias: 'widget.nx-header-logo',
  alt: 'TianheCloud',

  autoEl: 'span',
  height: 32,
  width: 32,

  /**
   * @protected
   */
  initComponent: function() {
    this.setSrc(NX.Icons.url('nexus-white', 'x32'));
    this.callParent();
  }
});
