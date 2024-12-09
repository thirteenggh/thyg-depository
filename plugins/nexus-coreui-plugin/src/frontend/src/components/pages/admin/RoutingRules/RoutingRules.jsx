import React from 'react';

import {Detail, Master, MasterDetail} from 'nexus-ui-plugin';

import RoutingRulesList from './RoutingRulesList';
import RoutingRulesForm from './RoutingRulesForm';
import './RoutingRules.scss';

export default function RoutingRules() {
  return <MasterDetail path="admin/repository/routingrules">
    <Master>
      <RoutingRulesList/>
    </Master>
    <Detail>
      <RoutingRulesForm/>
    </Detail>
  </MasterDetail>;
}
