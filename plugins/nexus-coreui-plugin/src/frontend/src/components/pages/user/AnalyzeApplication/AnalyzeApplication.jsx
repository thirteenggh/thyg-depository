import React from 'react';
import PropTypes from "prop-types";

import './AnalyzeApplicationModal.scss';
import {useMachine} from "@xstate/react";
import AnalyzeApplicationMachine from "./AnalyzeApplicationMachine";
import HealthCheckEula from "../HealthCheck/HealthCheckEula";
import AnalyzeApplicationModal from "./AnalyzeApplicationModal";
import {NxLoadWrapper} from 'nexus-ui-plugin';

export default function AnalyzeApplication(props) {
  const [state, send] = useMachine(AnalyzeApplicationMachine, {
    context: {
      componentModel: props.componentModel
    },
    actions: {
      onClose: () => props.onClose()
    },
    devTools: true
  });

  const isLoading = state.matches('loading');
  const showEula = state.matches('showEula');
  const showAnalyze = !state.matches('loading') && !showEula;

  function handleAnalyzed() {
    send('ANALYZED');
  }

  function handleCancel() {
    send('CANCEL');
  }

  function handleAccept() {
    send('EULA_ACCEPTED');
  }

  function handleDecline() {
    send('EULA_DECLINED');
  }

  function retry() {
    send('RETRY');
  }

  // This is needed to allow ExtJs to rerender the window 'after' React has rendered its components onto the DOM
  setTimeout(() => { props.rerender()}, 100);

  return <NxLoadWrapper loading={isLoading} retryHandler={retry}>
      {showEula && <HealthCheckEula onDecline={handleDecline} onAccept={handleAccept}/>}
      {showAnalyze && <AnalyzeApplicationModal componentModel={state.context.componentModel} onAnalyzed={handleAnalyzed} onCancel={handleCancel}/>}
    </NxLoadWrapper>

}

AnalyzeApplication.propTypes = {
  componentModel: PropTypes.object,
  rerender: PropTypes.func,
  onClose: PropTypes.func,
}
