import {assign, Machine} from 'xstate';
import Axios from 'axios';
import {ExtJS, Utils} from 'nexus-ui-plugin';

import UIStrings from '../../../../constants/UIStrings';

const userAccountMachine = Utils.buildFormMachine({
  id: 'UserAccount'
}).withConfig({
  actions: {
    logLoadError: ({error}) => {
      if (error) {
        console.error(error);
      }
      ExtJS.showErrorMessage(UIStrings.USER_ACCOUNT.MESSAGES.LOAD_ERROR);
    },
    logSaveSuccess: () => {
      ExtJS.showSuccessMessage(UIStrings.USER_ACCOUNT.MESSAGES.UPDATE_SUCCESS);
    },

    validate: assign({
      validationErrors: ({data}) => ({
        firstName: Utils.isBlank(data.firstName) ? UIStrings.ERROR.FIELD_REQUIRED : null,
        lastName: Utils.isBlank(data.lastName) ? UIStrings.ERROR.FIELD_REQUIRED : null,
        email: Utils.isBlank(data.email) ? UIStrings.ERROR.FIELD_REQUIRED : null
      })
    })
  },
  services: {
    fetchData: () => Axios.get('/service/rest/internal/ui/user'),
    saveData: ({data}) => {
      return Axios.put('/service/rest/internal/ui/user', data);
    }
  }
});

export default userAccountMachine;
