import React from 'react';
import {render} from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';

import SupportRequest from './SupportRequest';

describe('SystemInformation', () => {
  it('renders correctly', async () => {
    const {container} = render(<SupportRequest/>);

    expect(container).toMatchSnapshot();
  });
});
