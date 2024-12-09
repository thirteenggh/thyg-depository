
import {Machine} from "xstate";
import Axios from "axios";
import {applicationHealthCheckUrl} from "../AnalyzeApplication/AnalyzeApplicationMachine";

export default Machine(
    {
      id: 'HealthCheckEulaMachine',
      initial: 'idle',

      states: {
        idle: {
          on: {
            ACCEPT: {
              target: 'accept'
            },
            DECLINE: {
              actions: ['eulaDeclined']
            }
          }
        },
        accept: {
          invoke: {
            src: 'acceptEula',
            onDone: {
              actions: ['eulaAccepted']
            }
          }
        },
      }
    },
    {
      services: {
        acceptEula: () => Axios.put(applicationHealthCheckUrl)
      }
    });
