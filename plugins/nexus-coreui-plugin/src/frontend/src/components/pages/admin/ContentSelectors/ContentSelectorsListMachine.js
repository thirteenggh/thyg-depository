import {assign, Machine} from 'xstate';
import Axios from 'axios';
import {Utils} from 'nexus-ui-plugin';

export const ASC = 'asc';
export const DESC = 'desc';

export default Machine(
    {
      initial: 'loading',

      context: {
        data: [],
        pristineData: [],
        sortField: 'name',
        sortDirection: ASC,
        filter: '',
        error: ''
      },

      states: {
        loading: {
          id: 'loading',
          initial: 'fetch',
          states: {
            'fetch': {
              invoke: {
                src: 'fetch',
                onDone: {
                  target: '#loaded',
                  actions: ['setData']
                },
                onError: {
                  target: '#error',
                  actions: ['setError']
                }
              }
            },
            'resetting': {
              invoke: {
                src: 'reset',
                onDone: 'fetch',
                onError: {
                  target: 'fetch',
                  actions: ['showResetError']
                }
              }
            }
          }
        },
        loaded: {
          id: 'loaded',
          initial: 'editing',
          entry: ['filterData', 'sortData'],
          states: {
            editing: {
              on: {
                RESET: 'confirmReset'
              }
            },
            confirmReset: {
              invoke: {
                src: 'confirmReset',
                onDone: '#loading.resetting',
                onError: 'editing'
              }
            }
          },
          on: {
            SORT_BY_NAME: {
              target: 'loaded',
              actions: ['setSortByName']
            },
            SORT_BY_TYPE: {
              target: 'loaded',
              actions: ['setSortByType']
            },
            SORT_BY_DESCRIPTION: {
              target: 'loaded',
              actions: ['setSortByDescription']
            },
            FILTER: {
              target: 'loaded',
              actions: ['setFilter']
            }
          }
        },
        error: {
          id: 'error'
        }
      }
    },
    {
      actions: {
        setData: assign({
          data: ({sortField, sortDirection}, {data}) => data.data,
          pristineData: ({sortField, sortDirection}, {data}) => data.data
        }),

        setError: assign({
          error: (_, event) => event.data.message
        }),

        setSortByName: assign({
          sortField: 'name',
          sortDirection: Utils.nextSortDirection('name')
        }),
        setSortByType: assign({
          sortField: 'type',
          sortDirection: Utils.nextSortDirection('type')
        }),
        setSortByDescription: assign({
          sortField: 'description',
          sortDirection: Utils.nextSortDirection('description')
        }),

        setFilter: assign({
          filter: (_, {filter}) => filter
        }),

        filterData: assign({
          data: ({filter, data, pristineData}, _) => pristineData.filter(
              ({name, description}) => name.toLowerCase().indexOf(filter.toLowerCase()) !== -1 ||
                  description.toLowerCase().indexOf(filter.toLowerCase()) !== -1)
        }),

        sortData: assign({
          data: Utils.sortDataByFieldAndDirection
        }),
      },
      services: {
        fetch: () => Axios.get('/service/rest/v1/security/content-selectors'),
      }
    }
);
