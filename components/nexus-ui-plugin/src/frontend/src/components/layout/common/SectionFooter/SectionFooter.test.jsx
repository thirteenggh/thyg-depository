import React from 'react';
import {render} from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';

import SectionFooter from './SectionFooter';

describe('SectionFooter', () => {
  it('renders correctly', () => {
    const {container} = render(
        <SectionFooter>footer</SectionFooter>
    );
    expect(container).toMatchSnapshot();
  });
});
