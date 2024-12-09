import {assign, Machine} from 'xstate';
import Axios from "axios";

export default Machine(
{
  id: 'SystemInformation',
  initial: 'loading',

  context: {
    systemInformation: {}
  },

  states: {
    loading: {
      invoke: {
        src: 'fetchData',
        onDone: {
          target: 'loaded',
          actions: ['setData', 'logSuccess']
        },
        onError: {
          target: 'loaded',
          actions: ['logError']
        }
      }
    },
    loaded: {},
    error: {}
  },
  on: {
    'RETRY': {
      target: 'loading'
    }
  }
},
{
  actions: {
    setData: assign({
      systemInformation: (_, event) => event.data?.data
    }),
    logDone: (_, event) => console.log(event),
    logError: (_, event) => console.error('Failed to load System Information', event)
  },
  services: {
    fetchData: () => Axios.get('/service/rest/atlas/system-information')
  }
});
