import {assign} from 'xstate';
import Axios from 'axios';

import {ExtJS, Utils} from 'nexus-ui-plugin';

import UIStrings from '../../../../constants/UIStrings';

const baseUrl = '/service/rest/v1/security/content-selectors';
const url = (name) => `${baseUrl}/${name}`;

function isEdit({name}) {
  return Utils.notBlank(name);
}

export default Utils.buildFormMachine({
  id: 'ContentSelectorsFormMachine',
  config: (config) => ({
    ...config,
    states: {
      ...config.states,
      loaded: {
        ...config.states.loaded,
        on: {
          ...config.states.loaded.on,
          'CONFIRM_DELETE': {
            target: 'confirmDelete'
          }
        }
      },
      confirmDelete: {
        invoke: {
          src: 'confirmDelete',
          onDone: 'delete',
          onError: 'loaded'
        }
      },
      delete: {
        invoke: {
          src: 'delete',
          onDone: {
            target: 'loaded',
            actions: 'onDeleteSuccess'
          },
          onError: {
            target: 'loaded',
            actions: 'onDeleteError'
          }
        }
      }
    }
  })
}).withConfig({
  actions: {
    validate: assign({
      validationErrors: ({data}) => ({
        name: Utils.isBlank(data.name) ? UIStrings.ERROR.FIELD_REQUIRED : null,
        expression: Utils.isBlank(data.expression) ? UIStrings.ERROR.FIELD_REQUIRED : null
      })
    }),

    onDeleteError: ({data}) => ExtJS.showErrorMessage(UIStrings.CONTENT_SELECTORS.MESSAGES.DELETE_ERROR(data.name)),

    setSaveError: assign({
      saveErrorData: ({data}) => data,
      saveErrors: ({data}, event) => {
        const error = event.data?.response?.data;

        if (typeof error === 'string' && error.indexOf('ORecordDuplicatedException')) {
          return {
            name: UIStrings.CONTENT_SELECTORS.MESSAGES.DUPLICATE_ERROR(data.name)
          };
        }

        if (error instanceof Array) {
          return {
            expression: error[0].message
          };
        }

        return {};
      }
    })
  },
  guards: {
    isEdit: ({pristineData}) => isEdit(pristineData)
  },
  services: {
    fetchData: ({pristineData}) => {
      if (isEdit(pristineData)) {
        return Axios.get(url(pristineData.name));
      }
      else { // New
        return Promise.resolve({
          data: {
            name: '',
            type: 'CSEL',
            description: '',
            expression: ''
          }
        });
      }
    },
    saveData: ({data, pristineData}) => {
      if (isEdit(pristineData)) {
        return Axios.put(url(data.name), {
          description: data.description,
          expression: data.expression
        });
      }
      else { // New
        return Axios.post(baseUrl, {
          name: data.name,
          description: data.description,
          expression: data.expression
        });
      }
    },

    confirmDelete: ({data}) => ExtJS.requestConfirmation({
      title: UIStrings.CONTENT_SELECTORS.MESSAGES.CONFIRM_DELETE.TITLE,
      message: UIStrings.CONTENT_SELECTORS.MESSAGES.CONFIRM_DELETE.MESSAGE(data.name),
      yesButtonText: UIStrings.CONTENT_SELECTORS.MESSAGES.CONFIRM_DELETE.YES,
      noButtonText: UIStrings.CONTENT_SELECTORS.MESSAGES.CONFIRM_DELETE.NO
    }),

    delete: ({data}) => Axios.delete(url(data.name))
  }
});
