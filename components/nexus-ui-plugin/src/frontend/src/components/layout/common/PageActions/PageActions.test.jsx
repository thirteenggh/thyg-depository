import React from 'react';
import {render} from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';

import PageActions from './PageActions';

describe('PageActions', () => {
  it('renders correctly', () => {
    const {container} = render(
        <PageActions><span>action</span></PageActions>
    );
    expect(container).toMatchSnapshot();
  });
});
