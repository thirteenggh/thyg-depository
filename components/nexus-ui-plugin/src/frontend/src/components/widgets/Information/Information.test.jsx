import React from 'react';
import {render} from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';

import Information from "./Information";

describe('FieldErrorMessage', () => {
  it('renders boolean values correctly', () => {
    const info = {
      'boolean': false
    };
    const {container, getByText} = render(<Information information={info}/>);

    expect(getByText('false')).toBeInTheDocument();

    expect(container).toMatchSnapshot();
  });

  it('renders numeric values correctly', () => {
    const info = {
      'numeric': 0
    };
    const {container, getByText} = render(<Information information={info}/>);

    expect(getByText('0')).toBeInTheDocument();

    expect(container).toMatchSnapshot();
  });

  it('renders text values correctly', () => {
    const info = {
      'text': 'test'
    };
    const {container, getByText} = render(<Information information={info}/>);

    expect(getByText('test')).toBeInTheDocument();

    expect(container).toMatchSnapshot();
  });
});
