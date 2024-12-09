import {assign, Machine} from 'xstate';
import ExtJS from '../../interface/ExtJS';
import UIStrings from '../../constants/UIStrings';

export default Machine(
    {
      initial: 'idle',
      context: {
        userToken: null,
      },
      states: {
        idle: {
          on: {
            ACCESS: 'accessToken',
            RESET: 'resetToken',
          }
        },
        accessToken: {
          invoke: {
            id: 'accessToken',
            src: 'accessToken',
            onDone: {
              target: 'showToken',
              actions: 'setToken'
            },
            onError: {
              target: 'idle',
              actions: 'showAccessError'
            }
          },
        },
        resetToken: {
          invoke: {
            id: 'resetToken',
            src: 'resetToken',
            onDone: {
              target: 'idle',
              actions: 'showResetSuccess'
            },
            onError: {
              target: 'idle',
              actions: 'showResetError'
            }
          }
        },
        showToken: {
          after: {
            60000: 'idle'
          },
          exit: 'unsetToken',
          on: {
            ACCESS: 'accessToken',
            RESET: 'resetToken',
            HIDE: {
              target: 'idle',
              actions: 'unsetToken'
            }
          }
        }
      },
      on: {
        RETRY: {
          target: 'idle'
        }
      }
    },
    {
      actions: {
        showResetSuccess: () => {
          ExtJS.showSuccessMessage(UIStrings.USER_TOKEN.MESSAGES.RESET_SUCCESS)
        },
        showResetError: () => {
          ExtJS.showErrorMessage(UIStrings.USER_TOKEN.MESSAGES.RESET_ERROR)
        },
        showAccessError: () => {
          ExtJS.showErrorMessage(UIStrings.USER_TOKEN.MESSAGES.ACCESS_ERROR);
        },
        setToken: assign({
          token: (_, event) => event.data.data
        }),
        unsetToken: assign({
          token: null
        })
      }
    }
);
