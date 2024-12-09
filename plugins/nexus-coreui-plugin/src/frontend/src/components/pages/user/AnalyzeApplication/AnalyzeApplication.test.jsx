import React from 'react';
import Axios from 'axios';
import TestUtils from "nexus-ui-plugin/src/frontend/src/interface/TestUtils";
import AnalyzeApplication from "./AnalyzeApplication";
import {wait} from "@testing-library/react";
import '@testing-library/jest-dom/extend-expect';
import UIStrings from "../../../../constants/UIStrings";

const componentName = 'componentName';

jest.mock('axios', () => {
  return {
    get: jest.fn(() => Promise.resolve()),
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

describe('AnalyzeApplication', () => {
  const render = (selectors = ({}) => ({})) => TestUtils
      .render(<AnalyzeApplication rerender={jest.fn()} componentModel={componentName} onClose={jest.fn()} />, selectors);

  it('fetches state of eula', async() => {
    Axios.get.mockReturnValue(Promise.resolve({
      data: {
        tosAccepted: false
      }
    }));

    let {loadingMask} = render();

    await wait(() => expect(loadingMask()).not.toBeInTheDocument());

    expect(Axios.get).toHaveBeenCalledTimes(1);
    expect(Axios.get).toHaveBeenCalledWith(`/service/rest/internal/ui/ahc`, {params: {component: componentName}});
  });

  it('show eula if eula never accepted', async () => {
    Axios.get.mockReturnValue(Promise.resolve({
      data: {
        tosAccepted: false
      }
    }));

    let {header, loadingMask} = render(({getByText}) => ({
      header: () => getByText(UIStrings.HEALTHCHECK_EULA.HEADER)
    }));

    await wait(() => expect(loadingMask()).not.toBeInTheDocument());

    expect(header()).toBeInTheDocument();
  });

  it('show analyze if eula accepted', async () => {
    Axios.get.mockReturnValue(Promise.resolve({
      data: {
        tosAccepted: true
      }
    }));

    let {header, loadingMask} = render(({getByText}) => ({header: () => getByText(UIStrings.ANALYZE_APPLICATION.HEADER)}));

    await wait(() => expect(loadingMask()).not.toBeInTheDocument());

    expect(header()).toBeInTheDocument();
  });
});
