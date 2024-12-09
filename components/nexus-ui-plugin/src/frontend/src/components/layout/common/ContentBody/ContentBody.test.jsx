import React from 'react';
import {render} from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';

import ContentBody from './ContentBody';

describe('ContentBody', () => {
  it('renders correctly', () => {
    const {container} = render(
        <ContentBody className="test">body</ContentBody>
    );
    expect(container).toMatchSnapshot();
  });
});
