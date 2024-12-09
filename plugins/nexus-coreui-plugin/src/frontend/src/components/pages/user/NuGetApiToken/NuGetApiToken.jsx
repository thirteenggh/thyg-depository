import React from 'react';
import {useMachine} from '@xstate/react';
import {NxButton, NxLoadWrapper, Page, PageHeader, PageTitle, Section, SectionFooter, ContentBody} from 'nexus-ui-plugin';
import {NxFontAwesomeIcon} from '@sonatype/react-shared-components';
import {faKey, faLock} from '@fortawesome/free-solid-svg-icons';

import NuGetApiTokenMachine from './NuGetApiTokenMachine';
import NuGetApiTokenModal from './NuGetApiTokenModal';
import UIStrings from '../../../../constants/UIStrings';

export default function NuGetApiToken() {
  const [state, send] = useMachine(NuGetApiTokenMachine, {devTools: true});

  const isLoading = !(state.matches('idle') || state.matches('showToken'));
  const showNugetModal = state.matches('showToken');

  function handleAccessKey() {
    send('ACCESS');
  }

  function handleResetKey() {
    send('RESET');
  }

  function handleCloseKey() {
    send('HIDE');
  }

  function retry() {
    send('RETRY');
  }

  return <Page>
    <PageHeader><PageTitle icon={faKey} {...UIStrings.NUGET_API_KEY.MENU}/></PageHeader>
    <ContentBody>
      <Section>
        <NxLoadWrapper isLoading={isLoading} retryHandler={retry}>
          <p> {UIStrings.NUGET_API_KEY.INSTRUCTIONS} </p>
          <SectionFooter>
            <NxButton variant='primary' onClick={handleAccessKey}>
              <span>{UIStrings.NUGET_API_KEY.ACCESS.BUTTON}</span>
            </NxButton>
            <NxButton onClick={handleResetKey}>
              <NxFontAwesomeIcon icon={faLock}/>
              <span>{UIStrings.NUGET_API_KEY.RESET.BUTTON}</span>
            </NxButton>
          </SectionFooter>

          {showNugetModal && <NuGetApiTokenModal apiKey={state.context.token.apiKey} onCloseClick={handleCloseKey}/>}
        </NxLoadWrapper>
      </Section>
    </ContentBody>
  </Page>;
}
