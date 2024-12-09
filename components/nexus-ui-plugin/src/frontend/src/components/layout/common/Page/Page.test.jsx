import React from 'react';

import {render} from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';

import Page from './Page';

describe('Page', () => {
  it('renders with custom class', () => {
    const {container} = render(
        <Page className="test">test</Page>
    );
    expect(container).toMatchSnapshot();
  });
});
