import React from 'react';

import {Detail, Master, MasterDetail} from 'nexus-ui-plugin';

import CleanupPoliciesList from './CleanupPoliciesList';
import CleanupPoliciesForm from './CleanupPoliciesForm';

export default function CleanupPolicies() {
  return <MasterDetail path="admin/repository/cleanuppolicies">
    <Master>
      <CleanupPoliciesList/>
    </Master>
    <Detail>
      <CleanupPoliciesForm/>
    </Detail>
  </MasterDetail>;
}
