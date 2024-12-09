/*global Ext*/

/**
 * Panel with responsive behavior
 *
 * @since 3.19
 */
Ext.define('NX.ext.panel.ResponsivePanel', {
  extend: 'Ext.panel.Panel',
  alias: 'widget.nx-responsive-panel',

  plugins: {
    responsive: true
  },
  responsiveConfig: {
    'width <= 1366': {
      maxWidth: 600
    },
    'width <= 1600': {
      maxWidth: 800
    },
    'width > 1600': {
      maxWidth: 1000
    }
  }
});
