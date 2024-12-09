import React from 'react';
import {useMachine} from '@xstate/react';

import {
  FieldWrapper,
  NxButton,
  NxFilterInput,
  NxLoadWrapper,
  NxTable,
  NxTableBody,
  NxTableCell,
  NxTableHead,
  NxTableRow,
  Section, SectionToolbar,
  Select,
  Utils
} from 'nexus-ui-plugin';
import UIStrings from "../../../../constants/UIStrings";

import ContentSelectorsPreviewMachine from './ContentSelectorsPreviewMachine';

export default function ContentSelectorsPreview({type, expression}) {
  const [current, send] = useMachine(ContentSelectorsPreviewMachine, {devTools: true});
  const previewUnavailable = Utils.isBlank(expression);
  const {allRepositories, filterText, preview, previewError, repositories} = current.context;
  const isLoading = current.matches('loading');
  const isLoadingPreview = current.matches('preview');

  const repositoryChangeHandler = (event) => send('SET_REPOSITORIES', {repositories: event.target.value});
  const previewHandler = () => send('PREVIEW', {selectorType: type, expression});
  const filter = (value) => send('FILTER', {filter: value});

  function retry() {
    send('RETRY');
  }

  return <Section className="nxrm-content-selectors-preview">
    <h2>{UIStrings.CONTENT_SELECTORS.TITLE}</h2>
    <NxLoadWrapper isLoading={isLoading} retryHandler={retry}>
      <FieldWrapper labelText={UIStrings.CONTENT_SELECTORS.PREVIEW.REPOSITORY_LABEL}
                    descriptionText={UIStrings.CONTENT_SELECTORS.PREVIEW.REPOSITORY_DESCRIPTION}>
        <Select name="repository" onChange={repositoryChangeHandler} value={repositories}>
          {allRepositories.map(({id, name}) =>
              <option key={id} value={id}>{name}</option>
          )}
        </Select>
        <NxButton disabled={previewUnavailable} onClick={previewHandler}>{UIStrings.CONTENT_SELECTORS.PREVIEW.BUTTON}</NxButton>
      </FieldWrapper>
      <SectionToolbar>
        <div className="nxrm-spacer" />
        <NxFilterInput
            inputId="filter"
            onChange={filter}
            value={filterText}
            placeholder={UIStrings.CONTENT_SELECTORS.FILTER_PLACEHOLDER}/>
      </SectionToolbar>
      <NxTable>
        <NxTableHead>
          <NxTableRow>
            <NxTableCell>{UIStrings.CONTENT_SELECTORS.PREVIEW.NAME_COLUMN}</NxTableCell>
          </NxTableRow>
        </NxTableHead>
        <NxTableBody isLoading={isLoadingPreview} error={previewError} emptyMessage={UIStrings.CONTENT_SELECTORS.PREVIEW.EMPTY}>
          {preview?.map(name =>
              <NxTableRow key={name}>
                <NxTableCell>{name}</NxTableCell>
              </NxTableRow>
          )}
        </NxTableBody>
      </NxTable>
    </NxLoadWrapper>
  </Section>;
}
