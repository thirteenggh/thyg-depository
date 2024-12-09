import React from 'react';
import {render} from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';

import BreadcrumbActions from './BreadcrumbActions';

describe('BreadcrumbActions', () => {
  it('renders correctly', () => {
    const {container} = render(
        <BreadcrumbActions><span>action</span></BreadcrumbActions>
    );
    expect(container).toMatchSnapshot();
  });
});
