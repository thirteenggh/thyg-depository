import React from 'react';
import {useMachine} from '@xstate/react';
import UIStrings from '../../../../constants/UIStrings';
import {ContentBody, ExtJS, NxLoadWrapper, Page, PageHeader, PageTitle, Section} from 'nexus-ui-plugin';

import SupportZipForm from './SupportZipForm';
import SupportZipResponse from './SupportZipResponse';
import SupportZipResponseHA from './SupportZipResponseHA';
import SupportZipMachine from './SupportZipMachine';
import {faArchive} from '@fortawesome/free-solid-svg-icons';

export default function SupportZip() {
  const [current, send] = useMachine(SupportZipMachine, {devTools: true});
  const isLoading = current.matches('creatingSupportZips') || current.matches('creatingHaSupportZips');
  const isCreated = current.matches('supportZipsCreated');
  const isHaZipsCreated = current.matches('haSupportZipsCreated');
  const {params, response, createError} = current.context;
  const isHa = ExtJS.state().isClustered();

  function retry() {
    if (isHa) {
      send({type: 'RETRY_HA'});
    }
    else {
      send({type: 'RETRY'});
    }
  }

  function setParams({target}) {
    send({
      type: 'UPDATE',
      params: {
        ...params,
        [target.id]: target.checked
      }
    });
  }

  function submit(event) {
    event.preventDefault();
    send('CREATE_SUPPORT_ZIPS');
  }

  function hazips(event) {
    event.preventDefault();
    send('CREATE_HA_SUPPORT_ZIPS');
  }

  function download(event, filename) {
    event.preventDefault();
    ExtJS.requestAuthenticationToken(UIStrings.SUPPORT_ZIP.AUTHENTICATE_TEXT).then(authToken => {
      const url = ExtJS.urlOf(`service/rest/wonderland/download/${filename}?t=${btoa(authToken)}`);
      ExtJS.downloadUrl(url);
    });
  }

  return <Page>
    <PageHeader><PageTitle icon={faArchive} {...UIStrings.SUPPORT_ZIP.MENU}/></PageHeader>
    <ContentBody className="nxrm-support-zip">
      <Section>
        {!(isCreated || isHaZipsCreated) &&
        <NxLoadWrapper loading={isLoading} error={createError ? `${createError}` : null} retryHandler={retry}>
          <SupportZipForm params={params} setParams={setParams} submit={submit} clustered={isHa} hazips={hazips}/>
        </NxLoadWrapper>
        }
        {isCreated && <SupportZipResponse response={response} download={download}/>}
        {isHaZipsCreated && <SupportZipResponseHA response={response} download={download}/>}
      </Section>
    </ContentBody>
  </Page>;
}
