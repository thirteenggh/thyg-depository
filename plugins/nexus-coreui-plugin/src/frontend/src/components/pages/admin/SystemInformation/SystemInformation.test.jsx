import React from 'react';
import {render, wait} from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';

import SystemInformation from './SystemInformation';

jest.mock('axios', () => {  // Mock response from axios
  return {
    ...jest.requireActual('axios'), // Use most functions from actual axios
    get: jest.fn((url) => Promise.resolve({
      data: {
        'nexus-status': {
          'status': 'value'
        },
        'nexus-node': {
          'node': 0
        },
        'nexus-configuration': {
          'enabled': true
        },
        'nexus-properties': {
          'property': false
        },
        'nexus-license': {
          'fingerprint': 'hash'
        },
        'nexus-bundles': {
          '0': {
            'bundleId': 0,
            'enabled': true,
            'config': 'value'
          }
        },
        'system-time': {
          'time': 'value'
        },
        'system-properties': {
          'property': 'value'
        },
        'system-environment': {
          'property': 'value'
        },
        'system-runtime': {
          'property': 'value'
        },
        'system-network': {
          'en0': {
            'enabled': true
          }
        },
        'system-filestores': {
          '/dev/null': {
            'path': '/dev/null'
          }
        }
      }
    }))
  };
});

describe('SystemInformation', () => {
  it('renders correctly', async () => {
    const {container, queryByText} = render(<SystemInformation/>);

    expect(container).toMatchSnapshot();

    await wait(() => expect(container).toContainElement(queryByText('system-filestores')));

    expect(container).toMatchSnapshot();
  });
});
