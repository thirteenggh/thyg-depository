import {assign, Machine} from 'xstate';
import {ExtJS, Utils} from 'nexus-ui-plugin';
import Axios from "axios";

import UIStrings from '../../../../constants/UIStrings';

const EMPTY_DATA = {
  passwordCurrent: '',
  passwordNew: '',
  passwordNewConfirm: ''
};

export default Utils.buildFormMachine({
  id: 'passwordChange',
  initial: 'loaded',
  config: (config) => ({
    ...config,
    context: {
      ...config.context,
      data: EMPTY_DATA,
      pristineData: EMPTY_DATA
    }
  })
}).withConfig({
  actions: {
    validate: assign({
      validationErrors: ({data: {passwordCurrent, passwordNew, passwordNewConfirm}}) => {
        let passwordNewError = [];
        if (Utils.isBlank(passwordNew)) {
          passwordNewError.push(UIStrings.ERROR.FIELD_REQUIRED);
        }
        if (passwordCurrent === passwordNew) {
          passwordNewError.push(UIStrings.USER_ACCOUNT.MESSAGES.PASSWORD_MUST_DIFFER_ERROR);
        }

        let passwordNewConfirmError = [];
        if (Utils.isBlank(passwordNewConfirm)) {
          passwordNewConfirmError.push(UIStrings.ERROR.FIELD_REQUIRED);
        }
        if (passwordNew !== passwordNewConfirm) {
          passwordNewConfirmError.push(UIStrings.USER_ACCOUNT.MESSAGES.PASSWORD_NO_MATCH_ERROR);
        }

        return {
          passwordCurrent: Utils.isBlank(passwordCurrent) ? UIStrings.ERROR.FIELD_REQUIRED : null,
          passwordNew: passwordNewError,
          passwordNewConfirm: passwordNewConfirmError
        };
      }
    }),
    logSaveSuccess: () => ExtJS.showSuccessMessage('Password changed'),
    logSaveError: (error) => {
      if (Array.isArray(error.saveError)) {
        const message = error.saveError.filter(e => e.id !== "authToken").map(e => e.message).join('\\n');
        if (message) {
          ExtJS.showErrorMessage(message);
        }
      }
      else {
        ExtJS.showErrorMessage('Change password failed');
        console.error(error);
      }
    },
    onSaveSuccess: assign({
      data: () => EMPTY_DATA,
      pristineData: () => EMPTY_DATA
    })
  },
  services: {
    saveData: ({data: {passwordCurrent, passwordNew}}, {userId}) =>
        ExtJS.fetchAuthenticationToken(userId, passwordCurrent).then((result) =>
            Axios.put(`/service/rest/internal/ui/user/${userId}/password`, {
              authToken: result.data,
              password: passwordNew
            }))
  }
});
