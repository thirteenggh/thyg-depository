import {assign} from 'xstate';
import Axios from 'axios';
import {Utils} from 'nexus-ui-plugin';

export const ASC = 'asc';
export const DESC = 'desc';

export default Utils.buildListMachine({
  id: 'CleanupPoliciesListMachine',
  config: (config) => ({
    ...config,
    states: {
      ...config.states,
      loaded: {
        ...config.states.loaded,
        on: {
          ...config.states.loaded.on,
          SORT_BY_NAME: {
            target: 'loaded',
            actions: ['setSortByName']
          },
          SORT_BY_FORMAT: {
            target: 'loaded',
            actions: ['setSortByFormat']
          },
          SORT_BY_NOTES: {
            target: 'loaded',
            actions: ['setSortByNotes']
          }
        }
      }
    }
  }),
  options: (options) => ({
    ...options,
    actions: {
      ...options.actions,
      setSortByName: assign({
        sortField: 'name',
        sortDirection: Utils.nextSortDirection('name')
      }),
      setSortByFormat: assign({
        sortField: 'format',
        sortDirection: Utils.nextSortDirection('format')
      }),
      setSortByNotes: assign({
        sortField: 'notes',
        sortDirection: Utils.nextSortDirection('notes')
      }),
      filterData: assign({
        data: ({filter, data, pristineData}, _) => pristineData.filter(
            ({name, format, notes}) => name.toLowerCase().indexOf(filter.toLowerCase()) !== -1 ||
                format.toLowerCase().indexOf(filter.toLowerCase()) !== -1 ||
                notes.toLowerCase().indexOf(filter.toLowerCase()) !== -1)
      })
    },
    services: {
      ...options.services,
      fetchData: () => Axios.get('/service/rest/internal/cleanup-policies'),
    }
  })
});
