import {assign} from 'xstate';
import Axios from 'axios';

import {ExtJS, Utils} from 'nexus-ui-plugin';

import UIStrings from '../../../../constants/UIStrings';

const url = (name) => `/service/rest/internal/ui/routing-rules/${name}`;

export default Utils.buildFormMachine({
  id: 'RoutingRulesFormMachine',

  config: (config) => ({
    ...config,

    context: {
      ...config.context,
      path: '',
      testResult: null
    },

    states: {
      ...config.states,
      loaded: {
        ...config.states.loaded,
        on: {
          ...config.states.loaded.on,
          SAVE: [
            {
              target: 'saving'
            }
          ],
          CANCEL: {
            actions: ['onCancel']
          },
          ADD_MATCHER: {
            target: 'loaded',
            actions: ['addMatcher', 'clearTestResult'],
            internal: false
          },
          REMOVE_MATCHER: {
            target: 'loaded',
            actions: ['removeMatcher', 'clearTestResult'],
            internal: false
          },
          TEST: {
            target: 'testing'
          },
          UPDATE_MATCHER: {
            target: 'loaded',
            actions: ['updateMatcher', 'clearTestResult'],
            internal: false
          },
          UPDATE_PATH: {
            target: 'loaded',
            actions: ['updatePath', 'clearTestResult']
          },
          DELETE: {
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
      },
      testing: {
        invoke: {
          src: 'test',
          onDone: {
            target: 'loaded',
            actions: ['onTestSuccess']
          },
          onError: {
            target: 'loaded',
            actions: ['onTestError']
          }
        }
      }
    }
  })
}).withConfig({
  actions: {
    addMatcher: assign({
      data: ({data}) => ({...data, matchers: data.matchers.concat([''])}),
      isTouched: ({isTouched}) => ({
        ...isTouched,
        matchers: (isTouched.matchers || []).concat([false])
      })
    }),
    clearTestResult: assign({
      testResult: () => null
    }),
    removeMatcher: assign({
      data: ({data}, {index}) => ({...data, matchers: data.matchers.filter((_, i) => i !== index)}),
      isTouched: ({isTouched}, {index}) => ({
        ...isTouched,
        matchers: isTouched.matchers.filter((_, touchedIndex) => touchedIndex !== index )
      })
    }),
    updateMatcher: assign({
      data: ({data}, {index, value}) => ({
        ...data,
        matchers: data.matchers.map((matcher, i) => i === index ? value : matcher)
      }),
      isTouched: ({isTouched, data}, {index}) => ({
        ...isTouched,
        matchers: (isTouched.matchers || [false]).map((value, touchedIndex) =>
            touchedIndex === index ? true : (value || false))
      })
    }),
    updatePath: assign({
      path: (_, {path}) => path
    }),
    validate: assign(({data}) => {
      const validationErrors = {};
      if (Utils.isBlank(data.name)) {
        validationErrors.name = UIStrings.ERROR.FIELD_REQUIRED;
      }

      if (data.name === "None") {
        validationErrors.name = UIStrings.ROUTING_RULES.FORM.NAME_IS_NONE_ERROR
      }

      data.matchers.forEach((matcher, index) => {
        if (Utils.isBlank(matcher)) {
          validationErrors[`matcher[${index}]`] = UIStrings.ERROR.FIELD_REQUIRED
        }
      });
      return {validationErrors};
    }),

    onDeleteError: ({pristineData}) => ExtJS.showErrorMessage(
        UIStrings.ROUTING_RULES.MESSAGES.DELETE_ERROR(pristineData.name)
    ),
    onTestSuccess: assign({
      testResult: (_, event) => event.data.data
    }),
    onTestError: assign({
      testError: (_, event) => String(event.data?.data)
    })
  },
  services: {
    fetchData: ({pristineData}) => {
      if (Utils.notBlank(pristineData.name)) { // Edit
        return Axios.get(url(pristineData.name));
      }
      else { // New
        return Promise.resolve({
          data: {
            name: '',
            description: '',
            mode: 'BLOCK',
            matchers: ['']
          }
        });
      }
    },
    saveData: ({data, pristineData}) => {
      if (Utils.notBlank(pristineData.name)) {
        return Axios.put(url(pristineData.name), data);
      }
      return Axios.post(url(''), data);
    },
    confirmDelete: ({pristineData}) => ExtJS.requestConfirmation({
      title: UIStrings.ROUTING_RULES.MESSAGES.CONFIRM_DELETE.TITLE,
      message: UIStrings.ROUTING_RULES.MESSAGES.CONFIRM_DELETE.MESSAGE(pristineData.name),
      yesButtonText: UIStrings.ROUTING_RULES.MESSAGES.CONFIRM_DELETE.YES,
      noButtonText: UIStrings.ROUTING_RULES.MESSAGES.CONFIRM_DELETE.NO
    }),
    delete: ({pristineData}) => Axios.delete(url(pristineData.name)),
    test: ({data, path}) => Axios.post(`/service/rest/internal/ui/routing-rules/test`, {
      mode: data.mode,
      matchers: data.matchers,
      path: '/' + path
    })
  }
});
