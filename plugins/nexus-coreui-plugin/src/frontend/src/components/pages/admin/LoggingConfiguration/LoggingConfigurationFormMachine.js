import {assign} from 'xstate';
import Axios from 'axios';

import {ExtJS, Utils} from 'nexus-ui-plugin';

import UIStrings from '../../../../constants/UIStrings';

const loggerUrl = (name) => `/service/rest/internal/ui/loggingConfiguration/${name}`;

export default Utils.buildFormMachine({
  id: 'LoggingConfigurationFormMachine',
  config: (config) => ({
    ...config,
    states: {
      ...config.states,
      loaded: {
        ...config.states.loaded,
        on: {
          ...config.states.loaded.on,
          SAVE: [
            {
              target: 'saving',
              cond: 'isEdit'
            },
            {
              target: 'confirmSave'
            }
          ],
          CANCEL: {
            actions: ['onCancel']
          },

          RESET: {
            target: 'confirmReset',
            cond: 'isEdit'
          }
        }
      },
      confirmReset: {
        invoke: {
          src: 'confirmReset',
          onDone: 'resetting',
          onError: 'loaded'
        }
      },
      confirmSave: {
        invoke: {
          src: 'confirmSave',
          onDone: 'saving',
          onError: 'loaded'
        }
      },
      resetting: {
        invoke: {
          src: 'reset',
          onDone: {
            actions: ['onReset', 'clearDirtyFlag']
          },
          onError: {
            target: 'loaded',
            actions: ['setSaveError']
          }
        }
      }
    }
  })
}).withConfig({
  actions: {
    validate: assign({
      validationErrors: ({data}) => ({
        name: Utils.isBlank(data.name) ? UIStrings.ERROR.FIELD_REQUIRED : null
      })
    })
  },
  guards: {
    isEdit: ({pristineData}) => Utils.notBlank(pristineData.name)
  },
  services: {
    fetchData: ({pristineData}) => {
      if (Utils.notBlank(pristineData.name)) { // Edit logging configuration
        return Axios.get(loggerUrl(pristineData.name));
      }
      else { // New Logging Configuration
        return Promise.resolve({
          data: {
            name: '',
            level: 'INFO'
          }
        });
      }
    },
    confirmReset: () => ExtJS.requestConfirmation({
      title: UIStrings.LOGGING.CONFIRM_RESET.TITLE,
      message: UIStrings.LOGGING.CONFIRM_RESET.MESSAGE,
      yesButtonText: UIStrings.LOGGING.CONFIRM_RESET.CONFIRM_BUTTON,
      noButtonText: UIStrings.SETTINGS.CANCEL_BUTTON_LABEL
    }),
    confirmSave: async ({data}) => {
      const response = await Axios.get(loggerUrl(data.name));
      if (response.data.override) {
        return await ExtJS.requestConfirmation({
          title: UIStrings.LOGGING.CONFIRM_UPDATE.TITLE,
          message: UIStrings.LOGGING.CONFIRM_UPDATE.MESSAGE({name: data.name, level: data.level}),
          yesButtonText: UIStrings.LOGGING.CONFIRM_UPDATE.CONFIRM_BUTTON,
          noButtonText: UIStrings.SETTINGS.CANCEL_BUTTON_LABEL
        });
      }
      return Promise.resolve();
    },
    reset: ({data}) => Axios.post(`${loggerUrl(data.name)}/reset`),
    saveData: ({data}) => Axios.put(loggerUrl(data.name), data)
  }
});
