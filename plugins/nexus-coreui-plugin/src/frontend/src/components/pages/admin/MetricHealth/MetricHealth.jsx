import React from 'react';
import {useMachine} from '@xstate/react';

import './MetricHealth.scss';

import {faCheckCircle, faExclamationCircle, faMedkit} from '@fortawesome/free-solid-svg-icons';

import {
  ContentBody,
  NxFontAwesomeIcon,
  NxTable,
  NxTableBody,
  NxTableCell,
  NxTableHead,
  NxTableRow,
  Page,
  PageHeader,
  PageTitle,
  Section,
  Utils
} from 'nexus-ui-plugin';

import MetricHealthMachine from './MetricHealthMachine';

import UIStrings from "../../../../constants/UIStrings";

export default function MetricHealth() {
  const [current, send] = useMachine(MetricHealthMachine, {devTools: true});
  const isLoading = current.matches('loading');
  const data = current.context.data;
  const error = current.context.error;

  const nameSortDir = Utils.getSortDirection('name', current.context);
  const messageSortDir = Utils.getSortDirection('message', current.context);
  const errorSortDir = Utils.getSortDirection('error', current.context);

  return <Page>
    <PageHeader><PageTitle icon={faMedkit} {...UIStrings.METRIC_HEALTH.MENU}/></PageHeader>
    <ContentBody className="nxrm-metric-health">
      <Section>
        <NxTable>
          <NxTableHead>
            <NxTableRow>
              <NxTableCell hasIcon />
              <NxTableCell onClick={() => send('SORT_BY_NAME')} isSortable sortDir={nameSortDir}>
                {UIStrings.METRIC_HEALTH.NAME_HEADER}
              </NxTableCell>
              <NxTableCell onClick={() => send('SORT_BY_MESSAGE')} isSortable sortDir={messageSortDir}
                >{UIStrings.METRIC_HEALTH.MESSAGE_HEADER}
              </NxTableCell>
              <NxTableCell onClick={() => send('SORT_BY_ERROR')} isSortable sortDir={errorSortDir}>
                {UIStrings.METRIC_HEALTH.ERROR_HEADER}
              </NxTableCell>
            </NxTableRow>
          </NxTableHead>
          <NxTableBody isLoading={isLoading} error={error}>
            {data.map(metric => (
              <NxTableRow key={metric.name}>
                <NxTableCell hasIcon>
                  <NxFontAwesomeIcon
                    className={metric.healthy ? 'healthy' : 'unhealthy'}
                    icon={metric.healthy ? faCheckCircle : faExclamationCircle}/>
                </NxTableCell>
                <NxTableCell>{metric.name}</NxTableCell>
                <NxTableCell><span dangerouslySetInnerHTML={{__html: metric.message}} /></NxTableCell>
                <NxTableCell>{metric.error ? metric.error.message : ''}</NxTableCell>
              </NxTableRow>
            ))}
          </NxTableBody>
        </NxTable>
      </Section>
    </ContentBody>
  </Page>;
}
