import React from 'react';
import {ContentBody, NxButton, NxFontAwesomeIcon, Page, PageHeader, PageTitle, Section} from 'nexus-ui-plugin';
import {faExternalLinkAlt, faUserCircle} from '@fortawesome/free-solid-svg-icons';

import UIStrings from '../../../../constants/UIStrings';
import './SupportRequest.scss';

export default function SupportRequest() {
  function openSupportRequestPage() {
    window.open('https://links.sonatype.com/products/nexus/pro/support-request', '_blank');
  }

  return <Page>
    <PageHeader><PageTitle icon={faUserCircle} {...UIStrings.SUPPORT_REQUEST.MENU}/></PageHeader>
    <ContentBody className='nxrm-support-request'>
      <Section>
        <p>{UIStrings.SUPPORT_REQUEST.DESCRIPTION}</p>
        <p><a href="#admin/support/supportzip">{UIStrings.SUPPORT_REQUEST.ATTACH_SUPPORT_ZIP}</a></p>
        <NxButton variant="primary" onClick={() => openSupportRequestPage()}>
          <NxFontAwesomeIcon icon={faExternalLinkAlt}/>
          <span>{UIStrings.SUPPORT_REQUEST.ACTIONS.submitRequest}</span>
        </NxButton>
      </Section>
    </ContentBody>
  </Page>;
}
