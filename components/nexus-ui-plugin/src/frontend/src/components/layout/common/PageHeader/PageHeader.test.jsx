import React from 'react';

import {render} from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';

import PageHeader from './PageHeader';

describe('PageHeader', () => {
  it('renders with children', () => {
    const {container} = render(
        <PageHeader>test</PageHeader>
    );
    expect(container).toMatchSnapshot();
  });
});
