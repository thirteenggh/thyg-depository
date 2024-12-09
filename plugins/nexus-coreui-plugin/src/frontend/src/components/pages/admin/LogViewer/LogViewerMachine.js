import {assign, cancel, Machine} from 'xstate';
import Axios from 'axios';

import {ExtJS} from 'nexus-ui-plugin';

import UIStrings from "../../../../constants/UIStrings";

export default Machine(
    {
      initial: 'retrieve',

      context: {
        data: '',
        error: '',
        mark: '',
        period: 0,
        size: 25
      },

      states: {
        retrieve: {
          invoke: {
            src: 'retrieve',
            onDone: {
              target: 'retrieved',
              actions: ['setData']
            },
            onError: {
              target: 'error',
              actions: ['setError']
            }
          }
        },
        retrieved: {
          on: {
            '': [
              { target: 'manual', cond: 'isManual' },
              { target: 'waiting' }
            ]
          },
        },
        waiting: {
          on: {
            UPDATE_PERIOD: {
              target: 'retrieve',
              actions: ['setPeriod']
            },
            MANUAL_REFRESH: {
              target: 'manual',
              actions: ['setPeriod']
            },
            UPDATE_SIZE: {
              target: 'retrieve',
              actions: ['setSize']
            },
            UPDATE_MARK: {
              target: 'waiting',
              actions: ['setMark']
            },
            INSERT_MARK: {
              target: 'insertingMark'
            }
          },
          after: {
            REFRESH: 'retrieve'
          }
        },
        manual: {
          on: {
            UPDATE_PERIOD: {
              target: 'retrieve',
              actions: ['setPeriod']
            },
            UPDATE_SIZE: {
              target: 'retrieve',
              actions: ['setSize']
            },
            UPDATE_MARK: {
              target: 'manual',
              actions: ['setMark']
            },
            INSERT_MARK: {
              target: 'insertingMark'
            }
          }
        },
        insertingMark: {
          invoke: {
            src: 'insertMark',
            onDone: {
              target: 'retrieve'
            },
            onError: {
              target: 'error',
              actions: 'setError'
            }
          }
        },
        error: {}
      }
    },
    {
      actions: {
        setData: assign({
          data: (_, {data}) => data.data
        }),
        setError: assign({
          error: (_, event) => event.data.message
        }),
        setPeriod: assign({
          period: (_, {period}) => period
        }),
        setSize: assign({
          size: (_, {size}) => size
        }),
        setMark: assign({
          mark: (_, {mark}) => mark
        })
      },
      delays: {
        REFRESH: ({period}, event) => {
          return period * 1000;
        }
      },
      guards: {
        isManual: ({period}) => period == 0
      },
      services: {
        retrieve: ({size}) => Axios.get('/service/rest/internal/logging/log', {params: {bytesCount: size * -1024}}),
        insertMark: ({mark}) => Axios.post('/service/rest/internal/logging/log/mark', mark, {headers: {'Content-Type': 'text/plain'}})
      }
    }
);
