import React from 'react';
import {useMachine} from '@xstate/react';
import UserAccountSettings from './UserAccountSettings';
import PasswordChangeForm from './PasswordChangeForm';
import {faUser} from '@fortawesome/free-solid-svg-icons';
import {ContentBody, Page, PageHeader, PageTitle} from 'nexus-ui-plugin';

import './UserAccount.scss';
import UserAccountMachine from './UserAccountMachine';
import UIStrings from '../../../../constants/UIStrings';

export default function UserAccount() {
  const [current, _, userAccountService] = useMachine(UserAccountMachine, {devTools: true});

  return <Page>
    <PageHeader><PageTitle icon={faUser} {...UIStrings.USER_ACCOUNT.MENU}/></PageHeader>
    <ContentBody className='nxrm-user-account'>
      <UserAccountSettings service={userAccountService}/>

    { current.context.data?.external === false &&  <PasswordChangeForm userId={current.context.data.userId}/> }
    </ContentBody>
  </Page>;
}
