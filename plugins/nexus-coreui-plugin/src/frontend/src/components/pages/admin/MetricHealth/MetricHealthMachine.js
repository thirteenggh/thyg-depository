import {assign, Machine} from 'xstate';
import Axios from 'axios'
import {Utils} from 'nexus-ui-plugin';

export default Machine(
    {
      initial: 'loading',

      context: {
        data: [],
        pristineData: [],
        sortField: 'name',
        sortDirection: Utils.ASC,
        error: ''
      },

      states: {
        loading: {
          invoke: {
            src: 'fetchMetricHealth',
            onDone: {
              target: 'loaded',
              actions: ['setData']
            },
            onError: {
              target: 'error',
              actions: ['setError']
            }
          }
        },
        loaded: {
          entry: ['sortData'],
          on: {
            SORT_BY_NAME: {
              target: 'loaded',
              actions: ['setSortByName']
            },
            SORT_BY_MESSAGE: {
              target: 'loaded',
              actions: ['setSortByMessage']
            },
            SORT_BY_ERROR: {
              target: 'loaded',
              actions: ['setSortByError']
            }
          }
        },
        error: {}
      }
    },
    {
      actions: {
        setData: assign({
          data: (_, event) => Object.entries(event.data.data).map(metric => Object.assign({name: metric[0]}, metric[1])),
          pristineData: (_, event) => Object.entries(event.data.data).map(metric => Object.assign({name: metric[0]}, metric[1]))
        }),
        setError: assign({
          error: (_, event) => event.data.message
        }),
        setSortByName: assign({
          sortField: 'name',
          sortDirection: Utils.nextSortDirection('name')
        }),
        setSortByMessage: assign({
          sortField: 'message',
          sortDirection: Utils.nextSortDirection('message')
        }),
        setSortByError: assign({
          sortField: 'error',
          sortDirection: Utils.nextSortDirection('error')
        }),
        sortData: assign({
          data: Utils.sortDataByFieldAndDirection
        }),
      },
      services: {
        fetchMetricHealth: () => Axios.get('/service/rest/internal/ui/status-check')
      }
    }
);
