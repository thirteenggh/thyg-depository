import ContentSelectors from './components/pages/admin/ContentSelectors/ContentSelectors';
import AnonymousSettings from './components/pages/admin/AnonymousSettings/AnonymousSettings';
import LoggingConfiguration from './components/pages/admin/LoggingConfiguration/LoggingConfiguration';
import LogViewer from "./components/pages/admin/LogViewer/LogViewer";
import RoutingRules from "./components/pages/admin/RoutingRules/RoutingRules";
import SystemInformation from './components/pages/admin/SystemInformation/SystemInformation';
import SupportRequest from './components/pages/admin/SupportRequest/SupportRequest';
import MetricHealth from "./components/pages/admin/MetricHealth/MetricHealth";
import SupportZip from "./components/pages/admin/SupportZip/SupportZip";
import CleanupPolicies from "./components/pages/admin/CleanupPolicies/CleanupPolicies";

import UIStrings from './constants/UIStrings';
import UserAccount from "./components/pages/admin/UserAccount/UserAccount";
import NuGetApiToken from "./components/pages/user/NuGetApiToken/NuGetApiToken";
import AnalyzeApplication from "./components/pages/user/AnalyzeApplication/AnalyzeApplication";

window.ReactComponents = {
  ...window.ReactComponents,
  AnalyzeApplication
};

window.plugins.push({
  id: 'nexus-coreui-plugin',

  features: [
    {
      mode: 'admin',
      path: '/Repository/Selectors',
      ...UIStrings.CONTENT_SELECTORS.MENU,
      view: ContentSelectors,
      iconCls: 'x-fa fa-layer-group',
      visibility: {
        bundle: 'org.sonatype.nexus.plugins.nexus-coreui-plugin',
        permissions: ['nexus:selectors:read']
      }
    },
    {
      mode: 'admin',
      ...UIStrings.ROUTING_RULES.MENU,
      path: '/Repository/RoutingRules',
      view: RoutingRules,
      iconCls: 'x-fa fa-map-signs',
      visibility: {
        bundle: 'org.sonatype.nexus.plugins.nexus-coreui-plugin',
        permissions: ['nexus:*']
      }
    },
    {
      mode: 'admin',
      path: '/Security/Anonymous',
      ...UIStrings.ANONYMOUS_SETTINGS.MENU,
      view: AnonymousSettings,
      iconCls: 'x-fa fa-user',
      visibility: {
        bundle: 'org.sonatype.nexus.plugins.nexus-coreui-plugin',
        permissions: ['nexus:settings:read']
      }
    },
    {
      mode: 'admin',
      path: '/Support/Logging',
      ...UIStrings.LOGGING.MENU,
      view: LoggingConfiguration,
      iconCls: 'x-fa fa-scroll',
      visibility: {
        bundle: 'org.sonatype.nexus.plugins.nexus-coreui-plugin',
        permissions: ['nexus:logging:read']
      }
    },
    {
      mode: 'admin',
      path: '/Support/SupportRequest',
      ...UIStrings.SUPPORT_REQUEST.MENU,
      view: SupportRequest,
      iconCls: 'x-fa fa-user-circle',
      visibility: {
        bundle: 'org.sonatype.nexus.plugins.nexus-coreui-plugin',
        permissions: ['nexus:atlas:create'],
        editions: ['PRO']
      }
    },
    {
      mode: 'admin',
      path: '/Support/SystemInformation',
      ...UIStrings.SYSTEM_INFORMATION.MENU,
      view: SystemInformation,
      iconCls: 'x-fa fa-globe',
      visibility: {
        bundle: 'org.sonatype.nexus.plugins.nexus-coreui-plugin',
        permissions: ['nexus:atlas:read']
      }
    },
    {
      mode: 'user',
      path: '/Account',
      ...UIStrings.USER_ACCOUNT.MENU,
      view: UserAccount,
      iconCls: 'x-fa fa-user',
      visibility: {
        bundle: 'org.sonatype.nexus.plugins.nexus-coreui-plugin',
        requiresUser: true,
      }
    },
    {
      mode: 'user',
      path: '/NuGetApiToken',
      ...UIStrings.NUGET_API_KEY.MENU,
      view: NuGetApiToken,
      iconCls: 'x-fa fa-key',
      visibility: {
        bundle: 'org.sonatype.nexus.plugins.nexus-coreui-plugin',
        requiresUser: true,
      }
    },
    {
      mode: 'admin',
      path: '/Support/Status',
      ...UIStrings.METRIC_HEALTH.MENU,
      view: MetricHealth,
      iconCls: 'x-fa fa-medkit',
      visibility: {
        bundle: 'org.sonatype.nexus.plugins.nexus-coreui-plugin',
        permissions: ['nexus:metrics:read']
      }
    },
    {
      mode: 'admin',
      path: '/Support/SupportZip',
      ...UIStrings.SUPPORT_ZIP.MENU,
      view: SupportZip,
      iconCls: 'x-fa fa-file-archive',
      visibility: {
        bundle: 'org.sonatype.nexus.plugins.nexus-coreui-plugin',
        permissions: ['nexus:metrics:read']
      }
    },
    {
      mode: 'admin',
      path: '/Support/Logging/LogViewer',
      ...UIStrings.LOG_VIEWER.MENU,
      view: LogViewer,
      iconCls: 'x-fa fa-terminal',
      visibility: {
        bundle: 'org.sonatype.nexus.plugins.nexus-coreui-plugin',
        permissions: ['nexus:logging:read']
      }
    },
    {
      mode: 'admin',
      path: '/Repository/CleanupPolicies',
      ...UIStrings.CLEANUP_POLICIES.MENU,
      view: CleanupPolicies,
      iconCls: 'x-fa fa-broom',
      visibility: {
        bundle: 'org.sonatype.nexus.plugins.nexus-coreui-plugin',
        permissions: ['nexus:*']
      }
    }
  ]
});
