import React from 'react';

import {Detail, Master, MasterDetail} from 'nexus-ui-plugin';

import ContentSelectorsList from './ContentSelectorsList';
import ContentSelectorsForm from './ContentSelectorsForm';

export default function ContentSelectors() {
  return <MasterDetail path="admin/repository/selectors">
    <Master>
      <ContentSelectorsList/>
    </Master>
    <Detail>
      <ContentSelectorsForm/>
    </Detail>
  </MasterDetail>;
}
