import React from 'react';
import {useMachine} from '@xstate/react';

import {faChevronRight, faRedo, faScroll} from '@fortawesome/free-solid-svg-icons';

import {
  ContentBody,
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

import LoggingConfigurationListMachine from './LoggingConfigurationListMachine';

import UIStrings from '../../../../constants/UIStrings';

export default function LoggingConfigurationList({onCreate, onEdit}) {
  const [current, send] = useMachine(LoggingConfigurationListMachine, {devTools: true});
  const isLoading = current.matches('loading');
  const data = current.context.data;
  const filterText = current.context.filter;
  const error = current.context.error;

  const nameSortDir = Utils.getSortDirection('name', current.context);
  const levelSortDir = Utils.getSortDirection('level', current.context);

  function filter(value) {
    send('FILTER', {filter: value});
  }

  function reset() {
    send('RESET');
  }

  return <Page className="nxrm-logging-configuration">
    <PageHeader>
      <PageTitle icon={faScroll} {...UIStrings.LOGGING.MENU}/>
      <PageActions>
        <NxButton variant="tertiary" onClick={reset}>
          <NxFontAwesomeIcon icon={faRedo}/>
          <span>{UIStrings.LOGGING.RESET_ALL_BUTTON}</span>
        </NxButton>
        <NxButton variant="primary" onClick={onCreate}>
          <span>{UIStrings.LOGGING.CREATE_BUTTON}</span>
        </NxButton>
      </PageActions>
    </PageHeader>
    <ContentBody>
      <Section className="nxrm-logging-configuration-list">
        <SectionToolbar>
          <div className="nxrm-spacer" />
          <NxFilterInput
              inputId="filter"
              onChange={filter}
              value={filterText}
              placeholder={UIStrings.LOGGING.FILTER_PLACEHOLDER}/>
        </SectionToolbar>
        <NxTable>
          <NxTableHead>
            <NxTableRow>
              <NxTableCell onClick={() => send('SORT_BY_NAME')} isSortable sortDir={nameSortDir}>
                {UIStrings.LOGGING.NAME_LABEL}
              </NxTableCell>
              <NxTableCell onClick={() => send('SORT_BY_LEVEL')} isSortable sortDir={levelSortDir}>
                {UIStrings.LOGGING.LEVEL_LABEL}
              </NxTableCell>
              <NxTableCell hasIcon/>
            </NxTableRow>
          </NxTableHead>
          <NxTableBody isLoading={isLoading} error={error}>
            {data.map(({name, level}) => (
                <NxTableRow key={name} onClick={() => onEdit(name)} isClickable>
                  <NxTableCell>{name}</NxTableCell>
                  <NxTableCell>{level}</NxTableCell>
                  <NxTableCell hasIcon><NxFontAwesomeIcon icon={faChevronRight}/></NxTableCell>
                </NxTableRow>
            ))}
          </NxTableBody>
        </NxTable>
      </Section>
    </ContentBody>
  </Page>;
}
