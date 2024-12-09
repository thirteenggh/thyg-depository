import React from 'react';
import {fireEvent, wait} from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import TestUtils from 'nexus-ui-plugin/src/frontend/src/interface/TestUtils';
import PasswordChangeForm from './PasswordChangeForm';
import UIStrings from '../../../../constants/UIStrings';
import Axios from 'axios';

jest.mock('nexus-ui-plugin', () => {
  return {
    ...jest.requireActual('nexus-ui-plugin'),
    ExtJS: {
      showSuccessMessage: jest.fn(),
      fetchAuthenticationToken: jest.fn(() => Promise.resolve({data: 'fakeToken'})),
    }
  };
});

jest.mock('axios', () => {
  return {
    put: jest.fn(() => Promise.resolve()),
  };
});

describe('PasswordChangeForm', () => {
  const render = () => TestUtils.render(<PasswordChangeForm userId="admin"/>, ({getByLabelText, getByText}) => ({
    passwordCurrent: () => getByLabelText(UIStrings.USER_ACCOUNT.PASSWORD_CURRENT_FIELD_LABEL),
    passwordNew: () => getByLabelText(UIStrings.USER_ACCOUNT.PASSWORD_NEW_FIELD_LABEL),
    passwordNewConfirm: () => getByLabelText(UIStrings.USER_ACCOUNT.PASSWORD_NEW_CONFIRM_FIELD_LABEL),
    changePasswordButton: () => getByText(UIStrings.USER_ACCOUNT.ACTIONS.changePassword),
    discardButton: () => getByText(UIStrings.USER_ACCOUNT.ACTIONS.discardChangePassword),
  }));

  it('renders correctly', async () => {
    let {container, passwordCurrent, passwordNew, passwordNewConfirm, changePasswordButton, discardButton} = render();

    expect(container).toMatchSnapshot();
    expect(passwordCurrent()).toHaveValue('');
    expect(passwordNew()).toHaveValue('');
    expect(passwordNewConfirm()).toHaveValue('');
    expect(changePasswordButton()).toHaveClass('disabled');
    expect(discardButton()).toHaveClass('disabled');
  });

  it('prevents password change when new matches current', async () => {
    let {passwordCurrent, passwordNew, passwordNewConfirm, changePasswordButton, discardButton} = render();

    await TestUtils.changeField(passwordCurrent, 'foobar');

    await TestUtils.changeField(passwordNew, 'foobar');

    await TestUtils.changeField(passwordNewConfirm, 'foobar');

    expect(changePasswordButton()).toHaveClass('disabled');
    expect(discardButton()).not.toHaveClass('disabled');
  });

  it('prevents password change when new does not match confirm', async () => {
    let {passwordCurrent, passwordNew, passwordNewConfirm, changePasswordButton, discardButton} = render();

    await TestUtils.changeField(passwordCurrent, 'foobar');

    await TestUtils.changeField(passwordNew, 'bazzle');

    await TestUtils.changeField(passwordNewConfirm, 'bazzl');

    expect(changePasswordButton()).toHaveClass('disabled');
    expect(discardButton()).not.toHaveClass('disabled');
  });

  it('sends the correct password change request', async () => {
    let {passwordCurrent, passwordNew, passwordNewConfirm, changePasswordButton, discardButton} = render();

    await TestUtils.changeField(passwordCurrent, 'foobar');

    await TestUtils.changeField(passwordNew, 'bazzle');

    await TestUtils.changeField(passwordNewConfirm, 'bazzle');

    expect(changePasswordButton()).not.toHaveClass('disabled');
    expect(discardButton()).not.toHaveClass('disabled');

    fireEvent.click(changePasswordButton());

    await wait(() => expect(Axios.put).toHaveBeenCalledTimes(1));
    expect(Axios.put).toHaveBeenCalledWith(
        `/service/rest/internal/ui/user/admin/password`,
        {
          authToken: 'fakeToken',
          password: 'bazzle',
        }
    );
    expect(passwordCurrent()).toHaveValue('');
    expect(passwordNew()).toHaveValue('');
    expect(passwordNewConfirm()).toHaveValue('');
    expect(changePasswordButton()).toHaveClass('disabled');
    expect(discardButton()).toHaveClass('disabled');
  });

  it('resets the form on discard', async () => {
    let {passwordCurrent, passwordNew, passwordNewConfirm, changePasswordButton, discardButton} = render();

    await TestUtils.changeField(passwordCurrent, 'foobar');

    await TestUtils.changeField(passwordNew, 'bazzle');

    await TestUtils.changeField(passwordNewConfirm, 'bazzle');

    expect(changePasswordButton()).not.toHaveClass('disabled');
    expect(discardButton()).not.toHaveClass('disabled');

    fireEvent.click(discardButton());

    await wait(() => expect(passwordCurrent()).toHaveValue(''));
    expect(passwordNew()).toHaveValue('');
    expect(passwordNewConfirm()).toHaveValue('');
    expect(changePasswordButton()).toHaveClass('disabled');
    expect(discardButton()).toHaveClass('disabled');
  });
});
