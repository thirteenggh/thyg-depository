/*global Ext, NX*/

/**
 * Outreach controller.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.controller.Outreach', {
  extend: 'NX.app.Controller',
  requires: [
    'NX.util.Url',
    'NX.State',
    'NX.Permissions'
  ],

  refs: [
    { ref: 'welcomePage', selector: 'nx-dashboard-welcome' }
  ],

  /**
   * @override
   */
  init: function () {
    var me = this;

    me.listen({
      controller: {
        '#Refresh': {
          refresh: me.refreshOutreachContent
        },
        '#State': {
          userchanged: me.refreshOutreachContent
        }
      },
      component: {
        'nx-dashboard-welcome': {
          afterrender: me.refreshOutreachContent
        }
      }
    });
  },

  /**
   * @private
   * Add/Remove outreach content to/from welcome page, if outreach content is available.
   */
  refreshOutreachContent: function () {
    var me = this,
        welcomePage = me.getWelcomePage();

    if (welcomePage) {
      NX.direct.outreach_Outreach.readStatus(function (response) {
        if (Ext.isObject(response) && response.success && response.data != null && welcomePage.rendered) {
          var user = NX.State.getUser(),
              usertype,
              url;

          if (user) {
            usertype = user.administrator ? 'admin' : 'normal';
          }
          else {
            usertype = 'anonymous';
          }

          url = NX.util.Url.urlOf('service/outreach/?version=' + NX.State.getVersion() +
              '&versionMm=' + NX.State.getVersionMajorMinor() +
              '&edition=' + NX.State.getEdition() +
              '&usertype=' + usertype);

          // add the outreach iframe to the welcome view
          welcomePage.removeAll();
          welcomePage.add({
            xtype: 'uxiframe',
            itemId: 'outreach',
            anchor: '100%',
            width: '100%',
            flex: 1,
            border: false,
            frame: false,
            hidden: true,
            src: url,
            // override renderTpl to add title attribute for accessibility purpose
            renderTpl: [
              '<iframe src="{src}" id="{id}-iframeEl" data-ref="iframeEl" name="{frameName}" title="可信软件仓库 Outreach" width="100%" height="100%" frameborder="0"></iframe>'
            ],
            listeners: {
              load: function () {
                var iframe = this;
                // if the outreach content has loaded properly, show it
                if (iframe.getWin().iframeLoaded) {
                  iframe.show();
                }
                else {
                  // else complain and leave it hidden
                  //<if debug>
                  me.logDebug('Outreach iframe did not load: ' + url);
                  //</if>
                }
              }
            }
          });
        }
      });
    }
  }

});
