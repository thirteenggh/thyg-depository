import React from 'react';
import Axios from 'axios';
import {act} from 'react-dom/test-utils';
import {fireEvent, wait} from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import TestUtils from 'nexus-ui-plugin/src/frontend/src/interface/TestUtils';

import NuGetApiToken from './NuGetApiToken';
import UIStrings from '../../../../constants/UIStrings';

const mockToken = 'fakeToken'
const mockTokenB64 = 'ZmFrZVRva2Vu'

// TODO: come up with a better solution to this
window.NX = { Messages: { success: () => {} } };

jest.mock('nexus-ui-plugin', () => {
  return {
    ...jest.requireActual('nexus-ui-plugin'),
    ExtJS: {
      requestAuthenticationToken: jest.fn(() => Promise.resolve(mockToken)),
      urlOf: jest.fn(() => 'http://localhost:4242/repository/fakeUrl')
    }
  };
});

jest.mock('axios', () => {
  return {
    ...jest.requireActual('axios'),
    get: jest.fn((url) => {
      if (url === `/service/rest/internal/nuget-api-key?authToken=${mockTokenB64}`) {
        return Promise.resolve({data: {apiKey: 'testApiKey'}});
      }
    }),
    delete: jest.fn((url) => {
      if (url === `/service/rest/internal/nuget-api-key?authToken=${mockTokenB64}`) {
        return Promise.resolve({data: {apiKey: 'newTestApiKey'}});
      }
  }),
  };
});

describe('NuGetApiToken', () => {
  function renderView(view) {
    return TestUtils.render(view, ({queryByText, getByText}) => ({
      accessButton: () => getByText(UIStrings.NUGET_API_KEY.ACCESS.BUTTON),
      resetButton: () => getByText(UIStrings.NUGET_API_KEY.RESET.BUTTON),
      nugetKey: () => queryByText('testApiKey'),
      newNugetKey: () => queryByText('newTestApiKey')
    }));
  }

  it('renders correctly', async () => {
    let { container, accessButton, nugetKey } = renderView(<NuGetApiToken/>);

    expect(container).toMatchSnapshot('baseline');
    await wait(() =>  expect(nugetKey()).not.toBeInTheDocument());

    await act(async () => fireEvent.click(accessButton()));

    await wait(() =>  expect(nugetKey()).toBeInTheDocument());

    expect(container).toMatchSnapshot('nugetKeyPresent');
  });

  it('uses the get call when the access button is pressed',  async () => {
    let { accessButton, nugetKey } = renderView(<NuGetApiToken/>);

    await act(async () => fireEvent.click(accessButton()));

    await wait(() =>  expect(nugetKey()).toBeInTheDocument());

    expect(Axios.get).toHaveBeenCalledTimes(1);
    expect(Axios.get).toHaveBeenCalledWith(
        `/service/rest/internal/nuget-api-key?authToken=${mockTokenB64}`
    );
  });

  it('uses the delete call when the reset button is pressed',  async () => {
    let { resetButton } =  renderView(<NuGetApiToken/>);

    await act(async () => fireEvent.click(resetButton()));

    expect(Axios.delete).toHaveBeenCalledTimes(1);
    expect(Axios.delete).toHaveBeenCalledWith(
        `/service/rest/internal/nuget-api-key?authToken=${mockTokenB64}`
    );
  });
});
