import React from 'react';
import {wait, screen} from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import TestUtils from 'nexus-ui-plugin/src/frontend/src/interface/TestUtils';
import axios from 'axios';

import CleanupPoliciesList from './CleanupPoliciesList';

import UIStrings from "../../../../constants/UIStrings";

jest.mock('axios', () => ({
  get: jest.fn()
}));

describe('CleanupPoliciesList', function() {
  function renderView(view = <CleanupPoliciesList/>) {
    return TestUtils.render(view, ({queryByPlaceholderText}) => ({
      filter: () => queryByPlaceholderText(UIStrings.CLEANUP_POLICIES.FILTER_PLACEHOLDER)
    }));
  }

  it('renders the resolved data', async function() {
    const rows = [
      {
        name: 'cleanup',
        format: 'testformat',
        notes: 'cleanup-description'
      },{
        name: 'test',
        format: 'testformat',
        notes: 'notes'
      }
    ];

    axios.get.mockImplementation((url) => {
      if (url === '/service/rest/internal/cleanup-policies') {
        return Promise.resolve({data: rows});
      }
      else if (url === '/service/rest/internal/cleanup-policies/criteria/formats') {
        return Promise.resolve({
          data: [{
            'id' : 'testformat',
            'name' : 'Test Format',
            'availableCriteria' : ['lastBlobUpdated', 'lastDownloaded', 'isPrerelease', 'regex']
          }]
        });
      }
    });

    const {container, loadingMask} = renderView();

    await wait(() => expect(loadingMask()).not.toBeInTheDocument());

    rows.forEach((row, i) => {
      expect(container.querySelector(`tbody tr:nth-child(${i+1}) td:nth-child(1)`)).toHaveTextContent(row.name);
      expect(container.querySelector(`tbody tr:nth-child(${i+1}) td:nth-child(2)`)).toHaveTextContent(row.format);
      expect(container.querySelector(`tbody tr:nth-child(${i+1}) td:nth-child(3)`)).toHaveTextContent(row.notes);
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
});
