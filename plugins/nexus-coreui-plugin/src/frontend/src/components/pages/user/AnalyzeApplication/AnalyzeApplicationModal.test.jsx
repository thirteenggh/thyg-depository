import React from 'react';
import Axios from 'axios';
import axios from 'axios';
import TestUtils from "nexus-ui-plugin/src/frontend/src/interface/TestUtils";
import {fireEvent, wait} from "@testing-library/react";
import '@testing-library/jest-dom/extend-expect';
import UIStrings from "../../../../constants/UIStrings";
import AnalyzeApplicationModal from "./AnalyzeApplicationModal";
import {act} from "react-dom/test-utils";

const component = {
  'componentName': 'foobar',
  'repositoryName': 'hosted-repo'
};

jest.mock('axios', () => ({
  ...jest.requireActual('axios'), // Use most functions from actual axios
  get: jest.fn(() => Promise.resolve()),
  post: jest.fn(() => Promise.resolve())
}));

jest.mock('nexus-ui-plugin', () => {
  return {
    ...jest.requireActual('nexus-ui-plugin'),
    ExtJS: {
      showSuccessMessage: jest.fn(),
      showErrorMessage: jest.fn(),
    }
  }
});

const onAnalyzedMock = jest.fn(() => {});

const onCancelMock = jest.fn(() => {});

describe('AnalyzeApplicationModal', () => {
  const render = () => TestUtils
      .render(<AnalyzeApplicationModal componentModel={component} onCancel={onCancelMock} onAnalyzed={onAnalyzedMock} />, ({getByLabelText, getByText}) => ({
        email: () => getByLabelText(UIStrings.ANALYZE_APPLICATION.EMAIL.LABEL),
        password: () => getByLabelText(UIStrings.ANALYZE_APPLICATION.PASSWORD.LABEL),
        packages: () => getByLabelText(UIStrings.ANALYZE_APPLICATION.PACKAGES.LABEL),
        reportName: () => getByLabelText(UIStrings.ANALYZE_APPLICATION.REPORT.LABEL),
        analyzeButton: () => getByText(UIStrings.ANALYZE_APPLICATION.BUTTONS.ANALYZE),
        cancelButton: () => getByText(UIStrings.ANALYZE_APPLICATION.BUTTONS.CANCEL),
        selectedAsset: () => getByLabelText(UIStrings.ANALYZE_APPLICATION.SELECT_ASSET.LABEL),
      }));

  axios.get.mockReturnValue(Promise.resolve({
    data: {
      emailAddress: 'test@sonatype.com',
      reportLabel: 'foo-1.0.0.jar',
      assetMap: {
        'foo': 'foo-app',
        'bar': 'bar-app'
      },
      selectedAsset: 'foo',
      tosAccepted: false
    }
  }));

  it('renders correctly', async() => {
    let {loadingMask, email, password, packages, reportName, cancelButton, analyzeButton, selectedAsset } = render();

    await wait(() => expect(loadingMask()).not.toBeInTheDocument());

    expect(email()).toHaveValue('test@sonatype.com');
    expect(password()).toHaveValue('');
    expect(packages()).toHaveValue('');
    expect(reportName()).toHaveValue('foo-1.0.0.jar');
    expect(cancelButton()).not.toHaveClass('disabled');
    expect(analyzeButton()).toHaveClass('disabled');
    expect(selectedAsset()).toHaveValue('foo');
  });

  it('form values are updated', async() => {
    let {loadingMask, email, password, packages, reportName, selectedAsset, cancelButton, analyzeButton } = render();

    await wait(() => expect(loadingMask()).not.toBeInTheDocument());

    await TestUtils.changeField(email, 'foo@bar.com');
    await TestUtils.changeField(password, 'foobar');
    await TestUtils.changeField(packages, 'packages');
    await TestUtils.changeField(reportName, 'foo-2.0.0.jar');
    await TestUtils.changeField(selectedAsset, 'bar');

    expect(email()).toHaveValue('foo@bar.com');
    expect(password()).toHaveValue('foobar');
    expect(packages()).toHaveValue('packages');
    expect(reportName()).toHaveValue('foo-2.0.0.jar');
    expect(selectedAsset()).toHaveValue('bar');
    expect(cancelButton()).not.toHaveClass('disabled');
    expect(analyzeButton()).not.toHaveClass('disabled');
  });

  it('analyze cancelled', async() => {
    let {loadingMask, email, password, analyzeButton, cancelButton } = render();

    await wait(() => expect(loadingMask()).not.toBeInTheDocument());

    expect(analyzeButton()).toHaveClass('disabled');
    await TestUtils.changeField(email, 'foo@bar.com');
    await TestUtils.changeField(password, 'foobar');
    expect(analyzeButton()).not.toHaveClass('disabled');

    await act(async () => fireEvent.click(cancelButton()));

    expect(Axios.post).toHaveBeenCalledTimes(0);

    await wait( () => expect(onCancelMock).toHaveBeenCalled());
  });

  it('analyze submitted', async() => {
    let {loadingMask, email, password, packages, analyzeButton } = render();

    await wait(() => expect(loadingMask()).not.toBeInTheDocument());

    await TestUtils.changeField(email, 'foo@bar.com');
    await TestUtils.changeField(password, 'foobar');
    await TestUtils.changeField(packages, 'packageA');
    expect(analyzeButton()).not.toHaveClass('disabled');

    fireEvent.click(analyzeButton());

    expect(Axios.post).toHaveBeenCalledTimes(1);
    expect(Axios.post).toHaveBeenCalledWith(
        '/service/rest/internal/ui/ahc',
        {
          repositoryName: 'hosted-repo',
          assetId: 'foo',
          emailAddress: 'foo@bar.com',
          reportPassword: 'foobar',
          reportLabel: 'foo-1.0.0.jar',
          proprietaryPackages: 'packageA'
        }
    );

    await wait( () => expect(onAnalyzedMock).toHaveBeenCalled());
  });
});
