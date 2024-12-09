/*global Ext, NX*/

/**
 * Content (features area) controller.
 *
 * @since 3.0
 */
Ext.define('NX.controller.Content', {
  extend: 'NX.app.Controller',
  requires: [
    'NX.Icons',
    'NX.State'
  ],

  views: [
    'feature.Content'
  ],

  refs: [
    {
      ref: 'featureContent',
      selector: 'nx-feature-content'
    }
  ],

  /**
   * @override
   */
  init: function () {
    var me = this;

    me.listen({
      controller: {
        '#Menu': {
          featureselected: me.onFeatureSelected
        }
      },
      component: {
        'nx-feature-content': {
          resize: function (obj) {
            var drilldown;
            if (obj) {
              drilldown = obj.down('nx-drilldown');
              if (drilldown) {
                drilldown.fireEvent('syncsize');
              }
            }
          }
        }
      }
    });
  },

  /**
   * Update content to selected feature view.
   *
   * @private
   * @param {NX.model.Feature} feature selected feature
   */
  onFeatureSelected: function (feature) {
    var me = this,
        content = me.getFeatureContent(),
        view = feature.get('view'),
        text = feature.get('text'),
        iconName = feature.get('iconName'),
        iconCls = feature.get('iconCls'),
        description = feature.get('description'),
        cmp;

    // create new view and replace any current view
    if (Ext.isString(view)) {
      cmp = me.getView(view).create({});
    }
    else {
      cmp = Ext.widget(view);
    }
    me.mon(cmp, 'destroy', function () {
      //<if debug>
      me.logTrace('Destroyed:', cmp.self.getName());
      //</if>
    });

    // remove the current contents
    content.removeAll();

    // update title and icon
    content.setTitle(text);
    if (iconCls) {
      content.setIconCls(iconCls + " nx-icon");
    } else {
      content.setIconCls(NX.Icons.cls(iconName, 'x32'));
    }

    // Reset unsaved changes flag
    content.resetUnsavedChangesFlag();

    // set browser title
    NX.global.document.title = text + ' - ' + NX.State.getValue('uiSettings').title;

    // update description
    if (description === undefined) {
      description = '';
    }
    content.setDescription(description);

    // Update the breadcrumb
    content.showRoot();

    // install new feature view
    content.add(cmp);

    // fire activate event to view component
    cmp.fireEvent('activate', cmp);

    //<if debug>
    me.logInfo('Content changed to:', text, 'class:', cmp.self.getName());
    //</if>
  }

});
