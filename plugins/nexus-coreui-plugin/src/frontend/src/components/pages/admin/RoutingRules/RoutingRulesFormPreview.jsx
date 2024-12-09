import React from 'react';
import {NxButton, Textfield} from 'nexus-ui-plugin';

import UIStrings from '../../../../constants/UIStrings';

const {ROUTING_RULES} = UIStrings;

export default function RoutingRulesPreview({value, onChange, onTest}) {
  function handleEnter(event) {
    if (event === 'Enter') {
      onTest(event);
    }
  }

  return <div className="nx-form-row">
    <div className="nx-form-group">
      <label htmlFor="path" className="nx-label"><span className="nx-label">{ROUTING_RULES.PATH_LABEL}</span></label>
      <p className="nx-p">{ROUTING_RULES.PATH_DESCRIPTION}</p>
      <div className="nx-form-row">
        <div className="nx-form-group">
          {/* Ensure the button is at the correct height and prepend with a / */}
          <label className="nx-label nxrm-path">
            <Textfield name="path" value={value} onChange={onChange} onKeyPress={handleEnter} validatable={false}/>
          </label>
        </div>
        <div className="nx-btn-bar">
          <NxButton variant="primary" onClick={onTest}>{ROUTING_RULES.TEST_BUTTON}</NxButton>
        </div>
      </div>
    </div>
  </div>;
}
