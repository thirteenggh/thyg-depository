import React from 'react';
import PropTypes from "prop-types";
import {faHandsHelping} from "@fortawesome/free-solid-svg-icons";
import {ExtJS, NxButton, NxFontAwesomeIcon} from 'nexus-ui-plugin';

import UIStrings from '../../../../constants/UIStrings';
import './HealthCheckEula.scss';
import {useMachine} from "@xstate/react";
import HealthCheckEulaMachine from "./HealthCheckEulaMachine";

export default function HealthCheckEula(props) {
  const [state, send] = useMachine(HealthCheckEulaMachine, {
    actions: {
      eulaAccepted: () => props.onAccept(),
      eulaDeclined: () => props.onDecline()
    },
    devTools: true
  });

  const iframe = `<iframe style="width: 100%" src=${ExtJS.urlOf('/static/healthcheck-tos.html')}></iframe>`;

  function handleEulaAccept() {
    send('ACCEPT');
  }

  function handleEulaDecline() {
    send('DECLINE');
  }

  return <div>
    <header className="nx-modal-header">
      <h2 className="nx-h2">
        <NxFontAwesomeIcon icon={faHandsHelping} fixedWidth/>
        <span>{UIStrings.HEALTHCHECK_EULA.HEADER}</span>
      </h2>
    </header>

    <div className="nx-modal-content">
      <p dangerouslySetInnerHTML={{__html: iframe}} />
    </div>

    <footer className="nx-footer">
      <NxButton variant="primary" onClick={handleEulaAccept}>
        {UIStrings.HEALTHCHECK_EULA.BUTTONS.ACCEPT}
      </NxButton>
      <NxButton onClick={handleEulaDecline}>
        {UIStrings.HEALTHCHECK_EULA.BUTTONS.DECLINE}
      </NxButton>
    </footer>
  </div>
}

HealthCheckEula.propTypes = {
  onAccept: PropTypes.func,
  onDecline: PropTypes.func
}
