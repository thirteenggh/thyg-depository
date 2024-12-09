import React from 'react';
import {useMachine} from '@xstate/react';
import {
  ContentBody,
  FieldWrapper,
  NxButton,
  NxCheckbox,
  NxLoadWrapper,
  NxTooltip,
  Page,
  PageHeader,
  PageTitle,
  Section,
  SectionFooter,
  Select,
  Textfield,
  Utils
} from 'nexus-ui-plugin';
import {faUser} from '@fortawesome/free-solid-svg-icons';

import UIStrings from '../../../../constants/UIStrings';

import AnonymousMachine from './AnonymousMachine';

export default function AnonymousSettings() {
  const [current, send] = useMachine(AnonymousMachine, {devTools: true});
  const {data, isPristine, realms, validationErrors} = current.context;
  const isLoading = current.matches('loading');
  const isInvalid = Utils.isInvalid(validationErrors);
  const hasData = data && data !== {};

  function handleInputChange({target}) {
    send('UPDATE', {
      data: {
        [target.name || target.id]: target.type === 'checkbox' ? target.checked : target.value
      }
    });
  }

  function handleDiscard() {
    send('RESET');
  }

  function handleSave() {
    send('SAVE');
  }

  function retry() {
    send('RETRY');
  }

  return <Page>
    <PageHeader><PageTitle icon={faUser} {...UIStrings.ANONYMOUS_SETTINGS.MENU}/></PageHeader>
    <ContentBody className='nxrm-anonymous-settings'>
      <Section>
        <NxLoadWrapper loading={isLoading} retryHandler={retry}>
          {hasData && <>
            <FieldWrapper labelText={UIStrings.ANONYMOUS_SETTINGS.ENABLED_CHECKBOX_LABEL}>
              <NxCheckbox
                  checkboxId='enabled'
                  isChecked={data.enabled}
                  onChange={handleInputChange}
              >
                {UIStrings.ANONYMOUS_SETTINGS.ENABLED_CHECKBOX_DESCRIPTION}
              </NxCheckbox>
            </FieldWrapper>
            <FieldWrapper labelText={UIStrings.ANONYMOUS_SETTINGS.USERNAME_TEXTFIELD_LABEL}>
              <Textfield
                  {...Utils.fieldProps('userId', current)}
                  onChange={handleInputChange}
              />
            </FieldWrapper>
            <FieldWrapper labelText={UIStrings.ANONYMOUS_SETTINGS.REALM_SELECT_LABEL}>
              <Select
                  name='realmName'
                  value={data.realmName}
                  onChange={handleInputChange}
              >
                {
                  realms.map((realm) =>
                      <option key={realm.id} value={realm.id}>{realm.name}</option>
                  )
                }
              </Select>
            </FieldWrapper>
            <SectionFooter>
              <NxTooltip title={Utils.saveTooltip({isPristine, isInvalid})}>
                <NxButton variant='primary' className={(isPristine || isInvalid) && 'disabled'} onClick={handleSave}>
                  {UIStrings.SETTINGS.SAVE_BUTTON_LABEL}
                </NxButton>
              </NxTooltip>
              <NxTooltip title={Utils.discardTooltip({isPristine})}>
                <NxButton className={isPristine && 'disabled'} onClick={handleDiscard}>
                  {UIStrings.SETTINGS.DISCARD_BUTTON_LABEL}
                </NxButton>
              </NxTooltip>
            </SectionFooter>
          </>}
        </NxLoadWrapper>
      </Section>
    </ContentBody>
  </Page>;
}
