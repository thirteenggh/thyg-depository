import React from 'react';
import {useMachine} from '@xstate/react';

import {
  ContentBody,
  FieldWrapper,
  NxButton,
  NxErrorAlert,
  NxFontAwesomeIcon,
  NxInfoAlert,
  NxLoadWrapper,
  NxSubmitMask,
  NxSuccessAlert,
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

import RoutingRuleFormMachine from './RoutingRulesFormMachine';

import UIStrings from '../../../../constants/UIStrings';
import {faPlus, faTrash} from '@fortawesome/free-solid-svg-icons';
import RoutingRulesPreview from "./RoutingRulesFormPreview";

const {ROUTING_RULES, SETTINGS} = UIStrings;

export default function RoutingRulesForm({itemId, onDone}) {
  const [current, send] = useMachine(RoutingRuleFormMachine, {
    context: {
      pristineData: {
        name: itemId
      }
    },

    actions: {
      onCancel: onDone,
      onSaveSuccess: onDone,
      onDeleteSuccess: onDone
    },

    devTools: true
  });

  const {isPristine, isTouched, data, loadError, path, saveError, testError, testResult, validationErrors} = current.context;
  const isLoading = current.matches('loading');
  const isSaving = current.matches('saving');
  const isTesting = current.matches('testing');
  const isInvalid = Utils.isInvalid(validationErrors);
  const hasData = data && data !== {};
  const isEdit = Boolean(itemId);
  const hasAssignedRepositories = data?.assignedRepositoryCount > 0;

  function addMatcher() {
    send({type: 'ADD_MATCHER'});
  }

  function removeMatcher(index) {
    send({type: 'REMOVE_MATCHER', index});
  }

  function updateMatcher(event, index) {
    send({type: 'UPDATE_MATCHER', index, value: event.target.value})
  }

  function update(event) {
    send({type: 'UPDATE', data: {[event.target.name]: event.target.value}});
  }

  function save(event) {
    event.preventDefault();
    send({type: 'SAVE'});
  }

  function handleEnter(event) {
    if (event.key === 'Enter') {
      save(event);
    }
  }

  function cancel() {
    send({type: 'CANCEL'});
  }

  function remove() {
    send({type: 'DELETE'});
  }

  function retry() {
    send({type: 'RETRY'});
  }

  function updatePath(event) {
    send({type: 'UPDATE_PATH', path: event.target.value});
  }

  function test() {
    send({type: 'TEST'});
  }

  return <Page className="nxrm-routing-rules">
    {isEdit &&
    <NxInfoAlert>
      {!hasAssignedRepositories && <span dangerouslySetInnerHTML={{__html: ROUTING_RULES.FORM.UNUSED}}/>}
      {hasAssignedRepositories && <span dangerouslySetInnerHTML={{__html: ROUTING_RULES.FORM.USED_BY(data.assignedRepositoryNames)}}/>}
    </NxInfoAlert>}
    <PageHeader>
      <PageTitle text={itemId ? ROUTING_RULES.FORM.EDIT_TITLE : ROUTING_RULES.FORM.CREATE_TITLE}/>
    </PageHeader>
    <ContentBody>
      <Section className="nxrm-routing-rules-form" onKeyPress={handleEnter}>
        <NxLoadWrapper loading={isLoading} error={loadError ? `${loadError}` : null} retryHandler={retry}>
          {hasData && <>
            {saveError && <NxErrorAlert>{ROUTING_RULES.FORM.SAVE_ERROR} {saveError}</NxErrorAlert>}
            {isSaving && <NxSubmitMask message={UIStrings.SAVING}/>}

            <FieldWrapper labelText={ROUTING_RULES.FORM.NAME_LABEL}>
              <Textfield
                  {...Utils.fieldProps('name', current)}
                  onChange={update}/>
            </FieldWrapper>
            <FieldWrapper labelText={ROUTING_RULES.FORM.DESCRIPTION_LABEL} isOptional>
              <Textfield
                  className="nx-text-input--long"
                  {...Utils.fieldProps('description', current)}
                  onChange={update}/>
            </FieldWrapper>
            <FieldWrapper labelText={ROUTING_RULES.FORM.MODE_LABEL}>
              <Select {...Utils.fieldProps('mode', current)} onChange={update}>
                <option value="ALLOW">{ROUTING_RULES.FORM.MODE.ALLOW}</option>
                <option value="BLOCK">{ROUTING_RULES.FORM.MODE.BLOCK}</option>
              </Select>
              <span>{ROUTING_RULES.FORM.MODE_DESCRIPTION}</span>
            </FieldWrapper>
            <div className="nx-form-group">
              <div id="matchers-label" className="nx-label">
                <span className="nx-label__text">{ROUTING_RULES.FORM.MATCHERS_LABEL}</span>
              </div>
              <p id="matchers-description" className="nx-p">{ROUTING_RULES.FORM.MATCHERS_DESCRIPTION}</p>

              {data.matchers.map((value, index) =>
                  <div className="nx-form-row" key={`matcher-${index}`}>
                    <div className="nx-form-group">
                      {/*This nx-label is required to ensure the button is at the correct height*/}
                      <label className="nx-label">
                        <Textfield
                            aria-label={ROUTING_RULES.FORM.MATCHER_LABEL(index)}
                            aria-describedby="matchers-description"
                            className="nx-text-input--long"
                            {...Utils.fieldProps(`matchers[${index}]`, current)}
                            value={value}
                            onChange={(event) => updateMatcher(event, index)}/>
                      </label>
                    </div>
                    {data.matchers.length > 1 &&
                    <div className="nx-btn-bar">
                      <NxButton altText={ROUTING_RULES.FORM.DELETE_MATCHER_BUTTON} onClick={() => removeMatcher(index)}>
                        <NxFontAwesomeIcon icon={faTrash}/>
                      </NxButton>
                    </div>}
                  </div>)
              }
              <div className="add-matcher">
                <NxButton variant="tertiary" onClick={addMatcher}>
                  <NxFontAwesomeIcon icon={faPlus}/>
                  <span>{ROUTING_RULES.FORM.ADD_MATCHER_BUTTON}</span>
                </NxButton>
              </div>
            </div>

            <SectionFooter>
              <NxTooltip title={Utils.saveTooltip({isPristine, isInvalid})}>
                <NxButton type="submit" variant="primary" className={(isPristine || isInvalid) && 'disabled'}
                          onClick={save}>
                  {itemId ? SETTINGS.SAVE_BUTTON_LABEL : ROUTING_RULES.FORM.CREATE_BUTTON}
                </NxButton>
              </NxTooltip>
              <NxButton onClick={cancel}>{SETTINGS.CANCEL_BUTTON_LABEL}</NxButton>
              {itemId &&
              <NxTooltip title={data.assignedRepositoryCount > 0 ? ROUTING_RULES.FORM.CANNOT_DELETE(
                  data.assignedRepositoryNames) : undefined}>
                <NxButton variant="tertiary" onClick={remove} disabled={data.assignedRepositoryCount > 0}>
                  <NxFontAwesomeIcon icon={faTrash}/>
                  <span>{SETTINGS.DELETE_BUTTON_LABEL}</span>
                </NxButton>
              </NxTooltip>}
            </SectionFooter>
          </>}
        </NxLoadWrapper>
      </Section>

      <Section className="nxrm-routing-rules-preview">
        <RoutingRulesPreview value={path} onChange={updatePath} onTest={test}/>
        <NxLoadWrapper className="preview-result" loading={isTesting} loadError={testError} retryHandler={test}>
          {testResult === true && <NxSuccessAlert>{ROUTING_RULES.FORM.PREVIEW.ALLOWED}</NxSuccessAlert>}
          {testResult === false && <NxErrorAlert>{ROUTING_RULES.FORM.PREVIEW.BLOCKED}</NxErrorAlert>}
        </NxLoadWrapper>
      </Section>
    </ContentBody>
  </Page>;
}
