import {assign} from 'xstate';
import Axios from 'axios';
import UIStrings from '../../../../constants/UIStrings';
import {ExtJS, Utils} from "nexus-ui-plugin";
import {applicationHealthCheckUrl} from "./AnalyzeApplicationMachine";

export default Utils.buildFormMachine({
  id: 'analyzeApplicationModalMachine',
  config: (config) => ({
    ...config,
    states: {
      ...config.states,
      loaded: {
        ...config.states.loaded,
        on: {
          ...config.states.loaded.on,
          ASSET: {
            target: 'loaded',
            actions: ['selectAsset'],
            internal: false
          },
          ANALYZE: {
            target: 'analyzing'
          },
          CANCEL: {
            actions: ['onClose']
          }
        }
      },
      analyzing: {
        on: {
          CANCEL: {
            actions: ['onClose']
          }
        },
        invoke: {
          src: 'analyze',
          onDone: {
            actions: ['analyzeSuccess'],
            target: 'analyzed'
          },
          onError: {
            actions: ['analyzeError'],
            target: 'loaded'
          }
        }
      },
      analyzed: {
        'entry': 'onAnalyzed'
      }
    }
  })
}).withConfig({
  actions: {
    validate: assign({
      validationErrors: ({data: {emailAddress, password, reportLabel}}) => {
        return {
          emailAddress: Utils.isBlank(emailAddress) ? UIStrings.ERROR.FIELD_REQUIRED : null,
          password: Utils.isBlank(password) || password === undefined ? UIStrings.ERROR.FIELD_REQUIRED : null,
          reportLabel: Utils.isBlank(reportLabel) ? UIStrings.ERROR.FIELD_REQUIRED : null
        }
      }
    }),
    analyzeError: () => ExtJS.showErrorMessage("Error analyzing the application"),
    analyzeSuccess: () => ExtJS.showSuccessMessage('Analysis in process. Email will be sent when report is ready.'),
    selectAsset: assign({
      data: ({data}, event) => ({...data, ...event.data})
    })
  },
  services: {
    fetchData: ({componentModel}) => Axios.get(applicationHealthCheckUrl, {params: {component: componentModel}}),
    analyze: (context) => {
      let message = {
        repositoryName: context.componentModel.repositoryName,
        assetId: context.data.selectedAsset,
        emailAddress: context.data.emailAddress,
        reportPassword: context.data.password,
        proprietaryPackages: context.data.packages,
        reportLabel: context.data.reportLabel
      }
      return Axios.post(applicationHealthCheckUrl, message);
    }
  }
});
