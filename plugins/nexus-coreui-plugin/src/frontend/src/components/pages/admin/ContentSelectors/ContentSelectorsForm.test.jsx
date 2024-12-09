import React from 'react';
import {fireEvent, wait, waitForElement, waitForElementToBeRemoved} from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import TestUtils from 'nexus-ui-plugin/src/frontend/src/interface/TestUtils';
import axios from 'axios';
import {ExtJS} from 'nexus-ui-plugin';

import ContentSelectorsForm from './ContentSelectorsForm';

import UIStrings from '../../../../constants/UIStrings';

jest.mock('axios', () => ({
  ...jest.requireActual('axios'), // Use most functions from actual axios
  get: jest.fn(),
  put: jest.fn(),
  post: jest.fn(),
  delete: jest.fn()
}));

jest.mock('nexus-ui-plugin', () => ({
  ...jest.requireActual('nexus-ui-plugin'),
  ExtJS: {
    requestConfirmation: jest.fn(),
    showErrorMessage: jest.fn()
  },
  Utils: {
    buildFormMachine: function(args) {
      const machine = jest.requireActual('nexus-ui-plugin').Utils.buildFormMachine(args);
      return machine.withConfig({
        actions: {
          logSaveSuccess: jest.fn(),
          logSaveError: jest.fn(),
          logLoadError: jest.fn()
        }
      })
    },
    isInvalid: jest.requireActual('nexus-ui-plugin').Utils.isInvalid,
    isBlank: jest.requireActual('nexus-ui-plugin').Utils.isBlank,
    notBlank: jest.requireActual('nexus-ui-plugin').Utils.notBlank,
    fieldProps: jest.requireActual('nexus-ui-plugin').Utils.fieldProps,
    saveTooltip: jest.requireActual('nexus-ui-plugin').Utils.saveTooltip
  }
}));

describe('ContentSelectorsForm', function() {
  const CONFIRM = Promise.resolve();
  const onDone = jest.fn();

  function renderEditView(itemId) {
    return renderView(<ContentSelectorsForm itemId={itemId} onDone={onDone}/>);
  }

  function renderCreateView() {
    return renderView(<ContentSelectorsForm onDone={onDone} />);
  }

  function renderView(view) {
    return TestUtils.render(view, ({queryByLabelText, queryByText}) => ({
      name: () => queryByLabelText(UIStrings.CONTENT_SELECTORS.NAME_LABEL),
      type: () => queryByLabelText(UIStrings.CONTENT_SELECTORS.TYPE_LABEL),
      description: () => queryByLabelText(UIStrings.CONTENT_SELECTORS.DESCRIPTION_LABEL),
      expression: () => queryByLabelText(UIStrings.CONTENT_SELECTORS.EXPRESSION_LABEL),
      saveButton: () => queryByText(UIStrings.SETTINGS.SAVE_BUTTON_LABEL),
      cancelButton: () => queryByText(UIStrings.SETTINGS.CANCEL_BUTTON_LABEL),
      deleteButton: () => queryByText(UIStrings.SETTINGS.DELETE_BUTTON_LABEL)
    }));
  }

  it('renders a loading spinner', async function() {
    axios.get.mockImplementation(() => new Promise(() => {}));

    const {container, loadingMask} = renderEditView('test');

    await waitForElement(loadingMask);

    expect(container).toMatchSnapshot();
  });

  it('renders the resolved data', async function() {
    const itemId = 'test';

    axios.get.mockImplementation((url) => {
      if (url === `/service/rest/v1/security/content-selectors/${itemId}`) {
        return Promise.resolve({
          data: {
            'name' : 'xss<img src="/static/rapture/resources/icons/x16/user.png" onload="alert(0)">',
            'type' : 'csel',
            'description' : 'description',
            'expression' : 'format == "raw"'
          }
        });
      }
      else if (url === '/service/rest/internal/ui/repositories?withAll=true&withFormats=true') {
        return Promise.resolve({data: []});
      }
    });

    const {container, loadingMask, name, type, description, expression, saveButton} = renderEditView(itemId);

    await waitForElementToBeRemoved(loadingMask);

    expect(name()).toHaveValue('xss<img src="/static/rapture/resources/icons/x16/user.png" onload="alert(0)">');
    expect(type()).toHaveTextContent('CSEL');
    expect(description()).toHaveValue('description');
    expect(expression()).toHaveValue('format == "raw"');
    expect(saveButton()).toHaveClass('disabled');
    expect(container).toMatchSnapshot();
  });

  it('renders an error message', async function() {
    axios.get.mockReturnValue(Promise.reject({message: 'Error'}));

    const {container, loadingMask} = renderEditView('itemId');

    await waitForElementToBeRemoved(loadingMask);

    expect(container.querySelector('.nx-alert--error')).toHaveTextContent('Error');
  });

  it('requires the name and expression fields when creating a new content selector', async function() {
    axios.get.mockReturnValue(Promise.resolve({data: []}));

    const {loadingMask, name, expression, saveButton} = renderCreateView();

    await waitForElementToBeRemoved(loadingMask);

    await TestUtils.changeField(name, '');
    await TestUtils.changeField(expression, 'format == "raw"');
    expect(saveButton()).toHaveClass('disabled');

    await TestUtils.changeField(name, 'name');
    await TestUtils.changeField(expression, '');
    expect(saveButton()).toHaveClass('disabled');

    await TestUtils.changeField(name, 'name');
    await TestUtils.changeField(expression, 'format == "raw"');
    expect(saveButton()).not.toHaveClass('disabled');
  });

  it('fires onDone when cancelled', async function() {
    const {loadingMask, cancelButton} = renderCreateView();

    await waitForElementToBeRemoved(loadingMask);

    fireEvent.click(cancelButton());

    await wait(() => expect(onDone).toBeCalled());
  });

  it('requests confirmation when delete is requested', async function() {
    const itemId = 'test';
    axios.get.mockImplementation((url) => {
      if (url === `/service/rest/v1/security/content-selectors/${itemId}`) {
        return Promise.resolve({
          data: {
            'name' : itemId,
            'type' : 'csel',
            'description' : 'description',
            'expression' : 'format == "raw"'
          }
        });
      }
      else if (url === '/service/rest/internal/ui/repositories?withAll=true&withFormats=true') {
        return Promise.resolve({data: []});
      }
    });

    axios.delete.mockReturnValue(Promise.resolve());

    const {loadingMask, deleteButton} = renderEditView(itemId);

    await waitForElementToBeRemoved(loadingMask);

    axios.put.mockReturnValue(Promise.resolve());

    ExtJS.requestConfirmation.mockReturnValue(CONFIRM);
    fireEvent.click(deleteButton());

    await wait(() => expect(axios.delete).toBeCalledWith(`/service/rest/v1/security/content-selectors/${itemId}`));
    expect(onDone).toBeCalled();
  });

  it('loads xss and escapes the values', () => {
    axios.get.mockImplementation((url) => {
      if (url === `/service/rest/v1/security/content-selectors/${itemId}`) {
        return Promise.resolve({
          data: {
            'name' : itemId,
            'type' : 'csel',
            'description' : 'description',
            'expression' : 'format == "raw"'
          }
        });
      }
      else if (url === '/service/rest/internal/ui/repositories?withAll=true&withFormats=true') {
        return Promise.resolve({data: []});
      }
    });
  });

  it('saves', async function() {
    axios.get.mockReturnValue(Promise.resolve({data: []}));
    axios.post.mockReturnValue(Promise.resolve());

    const {loadingMask, name, description, expression, saveButton} = renderCreateView();

    await waitForElementToBeRemoved(loadingMask);

    await wait(() => expect(window.dirty).toEqual([]));

    await TestUtils.changeField(name, 'test');
    await TestUtils.changeField(description, 'description');
    await TestUtils.changeField(expression, 'format == "raw"');

    await wait(() => expect(window.dirty).toEqual(['ContentSelectorsFormMachine']));

    expect(saveButton()).not.toBeDisabled();
    fireEvent.click(saveButton());

    await wait(() => expect(axios.post).toHaveBeenCalledWith(
        '/service/rest/v1/security/content-selectors',
        {name: 'test', description: 'description', expression: 'format == "raw"'}
    ));
    expect(window.dirty).toEqual([]);
  });
});
