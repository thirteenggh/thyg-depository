
/**
 * @param feature - {
 *   mode: 'browse' || 'admin',
 *   path: '/somepath',
 *   text: 'menu label',
 *   description: 'description used for the header when visiting the feature',
 *   view: <reactViewReference>,
 *   iconCls: 'x-fa fa-icon-type',
 *   visibility: {
 *     bundle: 'an optional bundle expected to be available for the feature to be visible',
 *     featureFlags: [{ // optional
 *       key: 'featureFlagName',
 *       defaultValue: true // the value the feature flag is set to by default (optional)
 *     }],
 *     licenseValid: [{ // optional
 *       key: 'stateWithLicenseFlagName',
 *       defaultValue: false // the value the license validity is set to by default (optional)
 *     }],
 *     statesEnabled: [{ // optional
 *       key: 'stateWithEnabledFlagName',
 *       defaultValue: false // the value the state enablement is set to by default (optional)
 *     }],
 *     permissions: ['optional array of permission strings', 'nexus:settings:read']
 *   }
 * }
 */
export default function registerFeature(feature) {
  console.log(`Register feature`, feature);
  const reactViewController = Ext.getApplication().getController('NX.coreui.controller.react.ReactViewController');
  Ext.getApplication().getFeaturesController().registerFeature({
    mode: feature.mode,
    path: feature.path,
    text: feature.text,
    description: feature.description,
    view: {
      xtype: 'nx-coreui-react-main-container',
      itemId: 'react-view',
      reactView: feature.view
    },
    iconCls: feature.iconCls,
    visible: function () {
      var isVisible = true;
      const visibility = feature.visibility;

      if (!visibility) {
        console.warn('feature is active due to no visibility configuration defined', feature);
        return isVisible;
      }

      if (visibility.bundle) {
        isVisible = NX.app.Application.bundleActive(visibility.bundle)
        console.debug("bundleActive="+isVisible, visibility.bundle);
      }

      if (isVisible && visibility.licenseValid) {
        isVisible = visibility.licenseValid.every(licenseValid => NX.State.getValue(licenseValid.key, licenseValid.defaultValue)['licenseValid']);
        console.debug("licenseValid="+isVisible, visibility.licenseValid);
      }

      if (isVisible && visibility.featureFlags) {
        isVisible = visibility.featureFlags.every(featureFlag => NX.State.getValue(featureFlag.key, featureFlag.defaultValue));
        console.debug("featureFlagsActive="+isVisible, visibility.featureFlags);
      }

      if (isVisible && visibility.statesEnabled) {
        isVisible = visibility.statesEnabled.every(state => NX.State.getValue(state.key, state.defaultValue)['enabled']);
        console.debug("statesEnabled="+isVisible, visibility.statesEnabled);
      }

      if (isVisible && visibility.permissions) {
        isVisible = visibility.permissions.every((permission) => NX.Permissions.check(permission));
        console.debug("permissionCheck="+isVisible, visibility.permissions);
      }

      if (isVisible && visibility.editions) {
        isVisible = visibility.editions.every((edition) =>  NX.State.getEdition() === edition);
        console.debug("editionCheck="+isVisible, visibility.editions);
      }

      if (isVisible && visibility.requiresUser) {
        isVisible = NX.Security.hasUser();
      }

      return isVisible;
    }
  }, reactViewController);
}
