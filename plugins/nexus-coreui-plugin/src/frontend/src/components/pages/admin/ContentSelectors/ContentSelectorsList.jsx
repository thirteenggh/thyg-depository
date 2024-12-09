import React from 'react';
import {useMachine} from '@xstate/react';

import {faChevronRight, faInfoCircle, faScroll} from '@fortawesome/free-solid-svg-icons';

import {
  ContentBody,
  HelpTile,
  NxButton,
  NxFilterInput,
  NxFontAwesomeIcon,
  NxTable,
  NxTableBody,
  NxTableCell,
  NxTableHead,
  NxTableRow,
  Page,
  PageActions,
  PageHeader,
  PageTitle,
  Section,
  SectionToolbar,
  Utils
} from 'nexus-ui-plugin';

import ContentSelectorsListMachine from './ContentSelectorsListMachine';

import UIStrings from '../../../../constants/UIStrings';

export default function ContentSelectorsList({onCreate, onEdit}) {
  const [current, send] = useMachine(ContentSelectorsListMachine, {devTools: true});
  const isLoading = current.matches('loading');
  const data = current.context.data;
  const filterText = current.context.filter;
  const error = current.context.error;

  const nameSortDir = Utils.getSortDirection('name', current.context);
  const typeSortDir = Utils.getSortDirection('type', current.context);
  const descriptionSortDir = Utils.getSortDirection('description', current.context);

  function filter(value) {
    send('FILTER', {filter: value});
  }

  return <Page className="nxrm-content-selectors">
    <PageHeader>
      <PageTitle icon={faScroll} {...UIStrings.CONTENT_SELECTORS.MENU}/>
      <PageActions>
        <NxButton variant="primary" onClick={onCreate}>
          <span>{UIStrings.CONTENT_SELECTORS.CREATE_BUTTON}</span>
        </NxButton>
      </PageActions>
    </PageHeader>
    <ContentBody>
      <Section className="nxrm-content-selectors-list">
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
              <NxTableCell onClick={() => send('SORT_BY_NAME')} isSortable sortDir={nameSortDir}>
                {UIStrings.CONTENT_SELECTORS.NAME_LABEL}
              </NxTableCell>
              <NxTableCell onClick={() => send('SORT_BY_TYPE')} isSortable sortDir={typeSortDir}>
                {UIStrings.CONTENT_SELECTORS.TYPE_LABEL}
              </NxTableCell>
              <NxTableCell onClick={() => send('SORT_BY_DESCRIPTION')} isSortable sortDir={descriptionSortDir}>
                {UIStrings.CONTENT_SELECTORS.DESCRIPTION_LABEL}
              </NxTableCell>
              <NxTableCell hasIcon/>
            </NxTableRow>
          </NxTableHead>
          <NxTableBody isLoading={isLoading} error={error} emptyMessage={UIStrings.CONTENT_SELECTORS.EMPTY_MESSAGE}>
            {data.map(({name, type, description}) => (
                <NxTableRow key={name} onClick={() => onEdit(name)} isClickable>
                  <NxTableCell>{name}</NxTableCell>
                  <NxTableCell>{type?.toUpperCase()}</NxTableCell>
                  <NxTableCell>{description}</NxTableCell>
                  <NxTableCell hasIcon><NxFontAwesomeIcon icon={faChevronRight}/></NxTableCell>
                </NxTableRow>
            ))}
          </NxTableBody>
        </NxTable>
      </Section>

      <HelpTile>
        <h3><NxFontAwesomeIcon icon={faInfoCircle}/>{UIStrings.CONTENT_SELECTORS.HELP_TITLE}</h3>
        <p dangerouslySetInnerHTML={{__html: UIStrings.CONTENT_SELECTORS.HELP_TEXT}}/>
      </HelpTile>
    </ContentBody>
  </Page>;
}
