import React from 'react';
import {wait, screen} from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import TestUtils from 'nexus-ui-plugin/src/frontend/src/interface/TestUtils';
import axios from 'axios';

import ContentSelectorsList from './ContentSelectorsList';

import UIStrings from "../../../../constants/UIStrings";

jest.mock('axios', () => ({
  get: jest.fn()
}));

describe('ContentSelectorsList', function() {
  function renderView(view = <ContentSelectorsList/>) {
    return TestUtils.render(view, ({queryByPlaceholderText}) => ({
      filter: () => queryByPlaceholderText(UIStrings.CONTENT_SELECTORS.FILTER_PLACEHOLDER)
    }));
  }

  it('renders the resolved data', async function() {
    const rows = [
      {
        name: 'an xss<img src="/static/rapture/resources/icons/x16/user.png" onload="alert(0)">',
        type: 'csel',
        description: 'description'
      },
      {
        name: 'jexl-selector',
        type: 'jexl',
        description: 'jexl-description'
      }
    ];

    axios.get.mockReturnValue(Promise.resolve({
      data: rows
    }));

    const {container, loadingMask} = renderView();

    await wait(() => expect(loadingMask()).not.toBeInTheDocument());

    rows.forEach((row, i) => {
      expect(container.querySelector(`tbody tr:nth-child(${i+1}) td:nth-child(1)`)).toHaveTextContent(row.name);
      expect(container.querySelector(`tbody tr:nth-child(${i+1}) td:nth-child(2)`)).toHaveTextContent(row.type.toUpperCase());
      expect(container.querySelector(`tbody tr:nth-child(${i+1}) td:nth-child(3)`)).toHaveTextContent(row.description);
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
