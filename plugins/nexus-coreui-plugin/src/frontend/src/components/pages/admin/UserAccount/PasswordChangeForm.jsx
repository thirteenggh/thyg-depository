import React from 'react';
import {useMachine} from '@xstate/react';
import PasswordChangeMachine from './PasswordChangeMachine';
import {FieldWrapper, NxButton, NxTooltip, SectionFooter, Section, Textfield, Utils} from 'nexus-ui-plugin';
import UIStrings from '../../../../constants/UIStrings';

export default function PasswordChangeForm({userId}) {
  const [current, send] = useMachine(PasswordChangeMachine, {devTools: true});
  const {isPristine, validationErrors} = current.context;
  const isInvalid = Utils.isInvalid(validationErrors);

  function update(event) {
    send('UPDATE', {data: {[event.target.name]: event.target.value}});
  }

  function handlePasswordSubmit() {
    send({type: 'SAVE', userId: userId});
  }

  function handlePasswordDiscard() {
    send('RESET');
  }

  return <Section>
    <FieldWrapper labelText={UIStrings.USER_ACCOUNT.PASSWORD_CURRENT_FIELD_LABEL}>
      <Textfield {...Utils.fieldProps('passwordCurrent', current)} type="password" onChange={update}/>
    </FieldWrapper>
    <FieldWrapper labelText={UIStrings.USER_ACCOUNT.PASSWORD_NEW_FIELD_LABEL}>
      <Textfield {...Utils.fieldProps('passwordNew', current)} type="password" onChange={update} />
    </FieldWrapper>
    <FieldWrapper labelText={UIStrings.USER_ACCOUNT.PASSWORD_NEW_CONFIRM_FIELD_LABEL}>
      <Textfield {...Utils.fieldProps('passwordNewConfirm', current)} type="password" onChange={update} />
    </FieldWrapper>
    <SectionFooter>
      <NxTooltip text={Utils.saveTooltip({isInvalid})}>
        <NxButton variant='primary' className={isInvalid && 'disabled'} onClick={handlePasswordSubmit}>
          {UIStrings.USER_ACCOUNT.ACTIONS.changePassword}
        </NxButton>
      </NxTooltip>
      <NxTooltip text={Utils.discardTooltip({isPristine})}>
        <NxButton className={isPristine && 'disabled'} onClick={handlePasswordDiscard}>
          {UIStrings.USER_ACCOUNT.ACTIONS.discardChangePassword}
        </NxButton>
      </NxTooltip>
    </SectionFooter>
  </Section>;
}
