import React from 'react';
import {act} from 'react-dom/test-utils';
import {fireEvent} from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import TestUtils from 'nexus-ui-plugin/src/frontend/src/interface/TestUtils';
import UIStrings from '../../../../constants/UIStrings';
import Axios from 'axios';
import HealthCheckEula from "./HealthCheckEula";

jest.mock('axios', () => {
  return {
    put: jest.fn(() => Promise.resolve()),
  };
});

jest.mock('nexus-ui-plugin', () => {
  return {
    ...jest.requireActual('nexus-ui-plugin'),
    ExtJS: {
      urlOf: jest.fn()
    }
  }
});

describe('HealthCheckEula', () => {
  const render = () => TestUtils.render(<HealthCheckEula onAccept={() => {}} onDecline={() => {}}/>, ({getByText}) => ({
    acceptButton: () => getByText(UIStrings.HEALTHCHECK_EULA.BUTTONS.ACCEPT),
    declineButton: () => getByText(UIStrings.HEALTHCHECK_EULA.BUTTONS.DECLINE),
  }));

  it('sends the accept eula message', async () => {
    let {acceptButton} = render();

    await act(async () => fireEvent.click(acceptButton()));

    expect(Axios.put).toHaveBeenCalledTimes(1);
    expect(Axios.put).toHaveBeenCalledWith(`/service/rest/internal/ui/ahc`);
  });

  it('does not send accept eula message', async () => {
    let {declineButton} = render();

    await act(async () => fireEvent.click(declineButton()));

    expect(Axios.put).toHaveBeenCalledTimes(0);
  });
});
