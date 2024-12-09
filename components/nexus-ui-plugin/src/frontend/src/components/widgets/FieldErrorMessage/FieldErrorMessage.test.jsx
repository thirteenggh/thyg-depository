import React from 'react';
import {render} from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';

import FieldErrorMessage from "./FieldErrorMessage";

describe('FieldErrorMessage', () => {
  it('renders correctly', () => {
    const {container} = render(<FieldErrorMessage/>);
    expect(container).toMatchSnapshot();
  });

  it('renders correctly with an error message', () => {
    const {container} = render(<FieldErrorMessage message={"A custom error message"}/>);
    expect(container).toMatchSnapshot();
  });
});
