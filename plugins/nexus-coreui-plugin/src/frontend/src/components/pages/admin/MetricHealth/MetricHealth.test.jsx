import React from 'react';
import { act } from 'react-dom/test-utils';
import {render, wait} from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import axios from 'axios';

import MetricHealth from './MetricHealth';

jest.mock('axios', () => ({
  ...jest.requireActual('axios'), // Use most functions from actual axios
  get: jest.fn()
}));

describe('MetricHealth', function() {
  const renderView = async () => {
    var selectors;

    await act(async () => {
      const {container, queryByText} = render(<MetricHealth/>);

      selectors = {
        container,
        loadingMask: () => queryByText("Loading…"),
      }
    });

    return selectors;
  };

  it('renders the resolved data', async function() {
    const metrics = {
      "Available CPUs" : {
        "healthy" : true,
        "message" : "The host system is allocating a maximum of 6 cores to the application.",
        "error" : null,
        "details" : null,
        "time" : 1588629343436,
        "duration" : 0,
        "timestamp" : "2020-05-04T15:55:43.436-06:00"
      },
      "testError" : {
        "healthy" : false,
        "message" : "TestError",
        "error" : {
          "cause" : null,
          "stackTrace" : [ {
            "methodName" : "getSystemStatusChecks",
            "fileName" : "HealthCheckResource.java",
            "lineNumber" : 61,
            "className" : "org.sonatype.nexus.internal.rest.HealthCheckResource",
            "nativeMethod" : false
          }, {
            "methodName" : "CGLIB$getSystemStatusChecks$0",
            "fileName" : "<generated>",
            "lineNumber" : -1,
            "className" : "org.sonatype.nexus.internal.rest.HealthCheckResource$$EnhancerByGuice$$1b6f529d",
            "nativeMethod" : false
          } ],
          "message" : "TestError",
          "localizedMessage" : "TestError",
          "suppressed" : [ ]
        }
      }
    };

    axios.get.mockReturnValue(Promise.resolve({
      data: metrics
    }));

    const {container, loadingMask} = await renderView();

    await wait(() => expect(loadingMask()).not.toBeInTheDocument());

    expect(container.querySelector(`tbody tr:nth-child(1) td:nth-child(2)`)).toHaveTextContent("Available CPUs");
    expect(container.querySelector(`tbody tr:nth-child(1) td:nth-child(3)`)).toHaveTextContent("The host system is allocating a maximum of 6 cores to the application.");

    expect(container).toMatchSnapshot();
  });

  it('renders a loading spinner', async function() {
    axios.get.mockReturnValue(new Promise(() => {}));

    const {container, loadingMask} = await renderView();

    expect(loadingMask()).toBeInTheDocument();
    expect(container).toMatchSnapshot();
  });

  it('renders an error message', async function() {
    axios.get.mockReturnValue(Promise.reject({message: 'Error'}));

    const {container, loadingMask} = await renderView();

    await wait(() => expect(loadingMask()).not.toBeInTheDocument());

    expect(container.querySelector('.nx-cell--meta-info')).toHaveTextContent('Error');
    expect(container).toMatchSnapshot();
  });
});
