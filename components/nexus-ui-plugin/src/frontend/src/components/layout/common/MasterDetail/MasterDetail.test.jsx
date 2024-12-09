import React from 'react';
import {fireEvent, render} from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';

import MasterDetail from './MasterDetail';
import Master from './Master';
import Detail from './Detail';

import ExtJS from '../../../../interface/ExtJS';

jest.mock('../../../../interface/ExtJS', () => class {
  static useHistory = jest.fn();
});

describe('MasterDetail', () => {
  it('renders the master view when at the root', () => {
    ExtJS.useHistory.mockReturnValue({location: {pathname: ''}});
    const {container, queryByTestId} = render(
        <MasterDetail path="admin">
          <Master><TestMaster /></Master>
          <Detail><TestDetail /></Detail>
        </MasterDetail>
    );

    expect(queryByTestId('master')).toBeInTheDocument();
    expect(queryByTestId('detail')).not.toBeInTheDocument();
    expect(container).toMatchSnapshot();
  });

  it('renders the detail view when on a child path', () => {
    ExtJS.useHistory.mockReturnValue({location: {pathname: ':itemId'}});
    const {container, queryByTestId} = render(
        <MasterDetail path="admin">
          <Master><TestMaster /></Master>
          <Detail><TestDetail /></Detail>
        </MasterDetail>
    );

    expect(queryByTestId('master')).not.toBeInTheDocument();
    expect(queryByTestId('detail')).toBeInTheDocument();
    expect(container).toMatchSnapshot();
  });

  it('renders the detail view when onCreate is called', () => {
    ExtJS.useHistory.mockReturnValue({location: {pathname: ''}});
    const {container, getByTestId, queryByTestId} = render(
        <MasterDetail path="admin">
          <Master><TestMaster /></Master>
          <Detail><TestDetail /></Detail>
        </MasterDetail>
    );

    expect(queryByTestId('master')).toBeInTheDocument();
    fireEvent.click(getByTestId('create'));

    expect(queryByTestId('master')).not.toBeInTheDocument();
    expect(queryByTestId('detail')).toBeInTheDocument();
    expect(container).toMatchSnapshot();
  });
});

// These components remove the warnings about the Master/Detail props not being used and provide hooks for validation
function TestMaster({onCreate, onEdit}) {
  return <div data-testid="master"><button data-testid="create" onClick={onCreate} /></div>;
}

function TestDetail({itemId, onDone}) {
  return <div data-testid="detail">${itemId}</div>;
}
