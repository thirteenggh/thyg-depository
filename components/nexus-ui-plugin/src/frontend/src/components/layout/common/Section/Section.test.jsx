import React from 'react';
import {render} from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';

import Section from './Section';

describe('Section', () => {
  it('renders correctly', () => {
    const {container} = render(
        <Section>section</Section>
    );
    expect(container).toMatchSnapshot();
  });
});
