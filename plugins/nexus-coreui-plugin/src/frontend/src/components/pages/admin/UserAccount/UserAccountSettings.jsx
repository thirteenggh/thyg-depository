import React from 'react';
import {useService} from '@xstate/react';
import {
  Alert,
  FieldWrapper,
  NxButton,
  NxLoadWrapper,
  NxSubmitMask,
  NxTooltip,
  Section,
  SectionFooter,
  Textfield,
  Utils
} from 'nexus-ui-plugin';
import UIStrings from '../../../../constants/UIStrings';

export default function UserAccountSettings({service}) {
  const [current, send] = useService(service);
  const context = current.context;
  const data = context.data;
  const external = data?.external;
  const isLoading = current.matches('loading');
  const isSaving = current.matches('saving');
  const isPristine = context.isPristine;
  const validationErrors = context.validationErrors;
  const isInvalid = Utils.isInvalid(validationErrors);
  const hasData = data && data !== {};

  function handleSave(evt) {
    evt.preventDefault();
    send('SAVE');
  }

  function handleDiscard() {
    send('RESET');
  }

  function handleChange({target}) {
    send('UPDATE', {
      data: {
        [target.name]: target.value
      }
    });
  }

  function retry() {
    send('RETRY');
  }

  let error = null;
  if (context.error instanceof Array) {
    error = (
        <Alert type="error">
          {UIStrings.USER_ACCOUNT.MESSAGES.UPDATE_ERROR}
          <ul>
            {context.error.map(e => <li key={e.id}>{JSON.stringify(e)}</li>)}
          </ul>
        </Alert>
    );
  }
  else if (context.error) {
    error = (
        <Alert type="error">
          {UIStrings.USER_ACCOUNT.MESSAGES.UPDATE_ERROR}<br/>
          {context.error}
        </Alert>
    );
  }

  return <Section>
    <NxLoadWrapper loading={isLoading} retryHandler={retry}>
      {hasData && <>
        {isSaving && <NxSubmitMask message={UIStrings.SAVING}/>}
        {error}

        <FieldWrapper labelText={UIStrings.USER_ACCOUNT.ID_FIELD_LABEL}>
          <Textfield name="userId" readOnly disabled value={data.userId}/>
        </FieldWrapper>
        <FieldWrapper labelText={UIStrings.USER_ACCOUNT.FIRST_FIELD_LABEL}>
          <Textfield {...buildFieldProps('firstName', current, handleChange)}/>
        </FieldWrapper>
        <FieldWrapper labelText={UIStrings.USER_ACCOUNT.LAST_FIELD_LABEL}>
          <Textfield {...buildFieldProps('lastName', current, handleChange)}/>
        </FieldWrapper>
        <FieldWrapper labelText={UIStrings.USER_ACCOUNT.EMAIL_FIELD_LABEL}>
          <Textfield {...buildFieldProps('email', current, handleChange)}/>
        </FieldWrapper>
        <SectionFooter>
          <NxTooltip title={Utils.saveTooltip({isPristine, isInvalid})}>
            <NxButton variant='primary' className={(isPristine || isInvalid) && 'disabled'} disabled={external} onClick={handleSave}>
              {UIStrings.SETTINGS.SAVE_BUTTON_LABEL}
            </NxButton>
          </NxTooltip>
          <NxTooltip title={Utils.discardTooltip({isPristine})}>
            <NxButton disabled={external} className={isPristine && 'disabled'} onClick={handleDiscard}>
              {UIStrings.SETTINGS.DISCARD_BUTTON_LABEL}
            </NxButton>
          </NxTooltip>
        </SectionFooter>
      </>}
    </NxLoadWrapper>
  </Section>;
}

function buildFieldProps(name, current, handleChange) {
  const readOnly = current.context.data?.external;
  return {
    ...Utils.fieldProps(name, current),
    disabled: readOnly,
    readOnly,
    onChange: handleChange
  }
}
