/*global Ext, NX*/

/**
 * Branding controller.
 *
 * @since 3.0
 */
Ext.define('NX.controller.Branding', {
  extend: 'NX.app.Controller',
  requires: [
    'NX.State'
  ],

  views: [
    'header.Branding'
  ],

  refs: [
    { ref: 'viewport', selector: 'viewport' },
    { ref: 'headerBranding', selector: 'nx-header-branding' },
    { ref: 'footer', selector: 'nx-footer' },
    { ref: 'footerBranding', selector: 'nx-footer-branding' }
  ],

  /**
   * @override
   */
  init: function() {
    var me = this;

    me.listen({
      controller: {
        '#State': {
          brandingchanged: me.onBrandingChanged
        }
      },
      component: {
        'nx-header-branding': {
          afterrender: me.renderHeaderBranding
        },
        'nx-footer-branding': {
          afterrender: me.renderFooterBranding
        }
      }
    });
  },

  /**
   * Render header/footer branding when branding configuration changes.
   *
   * @private
   */
  onBrandingChanged: function() {
    this.renderHeaderBranding();
    this.renderFooterBranding();
  },

  /**
   * Render header branding.
   *
   * @private
   */
  renderHeaderBranding: function() {
    var branding = NX.State.getValue('branding'),
        headerBranding = this.getHeaderBranding();

    if (headerBranding) {
      if (branding && branding['headerEnabled']) {
        headerBranding.update(branding['headerHtml']);
        headerBranding.show();
      }
      else {
        headerBranding.hide();
      }
    }
  },

  /**
   * Render footer branding.
   *
   * @private
   */
  renderFooterBranding: function() {
    var branding = NX.State.getValue('branding'),
        footer = this.getFooter(),
        footerBranding = this.getFooterBranding();

    if (footerBranding) {
      if (branding && branding['footerEnabled']) {
        footerBranding.update(branding['footerHtml']);
        footerBranding.show();
        footer.show();
      }
      else {
        footerBranding.hide();
        footer.hide();
      }
    }
  }

});
