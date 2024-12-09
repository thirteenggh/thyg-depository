import React, {useState, useEffect} from 'react';
import {assign} from 'xstate';
import Axios from 'axios';
import {ExtJS, Utils} from 'nexus-ui-plugin';

import UIStrings from '../../../../constants/UIStrings';

const {ERROR, ANONYMOUS_SETTINGS} = UIStrings;


export default Utils.buildFormMachine({
  id: 'AnonymousSettingsForm'
}).withConfig({
  actions: {
    setData: assign({
      data: (_, {data: [realms, settings]}) => settings.data,
      pristineData: (_, {data: [realms, settings]}) => settings.data,
      realms: (_, {data: [realms]}) => realms.data
    }),

    validate: assign({
      validationErrors: ({data}) => ({
        userId: Utils.isBlank(data?.userId) ? ERROR.FIELD_REQUIRED : null
      })
    }),

    logLoadError: (_, {error}) => {
      if (error) {
        console.error(error);
      }
      ExtJS.showErrorMessage(ANONYMOUS_SETTINGS.MESSAGES.LOAD_ERROR);
    },

    logSaveError: (_, {error}) => {
      if (error) {
        console.error(error);
      }
      ExtJS.showErrorMessage(ANONYMOUS_SETTINGS.MESSAGES.SAVE_ERROR);
    },
    logSaveSuccess: () => ExtJS.showSuccessMessage(ANONYMOUS_SETTINGS.MESSAGES.SAVE_SUCCESS)
  },
  services: {
    fetchData: () => Axios.all([
      Axios.get('/service/rest/internal/ui/realms/types'),
      Axios.get('/service/rest/internal/ui/anonymous-settings')
    ]),
    saveData: ({data}) => Axios.put('/service/rest/internal/ui/anonymous-settings', data)
  }
});
