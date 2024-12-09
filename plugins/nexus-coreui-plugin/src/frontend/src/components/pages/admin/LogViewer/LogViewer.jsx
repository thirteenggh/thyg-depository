import React, {useEffect, useRef, useState} from 'react';
import classNames from 'classnames';
import {useMachine} from '@xstate/react';

import {
  ContentBody,
  Code,
  ExtJS,
  NxButton,
  NxFontAwesomeIcon,
  Page,
  PageActions,
  PageHeader,
  PageTitle,
  Select,
  Section,
  SectionToolbar,
  Textfield
} from 'nexus-ui-plugin';

import {faScroll, faStamp} from '@fortawesome/free-solid-svg-icons';

import LogViewerMachine from "./LogViewerMachine";
import UIStrings from "../../../../constants/UIStrings";
import './LogViewer.scss';

export default function LogViewer() {
  const refreshPeriods = [
    [0,   UIStrings.LOG_VIEWER.REFRESH.MANUAL_ITEM],
    [20,  UIStrings.LOG_VIEWER.REFRESH.TWENTY_SECONDS_ITEM],
    [60,  UIStrings.LOG_VIEWER.REFRESH.MINUTE_ITEM],
    [120, UIStrings.LOG_VIEWER.REFRESH.TWO_MINUTES_ITEM],
    [300, UIStrings.LOG_VIEWER.REFRESH.FIVE_MINUTES_ITEM]
  ];
  const refreshSizes = [
    [25,  UIStrings.LOG_VIEWER.SIZE.LAST25KB_ITEM],
    [50,  UIStrings.LOG_VIEWER.SIZE.LAST50KB_ITEM],
    [100, UIStrings.LOG_VIEWER.SIZE.LAST100KB_ITEM]
  ];
  const [current, send] = useMachine(LogViewerMachine, {devTools: true});
  const selectedPeriod = current.context.period;
  const selectedSize = current.context.size;
  const mark = current.context.mark;
  const logText = current.context.data;
  const textarea = useRef(null);

  // scroll to bottom of text area
  useEffect(() => {
    if (textarea.current) {
      textarea.current.scrollTop = textarea.current.scrollHeight;
    }
  });

  function onChangePeriod({target}) {
    const newPeriod = Number(target.value);
    if (newPeriod == 0) {
      send('MANUAL_REFRESH', {period: newPeriod});
    }
    else {
      send('UPDATE_PERIOD', {period: newPeriod});
    }
  }

  function onChangeSize({target}) {
    const newSize = Number(target.value);
    send('UPDATE_SIZE', {size: newSize});
  }

  function download() {
    const url = ExtJS.urlOf('service/rest/internal/logging/log');
    ExtJS.downloadUrl(url);
  }

  function updateMark({target}) {
    send('UPDATE_MARK', {mark: target.value});
  }

  function onMarkKeyPress(event) {
    if (event.key === 'Enter') {
      event.preventDefault();
      send('INSERT_MARK');
    }
  }  

  function insertMark() {
    send('INSERT_MARK');
  }

  return <Page className="nxrm-log-viewer">
    <PageHeader>
      <PageTitle icon={faScroll} {...UIStrings.LOG_VIEWER.MENU}/>
      <PageActions>
        <NxButton variant="primary" onClick={download}>
          <span>{UIStrings.LOG_VIEWER.DOWNLOAD}</span>
        </NxButton>
      </PageActions>
    </PageHeader>
    <ContentBody>
      <Section>
        <SectionToolbar>
          <Textfield
              className="nxrm-log-viewer-mark-text"
              name="mark"
              value={mark}
              isPristine={true}
              onChange={updateMark}
              onKeyPress={onMarkKeyPress}
              placeholder={UIStrings.LOG_VIEWER.MARK_PLACEHOLDER}
          />
          <NxButton onClick={insertMark} id="insertMark">
            <NxFontAwesomeIcon icon={faStamp}/>
            <span>{UIStrings.LOG_VIEWER.INSERT_MARK}</span>
          </NxButton>

          <div className="nxrm-spacer" />

          <label htmlFor="period">{UIStrings.LOG_VIEWER.REFRESH.TEXT}</label>
          <SelectAction>
            <Select name="period" id="period" value={selectedPeriod} onChange={onChangePeriod}>
              {refreshPeriods.map(([period, label]) =>
                  <option key={period} value={period}>{label}</option>
              )}
            </Select>
          </SelectAction>
          <SelectAction>
            <Select name="size" value={selectedSize} onChange={onChangeSize}>
              {refreshSizes.map(([size, label]) =>
                  <option key={size} value={size}>{label}</option>
              )}
            </Select>
          </SelectAction>
        </SectionToolbar>
        <Code ref={textarea} readOnly value={logText} />
      </Section>
    </ContentBody>
  </Page>;
}

function SelectAction({className, children, ...attrs}) {
  const classes = classNames('nxrm-select-action', className);

  return <div className={classes} {...attrs}>
    {children}
  </div>;
}
