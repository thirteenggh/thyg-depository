import {assign} from 'xstate';
import Axios from 'axios';
import {Utils} from 'nexus-ui-plugin';

export const ASC = 1;
export const DESC = -1;

export default Utils.buildListMachine({
  id: 'CleanupPoliciesPreview',
  initial: 'loadingRepositories',
  config: (config) => ({
    ...config,
    context: {
      ...config.context,
      repositories: [],
      repository: null,
      criteriaLastBlobUpdated: null,
      criteriaLastDownloaded: null,
      criteriaReleaseType: null,
      criteriaAssetRegex: null
    },
    states: {
      ...config.states,
      loadingRepositories: {
        invoke: {
          id: 'fetchRepositories',
          src: 'fetchRepositories',
          onDone: {
            target: 'loaded',
            actions: ['setRepositories']
          },
          onError: {
            target: '#error',
            actions: ['setError']
          }
        }
      },
      loaded: {
        ...config.states.loaded,
        on: {
          ...config.states.loaded.on,
          SET_REPOSITORY: {
            target: 'loaded',
            actions: ['setRepository']
          },
          PREVIEW: {
            target: 'loading',
            actions: ['clearFilter']
          },
          SORT_BY_NAME: {
            target: 'loaded',
            actions: ['setSortByName']
          },
          SORT_BY_GROUP: {
            target: 'loaded',
            actions: ['setSortByGroup']
          },
          SORT_BY_VERSION: {
            target: 'loaded',
            actions: ['setSortByVersion']
          }
        }
      }
    },
    on: {
      'RETRY': {
        target: 'loading'
      }
    }
  }),
  options: (options) => ({
    ...options,
    actions: {
      ...options.actions,
      setRepositories: assign({
        repositories: (_, event) => event.data.data
      }),
      setRepository: assign({
        repository: (_, {repository}) => repository
      }),
      setSortByName: assign({
        sortField: 'name',
        sortDirection: Utils.nextSortDirection('name')
      }),
      setSortByGroup: assign({
        sortField: 'group',
        sortDirection: Utils.nextSortDirection('group')
      }),
      setSortByVersion: assign({
        sortField: 'version',
        sortDirection: Utils.nextSortDirection('version')
      }),
      setData: assign({
        data: (_, {data}) => data.data.results,
        pristineData: (_, {data}) => data.data.results
      }),
      filterData: assign({
        data: ({filter, data, pristineData}, _) => {
          console.log('filter: ' + filter);
          console.log('data: ' + data);
          console.log('pristineData: ' + pristineData);
          return pristineData.filter(({name, group, version}) =>
              (name && name.toLowerCase().indexOf(filter.toLowerCase()) !== -1) ||
              (group && group.toLowerCase().indexOf(filter.toLowerCase()) !== -1) ||
              (version && version.toLowerCase().indexOf(filter.toLowerCase()) !== -1)
          );
        }
      })
    },
    services: {
      ...options.services,
      fetchRepositories: () => Axios.get('/service/rest/internal/ui/repositories'),
      fetchData: ({repository, criteriaLastBlobUpdated, criteriaLastDownloaded, criteriaReleaseType, criteriaAssetRegex}) => Axios.post(
          '/service/rest/internal/cleanup-policies/preview/components', {
            repository: repository,
            criteriaLastBlobUpdated: criteriaLastBlobUpdated,
            criteriaLastDownloaded: criteriaLastDownloaded,
            criteriaReleaseType: criteriaReleaseType,
            criteriaAssetRegex: criteriaAssetRegex
          })
    }
  })
});
