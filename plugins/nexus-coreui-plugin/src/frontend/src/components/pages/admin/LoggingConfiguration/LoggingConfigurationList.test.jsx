import React from 'react';
import {act} from 'react-dom/test-utils';
import {fireEvent, render, wait} from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import TestUtils from 'nexus-ui-plugin/src/frontend/src/interface/TestUtils';
import axios from 'axios';

import LoggingConfigurationList from './LoggingConfigurationList';

import UIStrings from "../../../../constants/UIStrings";

jest.mock('axios', () => ({
  ...jest.requireActual('axios'), // Use most functions from actual axios
  get: jest.fn()
}));

describe('LoggingConfigurationList', function() {
  function renderView(view = <LoggingConfigurationList/>) {
    return TestUtils.render(view, ({queryByPlaceholderText}) => ({
      filter: () => queryByPlaceholderText(UIStrings.LOGGING.FILTER_PLACEHOLDER)
    }));
  }

  it('renders the resolved data', async function() {
    const rows = [
      {name: 'ROOT', level: 'INFO'},
      {name: 'org.sonatype.nexus', level: 'DEBUG'}
    ];

    axios.get.mockReturnValue(Promise.resolve({
      data: rows
    }));

    const {container, loadingMask} = renderView();

    await wait(() => expect(loadingMask()).not.toBeInTheDocument());

    rows.forEach((row, i) => {
      expect(container.querySelector(`tbody tr:nth-child(${i+1}) td:nth-child(1)`)).toHaveTextContent(row.name);
      expect(container.querySelector(`tbody tr:nth-child(${i+1}) td:nth-child(2)`)).toHaveTextContent(row.level);
    });
    expect(container).toMatchSnapshot();
  });

  it('renders a loading spinner', async function() {
    axios.get.mockReturnValue(new Promise(() => {}));

    const {container, loadingMask} = renderView();

    expect(loadingMask()).toBeInTheDocument();
    expect(container).toMatchSnapshot();
  });

  it('renders an error message', async function() {
    axios.get.mockReturnValue(Promise.reject({message: 'Error'}));

    const {container, loadingMask} = renderView();

    await wait(() => expect(loadingMask()).not.toBeInTheDocument());

    expect(container.querySelector('.nx-cell--meta-info')).toHaveTextContent('Error');
    expect(container).toMatchSnapshot();
  });

  it('searches for lower-cased strings in the logger names', async function() {
    axios.get.mockReturnValue(Promise.resolve({
      data: [
        {name: 'ROOT', level: 'INFO'},
        {name: 'org.sonatype.nexus', level: 'DEBUG'}
      ]
    }));

    const {container, loadingMask, filter} = renderView();

    await wait(() => expect(loadingMask()).not.toBeInTheDocument());

    fireEvent.change(filter(), {target: {value: 'sonatype'}});

    expect(container.querySelector('tbody tr:nth-child(1) td:nth-child(1)')).toHaveTextContent('org.sonatype.nexus');
  });
});
