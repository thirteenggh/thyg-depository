import React from 'react';

import {Detail, Master, MasterDetail} from 'nexus-ui-plugin';

import LoggingConfigurationList from './LoggingConfigurationList';
import LoggingConfigurationForm from './LoggingConfigurationForm';

export default function LoggingConfiguration() {
  return <MasterDetail path="admin/support/logging">
    <Master>
      <LoggingConfigurationList/>
    </Master>
    <Detail>
      <LoggingConfigurationForm/>
    </Detail>
  </MasterDetail>;
}
