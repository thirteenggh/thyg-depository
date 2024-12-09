import Axios from 'axios';

import {ExtJS, TokenMachine} from 'nexus-ui-plugin';

import UIStrings from '../../../../constants/UIStrings';

export default TokenMachine.withConfig({
      services: {
        resetToken: () => ExtJS.requestAuthenticationToken(UIStrings.NUGET_API_KEY.AUTH_INSTRUCTIONS)
            .then(authToken =>
                Axios.delete(`/service/rest/internal/nuget-api-key?authToken=${btoa(authToken)}`)),
        accessToken: () => ExtJS.requestAuthenticationToken(UIStrings.NUGET_API_KEY.AUTH_INSTRUCTIONS)
            .then(authToken =>
                Axios.get(`/service/rest/internal/nuget-api-key?authToken=${btoa(authToken)}`))
      }
    }
);
