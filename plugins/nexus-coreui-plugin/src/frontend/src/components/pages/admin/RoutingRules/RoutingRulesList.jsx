import React from 'react';
import {useMachine} from '@xstate/react';

import {faChevronRight, faInfoCircle, faMapSigns} from '@fortawesome/free-solid-svg-icons';

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

import RoutingRulesGlobalPreview from './RoutingRulesGlobalPreview';
import RoutingRulesListMachine from './RoutingRulesListMachine';

import UIStrings from '../../../../constants/UIStrings';

const {ROUTING_RULES} = UIStrings;

export default function RoutingRulesList({onCreate, onEdit}) {
  const [current, send] = useMachine(RoutingRulesListMachine, {devTools: true});
  const isLoading = current.matches('loading');
  const data = current.context.data;
  const filterText = current.context.filter;
  const error = current.context.error;

  function getSortDirection(fieldName, sortField, sortDirection) {
    if (sortField !== fieldName) {
      return null;
    }
    return sortDirection === Utils.ASC ? 'asc' : 'desc';
  }

  const nameSortDir = getSortDirection('name', current.context.sortField, current.context.sortDirection);
  const descriptionSortDir = getSortDirection('description', current.context.sortField, current.context.sortDirection);
  const usedBySortDir = getSortDirection('assignedRepositoryCount', current.context.sortField, current.context.sortDirection);

  function filter(value) {
    send({type: 'FILTER', filter: value});
  }

  function preview() {
    send({type: 'PREVIEW'});
  }

  if (current.matches('preview')) {
    return <RoutingRulesGlobalPreview/>
  }

  return <Page className="nxrm-routing-rules">
    <PageHeader>
      <PageTitle icon={faMapSigns} {...ROUTING_RULES.MENU}/>
      <PageActions>
        <NxButton variant="tertiary" onClick={preview}>
          <span>{ROUTING_RULES.LIST.PREVIEW_BUTTON}</span>
        </NxButton>
        <NxButton variant="primary" onClick={onCreate}>
          <span>{ROUTING_RULES.LIST.CREATE_BUTTON}</span>
        </NxButton>
      </PageActions>
    </PageHeader>
    <ContentBody>
      <Section className="nxrm-routing-rules-list">
        <SectionToolbar>
          <div className="nxrm-spacer" />
          <NxFilterInput
              inputId="filter"
              onChange={filter}
              value={filterText}
              placeholder={UIStrings.FILTER}/>
        </SectionToolbar>
        <NxTable>
          <NxTableHead>
            <NxTableRow>
              <NxTableCell onClick={() => send('SORT_BY_NAME')} isSortable sortDir={nameSortDir}>
                {ROUTING_RULES.LIST.NAME_LABEL}
              </NxTableCell>
              <NxTableCell onClick={() => send('SORT_BY_DESCRIPTION')} isSortable sortDir={descriptionSortDir}>
                {ROUTING_RULES.LIST.DESCRIPTION_LABEL}
              </NxTableCell>
              <NxTableCell onClick={() => send('SORT_BY_USED_BY')} isSortable sortDir={usedBySortDir}>
                {ROUTING_RULES.LIST.USED_BY_LABEL}
              </NxTableCell>
              <NxTableCell hasIcon/>
            </NxTableRow>
          </NxTableHead>
          <NxTableBody isLoading={isLoading} error={error} emptyMessage={ROUTING_RULES.LIST.EMPTY_LIST}>
            {data.map(({name, description, assignedRepositoryCount}) => (
                <NxTableRow key={name} onClick={() => onEdit(name)} isClickable>
                  <NxTableCell>{name}</NxTableCell>
                  <NxTableCell>{description}</NxTableCell>
                  <NxTableCell>
                    {assignedRepositoryCount === 0 ?
                        <a href="#admin/repository/repositories">{ROUTING_RULES.LIST.NEEDS_ASSIGNMENT}</a> :
                        <span>{ROUTING_RULES.LIST.USED_BY(assignedRepositoryCount)}</span>
                    }
                  </NxTableCell>
                  <NxTableCell hasIcon><NxFontAwesomeIcon icon={faChevronRight}/></NxTableCell>
                </NxTableRow>
            ))}
          </NxTableBody>
        </NxTable>
      </Section>

      <HelpTile>
        <h3><NxFontAwesomeIcon icon={faInfoCircle}/>{ROUTING_RULES.LIST.HELP_TITLE}</h3>
        <p dangerouslySetInnerHTML={{__html: ROUTING_RULES.LIST.HELP_TEXT}}/>
      </HelpTile>
    </ContentBody>
  </Page>;
}
