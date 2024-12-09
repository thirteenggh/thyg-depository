import React from 'react';
import { act } from 'react-dom/test-utils';
import {fireEvent, render, wait} from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';

import SupportZipResponse from './SupportZipResponse';

describe('SupportZipResponse', function() {
  const renderView = async (response, download) => {
    var selectors;

    await act(async () => {
      selectors = render(<SupportZipResponse response={response} download={download}/>);
    });

    return selectors;
  };

  it('renders support zip response', async function() {
    const response = {
      "name": "support-20200527-095617-6.zip",
      "size": "281152",
      "file": "/Users/ataylor/dev/sonatype/nexus-internal/target/sonatype-work/nexus3/downloads/support-20200527-095617-6.zip",
      "truncated": "false"
    };

    const {container} = await renderView(response);

    expect(container.querySelector('input[name="name"]')).toHaveValue("support-20200527-095617-6.zip");
    expect(container.querySelector('input[name="size"]')).toHaveValue("281152");
    expect(container.querySelector('input[name="file"]')).toHaveValue("/Users/ataylor/dev/sonatype/nexus-internal/target/sonatype-work/nexus3/downloads/support-20200527-095617-6.zip");

    expect(container).toMatchSnapshot();
  });

  it('downloads', async function() {
    const response = {
      "name": "support-20200527-095617-6.zip",
      "size": "281152",
      "file": "/Users/ataylor/dev/sonatype/nexus-internal/target/sonatype-work/nexus3/downloads/support-20200527-095617-6.zip",
      "truncated": "false"
    };

    const download = jest.fn();
    const {container} = await renderView(response, download);
    const downloadButton = container.querySelector('button[type=submit]');

    fireEvent.click(downloadButton);

    expect(download).toBeCalledWith(expect.any(Object), 'support-20200527-095617-6.zip');
  });
});
