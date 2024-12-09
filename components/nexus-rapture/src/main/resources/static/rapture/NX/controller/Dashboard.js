/*global Ext*/

/**
 * Dashboard controller.
 *
 * @since 3.0
 */
Ext.define('NX.controller.Dashboard', {
  extend: 'NX.app.Controller',
  requires: [
    'NX.I18n'
  ],

  views: [
    'dashboard.Welcome'
  ],

  /**
   * @override
   */
  init: function () {
    this.getApplication().getFeaturesController().registerFeature({
      path: '/Welcome',
      mode: 'browse',
      view: 'NX.view.dashboard.Welcome',
      text: NX.I18n.get('Dashboard_Title'),
      description: NX.I18n.get('Dashboard_Description'),
      iconConfig: {
        file: 'sonatype.png',
        variants: ['x16', 'x32']
      },
      weight: 10,
      authenticationRequired: false
    });
  }

});
