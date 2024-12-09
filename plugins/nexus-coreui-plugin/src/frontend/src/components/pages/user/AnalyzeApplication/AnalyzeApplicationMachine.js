import Axios from 'axios';
import {assign, Machine} from "xstate";

export const applicationHealthCheckUrl = '/service/rest/internal/ui/ahc';

export default Machine({
  id: 'analyzeApplicationMachine',
  initial: 'loading',

  states: {
    loading: {
      invoke: {
        src: 'fetchData',
        onDone: {
          target: 'loaded',
          actions: ['setAccepted']
        }
      }
    },
    loaded: {
      on: {
        '': [
            {target: 'showEula', cond: 'shouldShowEula' },
            {target: 'showAnalyze'}
        ]
      }
    },
    showEula: {
      on: {
        EULA_ACCEPTED: {
          target: 'loading'
        },
        EULA_DECLINED: {
          actions: ['onClose']
        }
      }
    },
    showAnalyze: {
      on: {
        CANCEL: {
          actions: ['onClose']
        },
        ANALYZED: {
          actions: ['onClose']
        }
      }
    }
  },
  on: {
    RETRY: {
      target: 'loading'
    }
  }
}, {
  guards: {
    shouldShowEula: context => context.pristineData.tosAccepted === false
  },
  actions: {
    setAccepted: assign({
      pristineData: (_, event) => event.data?.data
    })
  },
  services: {
    fetchData: ({componentModel}) => Axios.get(applicationHealthCheckUrl, {params: {component: componentModel}})
  }
});
