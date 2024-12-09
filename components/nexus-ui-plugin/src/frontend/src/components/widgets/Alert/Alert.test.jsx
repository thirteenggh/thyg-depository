import React from 'react';
import {render} from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';

import Alert from "./Alert";

describe('Alert', () => {
  it('renders correctly', () => {
    const {container} = render(<Alert type="error"/>);
    expect(container).toMatchSnapshot();
  });

  it('renders correctly with an error message', () => {
    const {container} = render(<Alert type="error">A custom error message</Alert>);
    expect(container).toMatchSnapshot();
  });
});
